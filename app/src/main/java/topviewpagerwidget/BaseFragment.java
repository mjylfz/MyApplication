package topviewpagerwidget;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by LFZ on 2017/7/23.
 */

public abstract class BaseFragment extends Fragment implements View.OnClickListener{

    private View convertView;
    private SparseArray<View> mView = new SparseArray<>();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        convertView = inflater.inflate(getLayoutId(),null);
        initView();

        return convertView;
    }

    public  <T extends View> T findViewById(int id){
        T view = (T)mView.get(id);
        if(view == null){
            view = (T)convertView.findViewById(id);
            mView.put(id,view);
        }
        return view;
    }

    public <T extends View> void setClickListener(T view){
        view.setOnClickListener(this);
    }

    protected abstract int getLayoutId();

    protected abstract void initView();

    protected abstract void initListener();

    protected abstract void loadData();
}
