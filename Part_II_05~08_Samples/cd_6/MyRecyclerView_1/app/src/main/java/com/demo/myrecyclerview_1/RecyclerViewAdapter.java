package com.demo.myrecyclerview_1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

/**
 * Created by Paul on 2/10/2018.
 */
public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    final static String TAG = "RecyclerViewAdapter";

    Context context;
    LinearLayout view;
    ViewHolder viewHolder;

    final int [] resId = {
            R.drawable.icon_01,
            R.drawable.icon_02,
            R.drawable.icon_03,
            R.drawable.icon_04,
            R.drawable.icon_05,
            R.drawable.icon_06,
            R.drawable.icon_07,
            R.drawable.icon_08,
            R.drawable.icon_09,
            R.drawable.icon_10
    };
    String [] data = {"陳一", "林二", "張三", "李四", "王五",
            "小六", "川七", "老八", "馬九", "全十"};
    
    public RecyclerViewAdapter(Context context){
        this.context = context;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public ImageView imageView;
        public TextView textView;

        public ViewHolder(View v){
            super(v);
            // 圖片元件
            imageView = (ImageView) v.findViewById(R.id.imageView1);
            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) imageView.getLayoutParams();
            params.width = 120;
            params.height = 120;
            imageView.setLayoutParams(params);
            // 文字元件
            textView = (TextView) v.findViewById(android.R.id.text1);
        }
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType){

        view = (LinearLayout) LayoutInflater.from(context)
                                .inflate(R.layout.my_composite,parent,false);
        view.setOrientation(LinearLayout.VERTICAL);
        viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position){
        // 載圖
        Glide.with(context).load(resId[position]).into(holder.imageView);
        // 載文
        holder.textView.setText(data[position]);
    }

    @Override
    public int getItemCount(){
        return resId.length;
    }

}
