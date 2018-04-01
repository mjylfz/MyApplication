package utils.ImageUtils;

import android.content.Context;

/**
 * Created by LFZ on 2017/3/15.
 * 采用策略模式封装的图片加载类，持有ImageLoader和上下文，交给strategy进行加载
 */

public class ImageLoadUtils {

    private static ImageLoadUtils mInstance;
    private BaseImageLoadStrategy imageLoadStrategy;

    public ImageLoadUtils(){
        imageLoadStrategy = new GlideImageLoadStrategy();//使用glide封装的图片加载框架
    }

    public static ImageLoadUtils getInstance(){
        if(mInstance == null){
            synchronized (ImageLoadUtils.class){
                if(mInstance == null){
                    mInstance = new ImageLoadUtils();
                    return mInstance;
                }
            }
        }

        return mInstance;
    }

    /**
     * 加载图片
     * @param context
     * @param imageLoader
     */
    public void loadImage(Context context , ImageLoader imageLoader){
        imageLoadStrategy.loadImage(context,imageLoader);//交给BaseImageLoadStategy对象处理
    }

    public void loadRoundImage(Context context , ImageLoader imageLoader){
        imageLoadStrategy.loadRoundResouce(context,imageLoader);
    }

    public void setImageLoadStrategy(BaseImageLoadStrategy imageLoadStrategy) {
        this.imageLoadStrategy = imageLoadStrategy;
    }
}
