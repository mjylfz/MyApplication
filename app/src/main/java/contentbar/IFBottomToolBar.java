package contentbar;

import android.content.Context;
import android.util.AttributeSet;
import android.util.LayoutDirection;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.john.myapplication.R;

import utils.IFHelper;

/**
 * Created by LFZ on 2017/7/15.
 */

public class IFBottomToolBar extends LinearLayout {

    int toolBarHeight;
    public IFBottomToolBar(Context context) {
        super(context);
        toolBarHeight = IFHelper.dp2px(context,40);
        initWidget(context);

    }

    private void initWidget(Context context){
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,toolBarHeight);
        params.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
        this.setLayoutParams(params);
        this.setOrientation(HORIZONTAL);
        this.setGravity(Gravity.CENTER_VERTICAL);

        LinearLayout l1 = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        layoutParams1.weight = 1;
        l1.setLayoutParams(layoutParams1);
        l1.setOrientation(VERTICAL);
        l1.setGravity(Gravity.CENTER);

        ImageView imageView = new ImageView(context);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView.setImageResource(R.mipmap.fr_icon_scancode);
        l1.addView(imageView);

        LinearLayout l2 = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        layoutParams2.weight = 1;
        l2.setLayoutParams(layoutParams1);
        l2.setOrientation(VERTICAL);

        ImageView imageView2 = new ImageView(context);
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView2.setImageResource(R.mipmap.fr_icon_scancode);
        l2.addView(imageView2);
        l2.setGravity(Gravity.CENTER);

        LinearLayout l5 = new LinearLayout(context);
        LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(0, LayoutParams.WRAP_CONTENT);
        layoutParams5.weight = 1;
        l5.setLayoutParams(layoutParams5);
        l5.setOrientation(VERTICAL);

        ImageView imageView5 = new ImageView(context);
        imageView5.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
        imageView5.setImageResource(R.mipmap.fr_icon_scancode);
        l5.addView(imageView5);
        l5.setGravity(Gravity.CENTER);

        this.addView(l1);
        this.addView(l2);
        this.addView(l5);
    }

}
