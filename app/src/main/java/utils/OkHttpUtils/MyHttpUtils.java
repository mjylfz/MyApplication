package utils.OkHttpUtils;

import android.os.Handler;
import android.os.Looper;

import com.squareup.okhttp.Call;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.Headers;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.MultipartBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.CookieManager;
import java.net.CookiePolicy;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by LFZ on 2017/3/13.
 * OKHttp的封装类
 * change by LFZ on 2017/10/8
 */

public class MyHttpUtils {

    private static MyHttpUtils mInstance;
    private OkHttpClient okHttpClient;
    private Handler mHandler;

    private MyHttpUtils() {
        okHttpClient = new OkHttpClient();
        okHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        okHttpClient.setReadTimeout(20, TimeUnit.SECONDS);
        okHttpClient.setWriteTimeout(10, TimeUnit.SECONDS);
        //cookie
        okHttpClient.setCookieHandler(new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER));

        mHandler = new Handler(Looper.getMainLooper());//在主UI中创建
    }

    private static MyHttpUtils getInstance() {
        if (mInstance == null) {
            synchronized (MyHttpUtils.class) {
                if (mInstance == null) {
                    mInstance = new MyHttpUtils();
                }
            }
        }
        return mInstance;
    }

    /**
     * 同步get
     *
     * @param url
     */
    private Response getSync(String url) {
        final Request request = new Request.Builder().url(url).build();
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return response;
    }

    /**
     * 异步
     * get方法
     *
     * @param url
     * @param resultCallBack
     */
    private void getRequest(String url, ResultCallBack resultCallBack) {
        final Request request = new Request.Builder().url(url).build();
        deliverResult(request, resultCallBack);
    }

    /**
     * 同步post
     *
     * @param url
     * @param map
     * @return
     */
    private Response postSync(String url, HashMap<String, String> map) {
        Request request = buildRequestBody(map, url);
        Call call = okHttpClient.newCall(request);
        Response response = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;
    }

    /**
     * 异步
     * post方法
     *
     * @param url
     * @param resultCallBack
     * @param map            key和value键值对
     */
    private void postRequest(String url, ResultCallBack resultCallBack, HashMap<String, String> map) {
        Request request = buildRequestBody(map, url);
        deliverResult(request, resultCallBack);
    }

    /**
     * 基于异步post上传文件
     *
     * @param url
     * @param resultCallBack
     * @param files
     * @param fileKeys
     * @param params
     */
    private void postUpload(String url, ResultCallBack resultCallBack, File[] files, String[] fileKeys, Param... params) {
        Request request = buildMultipartFormRequest(url, files, fileKeys, params);
        deliverResult(request, resultCallBack);
    }

    /**
     * 单个文件上传
     *
     * @param url
     * @param resultCallBack
     * @param file
     * @param fileKey
     * @param params
     */
    private void postUpload(String url, ResultCallBack resultCallBack, File file, String fileKey, Param... params) {
        Request request = buildMultipartFormRequest(url, new File[]{file}, new String[]{fileKey}, params);
        deliverResult(request, resultCallBack);
    }

    /**
     * 通过参数和url来构建request对象
     *
     * @param map 参数
     * @param url
     * @return
     */
    private Request buildRequestBody(HashMap<String, String> map, String url) {
        FormEncodingBuilder builder = new FormEncodingBuilder();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        RequestBody requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        return request;
    }

    /**
     * 构建文件类型的request
     *
     * @param url
     * @param files
     * @param fileKeys
     * @param params
     * @return
     */
    private Request buildMultipartFormRequest(String url, File[] files,
                                              String[] fileKeys, Param[] params) {
        params = validateParam(params);

        MultipartBuilder builder = new MultipartBuilder()
                .type(MultipartBuilder.FORM);

        for (Param param : params) {
            builder.addPart(Headers.of("Content-Disposition", "form-data; name=\"" + param.key + "\""),
                    RequestBody.create(null, param.value));
        }
        if (files != null) {
            RequestBody fileBody = null;
            for (int i = 0; i < files.length; i++) {
                File file = files[i];
                String fileName = file.getName();
                fileBody = RequestBody.create(MediaType.parse(guessMimeType(fileName)), file);
                //TODO 根据文件名设置contentType
                builder.addPart(Headers.of("Content-Disposition",
                        "form-data; name=\"" + fileKeys[i] + "\"; filename=\"" + fileName + "\""),
                        fileBody);
            }
        }

        RequestBody requestBody = builder.build();
        return new Request.Builder()
                .url(url)
                .post(requestBody)
                .build();
    }

    /**
     * 异步下载文件
     *
     * @param url
     * @param destFileDir 本地文件存储的文件夹
     * @param callback
     */
    private void downloadAsyn(final String url, final String destFileDir, final ResultCallBack callback) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(final Request request, final IOException e) {
                sendFailureMessage(e, callback);
            }

            @Override
            public void onResponse(Response response) {
                InputStream is = null;
                byte[] buf = new byte[2048];
                int len = 0;
                FileOutputStream fos = null;
                try {
                    is = response.body().byteStream();
                    File file = new File(destFileDir, getFileName(url));
                    fos = new FileOutputStream(file);
                    while ((len = is.read(buf)) != -1) {
                        fos.write(buf, 0, len);
                    }
                    fos.flush();
                    //如果下载文件成功，第一个参数为文件的绝对路径
                    sendSuccessResult(file.getAbsolutePath(), callback);
                } catch (IOException e) {
                    sendFailureMessage(e, callback);
                } finally {
                    try {
                        if (is != null) is.close();
                    } catch (IOException e) {
                    }
                    try {
                        if (fos != null) fos.close();
                    } catch (IOException e) {
                    }
                }

            }
        });
    }

    private String getFileName(String path)
    {
        int separatorIndex = path.lastIndexOf("/");
        return (separatorIndex < 0) ? path : path.substring(separatorIndex + 1, path.length());
    }

    /**
     * 请求，回调
     *
     * @param request
     * @param resultCallBack
     */
    private void deliverResult(Request request, final ResultCallBack resultCallBack) {
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Request request, IOException e) {
                sendFailureMessage(e, resultCallBack);
            }

            @Override
            public void onResponse(Response response) throws IOException {
                //必须放在此处，线程不能切换
                String result = response.body().string();
                sendSuccessResult(result, resultCallBack);
            }
        });
    }

    /**
     * 成功加载
     *
     * @param result
     * @param resultCallBack
     */
    protected void sendSuccessResult(final Object result, final ResultCallBack resultCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (resultCallBack != null) {
                    resultCallBack.onResponse(result);
                }
            }
        });
    }

    /**
     * 加载失败
     *
     * @param e
     * @param resultCallBack
     */
    protected void sendFailureMessage(final Exception e, final ResultCallBack resultCallBack) {
        mHandler.post(new Runnable() {
            @Override
            public void run() {
                if (resultCallBack != null) {
                    resultCallBack.onFailure(e);
                }
            }
        });
    }


    //---------------------对外接口---------------------------

    public static void _get(String url, ResultCallBack resultCallBack) {
        getInstance().getRequest(url, resultCallBack);
    }

    public static void _getSync(String url){
        getInstance().getSync(url);
    }

    public static void _post(String url, ResultCallBack resultCallback, HashMap<String, String> params) {
        getInstance().postRequest(url, resultCallback, params);
    }

    public static void _postSync(String url, HashMap<String,String> map){
        getInstance().postSync(url, map);
    }

    public static void upLoad(String url, ResultCallBack resultCallBack, File[] files, String[] fileKeys, Param... params){
        getInstance().postUpload(url, resultCallBack, files, fileKeys, params);
    }

    public static void upLoad(String url, ResultCallBack resultCallBack, File file, String fileKey, Param... params){
        getInstance().postUpload(url, resultCallBack, file, fileKey, params);
    }

    public static void downLoad(String url ,String dir, ResultCallBack resultCallback){
        getInstance().downloadAsyn(url, dir, resultCallback);
    }



    /**
     * 回调的接口
     *
     * @param <T>
     */
    public interface ResultCallBack<T> {
        public void onResponse(T response);

        public void onFailure(Exception e);
    }

    /**
     * 检查参数是否合法
     *
     * @param params
     * @return
     */
    private Param[] validateParam(Param[] params) {
        if (params == null)
            return new Param[0];
        else return params;
    }

    private String guessMimeType(String path) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if (contentTypeFor == null) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }
}
