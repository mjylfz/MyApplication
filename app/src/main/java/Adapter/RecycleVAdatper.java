package Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.john.myapplication.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by LFZ on 2017/7/15.
 */

public class RecycleVAdatper extends RecyclerView.Adapter<RecycleVAdatper.ViewHolder> {

    Context context;
    List<String> data = new ArrayList<>();

    public RecycleVAdatper(Context context, List<String> data){
        this.context = context;
        this.data = data;
    }

    @Override
    public RecycleVAdatper.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_rv,null);
        ViewHolder viewHolder = new ViewHolder(itemView);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecycleVAdatper.ViewHolder holder, int position) {
        holder.tv.setText(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public List<String> getData() {
        return data;
    }


    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv;

        public ViewHolder(View itemView) {
            super(itemView);
            tv = (TextView)itemView.findViewById(R.id.t);
        }
    }
}
