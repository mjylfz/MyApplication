package utils.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import com.example.john.myapplication.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by LFZ on 2017/10/10.
 * 图片压缩的工具类
 */

public class ImageZip {
    private static int DEFAULT_MAX_SQUARE = 10000;

    /**
     * base64转Bitmap
     * inSampleSize是采样率
     * 第一次decodeByteArray返回空的Bitmap，测量图片尺寸，计算压缩的比例
     * 第二次decodeByteArray进行图片压缩
     *
     * @param image64String 从服务器传来的图片资源字符串
     * @return 返回图片
     */
    public static Bitmap createBitmapWithString(String image64String) {
        if (TextUtils.isEmpty(image64String)) {
            return null;
        }
        byte[] image = null;
        try {
            image = Base64.decode(image64String, Base64.DEFAULT);
            BitmapFactory.Options opts = new BitmapFactory.Options();

            opts.inJustDecodeBounds = true;     //读取图片尺寸
            BitmapFactory.decodeByteArray(image, 0, image.length, opts);

            //计算缩放比例
            int scale = 1;
            for (int i = 0; i < Integer.MAX_VALUE; i++) {
                if (opts.outWidth * opts.outHeight / (scale * scale) > DEFAULT_MAX_SQUARE) {
                    scale++;
                } else {
                    break;
                }
            }
            opts.inSampleSize = scale;
            opts.inJustDecodeBounds = false;    //读取图片内容
            opts.inPreferredConfig = Bitmap.Config.RGB_565;

            //压缩
            Bitmap bitmap = BitmapFactory.decodeByteArray(image, 0, image.length, opts);
            return bitmap;
        } catch (Exception e) {
            return null;
        } finally {
            image = null;
        }
    }



    /**
     * 容易OOM
     * 按照质量压缩，图片的大小是没有变的，因为质量压缩不会减少图片的像素，所以bitmap占用内存不变
     * @param bitmap 位图
     * @param quality 比例
     * @return
     */
    public static Bitmap zipBitmapByQuality(Bitmap bitmap, int quality){
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            //根据质量压缩到baos中
            bitmap.compress(Bitmap.CompressFormat.JPEG,quality, baos);
            byte[] image = baos.toByteArray();
            //容易OOM,加一步
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inPreferredConfig = Bitmap.Config.RGB_565;
//        options.inSampleSize = 8;
            bitmap = BitmapFactory.decodeByteArray(image, 0 ,image.length);     //容易OOM
        }catch (OutOfMemoryError e){
            Log.e("zipBitmapByQuality",e.getMessage());
            bitmap = null;
        }

        return bitmap;
    }

    /**
     * 采样率压缩，和rgb的压缩，第一次先测尺寸，第二次再进行压缩
     * @param context
     * @param imageId
     * @param maxHeight
     * @param maxWidth
     * @return
     */
    public static Bitmap zipBitmapBySample(Context context, int imageId, int maxHeight, int maxWidth){
        Bitmap bitmap = null;
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;//读取图片尺寸
            //测量尺寸，返回空值
            BitmapFactory.decodeResource(context.getResources(), imageId, options);

            int width = options.outWidth;
            int height = options.outHeight;

            //计算缩放比例
            int scale = getScale(width, height, maxWidth ,maxHeight);
            options.inSampleSize = scale;
            options.inJustDecodeBounds = false; //读取图片内容
            options.inPreferredConfig = Bitmap.Config.RGB_565;

            bitmap =  BitmapFactory.decodeResource(context.getResources(), imageId, options);
        }catch (Exception e){
            Log.e("zipBitmapBySample",e.getMessage());
        }

        return bitmap;
    }

    /**
     * 压缩指定的宽高，可能会导致失真
     * @param bitmap
     * @param width
     * @param height
     * @return
     */
    public static Bitmap createScaledBitmap(Bitmap bitmap, int width, int height){
        Bitmap bm = null;
        try {
            bm = Bitmap.createScaledBitmap(bitmap,width, height, true);
        }catch (Exception e){
            Log.e("createScaledBitmap",e.getMessage());
        }
        return bm;
    }

    public static boolean compressFile(String imagePath, String newPath, String format){
        //
        File compressedPictureFile = new File(newPath);
        if(!compressedPictureFile.exists()){
            if(!compressedPictureFile.getParentFile().exists()){
                compressedPictureFile.getParentFile().mkdirs();
            }
            try {
                compressedPictureFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath);
        FileOutputStream fOut = null;
        boolean compressed = false;
        try {
            fOut = new FileOutputStream(compressedPictureFile);
            compressed = bitmap.compress(Bitmap.CompressFormat.JPEG, 50, fOut);
            fOut.flush();
            fOut.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return compressed;
    }


    //----------------------------------

    private static int getScale(int width, int height, int maxWidth, int maxHeight){
        int scale = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (width/ scale > maxWidth || height / scale >maxHeight) {
                scale++;
            } else {
                break;
            }
        }
        return scale;
    }

    private static int getScale(int width ,int height ,int maxSquare){
        //计算缩放比例
        int scale = 1;
        for (int i = 0; i < Integer.MAX_VALUE; i++) {
            if (width * height / (scale * scale) > maxSquare) {
                scale++;
            } else {
                break;
            }
        }
        return scale;
    }
}
