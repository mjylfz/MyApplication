package topviewpagerwidget;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by LFZ on 2017/7/23.
 * 轮播Adapter
 */

public class ViewAdapter extends FragmentPagerAdapter {
    public List<Fragment> fragmentList;
    public ViewAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    public void setFragmentList(List<Fragment> fList){
        this.fragmentList = fList;
        notifyDataSetChanged();
    }
}
