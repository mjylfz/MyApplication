package topviewpagerwidget;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LFZ on 2017/7/23.
 * 轮播设置
 */

public class WidgetModel {

    private List<String> data;
    private int length;
    private boolean touchStop;
    private boolean auto;
    private long time;

    public int getLength() {
        return length;
    }

    public WidgetModel setLength(int length) {
        this.length = length;
        return this;
    }

    public boolean isTouchStop() {
        return touchStop;
    }

    public WidgetModel setTouchStop(boolean touchStop) {
        this.touchStop = touchStop;
        return this;
    }

    public boolean isAuto() {
        return auto;
    }

    public WidgetModel setAuto(boolean auto) {
        this.auto = auto;
        return this;
    }

    public long getTime() {
        return time;
    }

    public List<String> getData() {
        return data;
    }

    public WidgetModel setData(List<String> data) {
        this.data = data;
        return this;
    }

    public WidgetModel setTime(long time) {
        this.time = time;
        return this;
    }

    public WidgetModel(){
        this.length = 0;
        this.touchStop = false;
        this.auto = true;
        this.time = 2000;
        this.data = new ArrayList<>();
    }
}
