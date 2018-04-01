package image;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.john.myapplication.BuildConfig;
import com.example.john.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;

import utils.EncryptionUtils;
import utils.ImageUtils.ImageZip;

/**
 * Created by LFZ on 2017/10/11.
 * 图片
 */

public class BitmapActivity extends AppCompatActivity implements View.OnClickListener{

    private ImageView img;
    private Button btn_quality;
    private Button btn_sample;
    private Button btn_scale;

    private Button btn_show;
    private Button btn_show1;
    Button code;
    Button code2;

    TextView text;
    TextView code1;

    Bitmap bitmap = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zip);

        findId();
        initListener();
    }

    private void findId() {
        img = (ImageView)findViewById(R.id.image);
        btn_quality = (Button)findViewById(R.id.zipqulity);
        btn_sample = (Button)findViewById(R.id.zipSample);
        btn_scale = (Button)findViewById(R.id.zipScale);

        btn_show = (Button)findViewById(R.id.show);
        btn_show1 = (Button)findViewById(R.id.s);
        code = (Button)findViewById(R.id.code);
        code2 = (Button)findViewById(R.id.code1);
        text = (TextView)findViewById(R.id.text);
        code1 = (TextView)findViewById(R.id.test);
    }
    private void initListener(){
        btn_quality.setOnClickListener(this);
        btn_sample.setOnClickListener(this);
        btn_scale.setOnClickListener(this);
        btn_show.setOnClickListener(this);
        btn_show1.setOnClickListener(this);
        code.setOnClickListener(this);
        code2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.zipqulity:
                bitmap = zipQuality();
                break;
            case R.id.zipSample:
                bitmap = zipSample();
                break;
            case R.id.zipScale:
                bitmap = zipScale();
                break;
            case R.id.show:
//                showAlertDialog();
//                String apiServerUrl = BuildConfig.FILE_STRING;
//                Toast.makeText(this,apiServerUrl+"",Toast.LENGTH_SHORT).show();
                String json = getJsonFromAssets(this, "a.json");
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject = rebuildJson(new JSONObject(json));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                Toast.makeText(this,jsonObject.toString(), Toast.LENGTH_SHORT).show();

                break;
            case R.id.code:
                try {
                    String color = "#1FADE5";
                    int co = Color.parseColor(color);
                    code.setBackgroundColor(co);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case R.id.code1:
                try {
                    compressFile();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
        if(bitmap != null){
            img.setImageBitmap(bitmap);
        }
    }

    private void showAlertDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("haha").setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.show();
        final AlertDialog dialog =builder.create();
                Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                ((DialogInterface)dialog).cancel();
                ((DialogInterface)dialog).dismiss();
            }
        },2000);
    }

    private Bitmap zipQuality() {
        return ImageZip.zipBitmapByQuality(BitmapFactory.decodeResource(getResources(),R.drawable.background),50);
    }

    private Bitmap zipSample() {
        return ImageZip.zipBitmapBySample(this, R.drawable.image, 500, 500);
    }

    private Bitmap zipScale() {
        return ImageZip.createScaledBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.background),200, 200);
    }

    private boolean compressFile(){
        String imagePath = Environment.getExternalStorageDirectory() + "/cat.jpg";
        String newPath = Environment.getExternalStorageDirectory() + "/miao.jpg";
        return ImageZip.compressFile(imagePath, newPath, "png");
    }

    private void releaseImageViewResource(ImageView imageView){
        if(imageView == null){
            return;
        }

        Drawable drawable = imageView.getDrawable();
        if(drawable !=null && drawable instanceof BitmapDrawable){
            Bitmap bitmap1= ((BitmapDrawable)drawable).getBitmap();
            if(bitmap1 != null &&!bitmap1.isRecycled()){
                bitmap1.recycle();
                bitmap1 = null;
            }
        }

        if(bitmap != null){
            bitmap.recycle();
            bitmap = null;
        }
    }


    /**
     * 从asset路径下读取对应文件转String输出
     * @param mContext
     * @return
     */
    public  String getJsonFromAssets(Context mContext, String fileName) {
        // TODO Auto-generated method stub
        StringBuilder sb = new StringBuilder();
        AssetManager am = mContext.getAssets();
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(
                    am.open(fileName)));
            String next = "";
            while (null != (next = br.readLine())) {
                sb.append(next);
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            sb.delete(0, sb.length());
        }
        return sb.toString().trim();
    }



    private JSONArray localizedInfo;

    public  JSONObject rebuildJson(JSONObject jsonObject) throws JSONException {
        //国际化的key
        this.localizedInfo = jsonObject.getJSONArray("localizedInfo");

        //传给RN的内容
        JSONObject OEMInfo = jsonObject.getJSONObject("oeminfo");
        //做国际化
        JSONObject resultJson = (JSONObject) getResultJson(OEMInfo);
        return resultJson;
    }


    /**
     * 把json中需要国际化的内容国际化
     * @param OEMInfo
     * @return
     * @throws JSONException
     */
    public  Object getResultJson(Object OEMInfo) throws JSONException {
        if(OEMInfo instanceof JSONObject){
            for (Iterator iter = ((JSONObject)OEMInfo).keys(); iter.hasNext();) {
                String key = (String) iter.next();
                Object value = ((JSONObject)OEMInfo).get(key);
                //String类型并且是需要国际化的字段
                if(value instanceof String && hasKey(localizedInfo, key)){
                    ((JSONObject)OEMInfo).put(key, this.getString(this.getResources().getIdentifier((String)value,"string",this.getPackageName())));
                }else{
                    getResultJson(value);
                }

            }
        }else if(OEMInfo instanceof JSONArray){
            for(int i = 0;i <((JSONArray)OEMInfo).length();i++){
                getResultJson(((JSONArray)OEMInfo).get(i));
            }
        }

        return OEMInfo;
    }

    private boolean hasKey(JSONArray jsonArray , String key) throws JSONException {
        for(int i = 0;i < jsonArray.length(); i++){
            if(jsonArray.get(i).equals(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        releaseImageViewResource(img);
    }

}
