package utils.ImageUtils;

import android.widget.ImageView;

import android.R;


/**
 * Created by LFZ on 2017/3/15.
 * 封装了加载图片属性，使用Builder生成器模式
 * Builder在构造方法中设置默认值，成员方法中设置属性并返回本身类的实例，可以重复设置，
 * build()方法返回ImageLoader实例，并已经设置好属性
 */

public class ImageLoader {

    private String url;
    private int placeHolder;
    private int type;
    private ImageView img;
    private boolean internet;
    Builder builder;
    public ImageLoader(Builder builder){
        this.builder = builder;
        this.url = builder.url;
        this.placeHolder = builder.placeHolder;
        this.type = builder.type;
        this.img = builder.img;
        this.internet = builder.internet;
    }

    public ImageView getImg() {
        return img;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public String getUrl() {
        return url;
    }

    public int getType() {
        return type;
    }

    public boolean getInternet(){
        return internet;
    }

    public static class Builder{
        private String url;
        private int placeHolder;
        private int type;
        private ImageView img;
        private boolean internet;

        public Builder(){
            //设定默认值
            this.type = 0;
            this.placeHolder = com.example.john.myapplication.R.mipmap.ic_image_loading;
            this.img = null;
            this.url = "";
            this.internet = true;
        }

        public Builder type(int type){
            this.type = type;
            return this;
        }

        public Builder placeHolder(int placeHolder){
            this.placeHolder = placeHolder;
            return this;
        }

        public Builder imgView(ImageView img){
            this.img = img;
            return this;
        }

        public Builder url(String url){
            this.url = url;
            return this;
        }

        public Builder internet(boolean internet){
            this.internet = internet;
            return this;
        }

        public ImageLoader builder(){
            return new ImageLoader(this);
        }
    }
}
