package utils.ImageUtils;

import android.content.Context;

/**
 * Created by LFZ on 2017/3/15.
 * 每个图片类框架的抽象接口，使用策略模式
 */

public interface BaseImageLoadStrategy {
    void loadImage(Context context, ImageLoader imageLoader);
    void loadRoundResouce(Context context, ImageLoader imageLoader);
}
