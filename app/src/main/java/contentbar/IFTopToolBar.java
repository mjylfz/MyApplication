package contentbar;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.john.myapplication.R;

import java.util.ArrayList;
import java.util.List;

import utils.IFHelper;

/**
 * Created by LFZ on 2017/7/15.
 * 搜索框
 */

public abstract class IFTopToolBar extends LinearLayout {

    int toolBarHeight;
    ImageView imageSearch;
    TextView textSearch;
    TextView textSearchEdit;
    EditText editSearch;
    LinearLayout unEditSearch;
    LinearLayout editSearchView;
    TextView cancel;
    TextWatcher watcher;
    List<TextWatcher> watcherList = new ArrayList<>();

    final String TAG = "IFTopToolBar";

    public IFTopToolBar(Context context) {
        super(context);
        toolBarHeight = IFHelper.dp2px(getContext(),50);


        initWidget(context);
        initLayout(context);
        initListener(context);

    }

    private void initWidget(Context context){
        this.setOrientation(HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,toolBarHeight);
        this.setLayoutParams(params);
        this.setGravity(Gravity.CENTER_VERTICAL);
        this.setBackgroundColor(Color.parseColor("#3a454e"));
        this.getBackground().setAlpha(49);

        unEditSearch = new LinearLayout(context);
        LinearLayout.LayoutParams uneditp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        int margin = IFHelper.dp2px(getContext(),8);
        uneditp.setMargins(margin,margin,margin,margin);
        unEditSearch.setLayoutParams(uneditp);
        unEditSearch.setOrientation(HORIZONTAL);
        unEditSearch.setGravity(Gravity.CENTER);
        unEditSearch.setBackgroundColor(Color.parseColor("#ffffff"));

        this.addView(unEditSearch);

        //点击之后的View
        editSearchView = new LinearLayout(context);
        LinearLayout.LayoutParams editp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        editp.setMargins(margin,margin,margin,margin);
        editSearchView.setLayoutParams(editp);
        editSearchView.setOrientation(HORIZONTAL);
        editSearchView.setGravity(Gravity.CENTER_VERTICAL);
        this.addView(editSearchView);
    }

    private void initLayout(Context context){

        //搜索图标
        imageSearch = new ImageView(context);
        imageSearch.setImageResource(R.mipmap.fr_icon_search_normal);
        imageSearch.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        unEditSearch.addView(imageSearch);
        //搜索文字
        textSearch = new TextView(context);
        textSearch.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        textSearch.setTextColor(Color.parseColor("#999999"));
        textSearch.setTextSize(12);
        textSearch.setText("搜索");
        unEditSearch.addView(textSearch);


        //内部搜索的条
        LinearLayout editLinear = new LinearLayout(context);
        LinearLayout.LayoutParams editp = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        editp.weight = 1;
        editLinear.setLayoutParams(editp);
        int padding = IFHelper.dp2px(getContext(),8);
        editLinear.setPadding(padding,0,padding,0);
        editLinear.setOrientation(HORIZONTAL);
        editLinear.setGravity(Gravity.CENTER_VERTICAL);
        editLinear.setBackgroundColor(Color.parseColor("#ffffff"));

        ImageView imgSearch = new ImageView(context);
        imgSearch.setImageResource(R.mipmap.fr_icon_search_normal);
        imgSearch.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        editLinear.addView(imgSearch);

        editSearch = new EditText(context);
        editSearch.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT));
        editSearch.setHint("搜索");
        editSearch.setTextSize(12);
        editSearch.setBackgroundColor(Color.TRANSPARENT);
        editLinear.addView(editSearch);


        //取消按钮
        cancel = new TextView(context);
        LinearLayout.LayoutParams tp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
        tp.setMargins(10,10,10,10);
        tp.weight = 0;
        cancel.setLayoutParams(tp);
        cancel.setText("取消");
        cancel.setTextColor(Color.parseColor("#000099"));
        cancel.setTextSize(18);
        editSearchView.addView(editLinear);
        editSearchView.addView(cancel);

    }

    private void initListener(Context context){
        unEditSearch.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchEdit(true);
            }
        });

        cancel.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                switchEdit(false);
            }
        });
        this.editSearch.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                IFTopToolBar.this.onFocusChange(hasFocus);
            }
        });

        initTextWatcher();
    }

    private void switchEdit(boolean edit){
        if(edit){
            unEditSearch.setVisibility(View.GONE);
            editSearchView.setVisibility(View.VISIBLE);
            editSearch.requestFocus();
            editSearch.setFocusable(true);
            editSearch.setFocusableInTouchMode(true);
            enterEdit();
        }else{
            unEditSearch.setVisibility(View.VISIBLE);
            editSearchView.setVisibility(View.GONE);
            editSearch.setText("");
            editSearch.clearFocus();
            removeTextWatcher();
            exitEdit();
        }
    }

    private void onFocusChange(boolean focus){
        if(focus){
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(editSearch, InputMethodManager.SHOW_FORCED);// 显示输入法
        }else{
            InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(((Activity)getContext()).getWindow().getDecorView().getWindowToken(), 0);
        }
    }

    private void initTextWatcher(){
        if(watcher == null){
            this.editSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    fData(String.valueOf(s));
                }
            });
        }
    }

    private void removeTextWatcher(){
        for(TextWatcher watcher:watcherList){
            editSearch.removeTextChangedListener(watcher);
        }
        watcherList.clear();
    }

    public void addTextWatcher(TextWatcher w){
        if(!watcherList.contains(w)){
            editSearch.addTextChangedListener(w);
            watcherList.add(w);
        }
    }


    public abstract void enterEdit();

    public abstract void exitEdit();

    public abstract void fData(String data);

}
