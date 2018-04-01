package topviewpagerwidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.john.myapplication.R;

import utils.ImageUtils.ImageLoadUtils;
import utils.ImageUtils.ImageLoader;

/**
 * Created by LFZ on 2017/7/23.
 * 每个页面
 */

public class TopViewPagerFragment extends BaseFragment{

    ImageView imageView;
    TextView textView;
    View view;


    public static TopViewPagerFragment getInstance(String textSize,String url){
        TopViewPagerFragment fragment = new TopViewPagerFragment();
        Bundle bundle = new Bundle();
        bundle.putString("title",textSize);
        bundle.putString("url",url);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view =  super.onCreateView(inflater, container, savedInstanceState);
        initListener();
        initData();
        loadData();
        return view;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.toppage;
    }

    @Override
    protected void initView() {
        imageView = findViewById(R.id.img);
        textView = findViewById(R.id.textview);
    }

    @Override
    protected void initListener() {
    }

    private void initData(){
        Bundle bundle = getArguments();
        String title = bundle.getString("title");
        String url = bundle.getString("url");

        textView.setText(title);
        ImageLoader imgData = new ImageLoader.Builder().imgView(imageView).url(url).internet(true).builder();
        ImageLoadUtils.getInstance().loadImage(getContext(),imgData);

    }

    @Override
    protected void loadData() {
    }

    @Override
    public void onClick(View v) {

    }
}
