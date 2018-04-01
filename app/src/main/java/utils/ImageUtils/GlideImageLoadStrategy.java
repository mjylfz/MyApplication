package utils.ImageUtils;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;

import com.bumptech.glide.Glide;
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.data.DataFetcher;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.stream.StreamModelLoader;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.example.john.myapplication.R;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by LFZ on 2017/3/15.
 * glide框架的封装
 */

public class GlideImageLoadStrategy implements BaseImageLoadStrategy {


    public GlideImageLoadStrategy(){
    }

    /**
     * 实现接口中方法进行加载
     *
     * @param context
     * @param imageLoader
     */
    @Override
    public void loadImage(Context context, ImageLoader imageLoader) {
        if(imageLoader.getInternet()){
            Glide.with(context).load(imageLoader.getUrl()).placeholder(imageLoader.getPlaceHolder())
                    .error(R.mipmap.ic_image_loadfail).crossFade().diskCacheStrategy(DiskCacheStrategy.ALL).into(imageLoader.getImg());
        }else{
            Glide.with(context).using(cacheOnlyStreamLoader).load(imageLoader.getUrl()).placeholder(imageLoader.getPlaceHolder())
                    .error(R.mipmap.ic_image_loadfail).crossFade().into(imageLoader.getImg());
        }

    }

    @Override
    public void loadRoundResouce(final Context context, final ImageLoader imageLoader) {
        Glide.with(context).load(Integer.parseInt(imageLoader.getUrl())).asBitmap().into(new BitmapImageViewTarget(imageLoader.getImg()) {
            @Override
            protected void setResource(Bitmap resource) {
                RoundedBitmapDrawable circularBitmapDrawable =
                        RoundedBitmapDrawableFactory.create(context.getResources(), resource);
                circularBitmapDrawable.setCircular(true);
                imageLoader.getImg().setImageDrawable(circularBitmapDrawable);
            }

        });
    }


    private static StreamModelLoader<String> cacheOnlyStreamLoader = new StreamModelLoader<String>() {
        @Override
        public DataFetcher<InputStream> getResourceFetcher(final String model, int width, int height) {
            return new DataFetcher<InputStream>() {
                @Override
                public InputStream loadData(Priority priority) throws Exception {
                    throw new IOException();
                }

                @Override
                public void cleanup() {

                }

                @Override
                public String getId() {
                    return model;
                }

                @Override
                public void cancel() {

                }
            };
        }
    };
}
