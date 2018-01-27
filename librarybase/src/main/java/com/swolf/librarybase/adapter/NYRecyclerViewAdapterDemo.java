package com.swolf.librarybase.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * RecyclerView通用适配器Demo
 * Created by LiuYi-15973602714
 */
public class NYRecyclerViewAdapterDemo{
    NYRecyclerViewAdapter<String, MyViewHolder> adapter;
    public void initView() {
//        adapter = new NYRecyclerViewAdapter<String, MyViewHolder>(activity, list, R.layout.item_demo_recycler_view) {
//            @Override
//            public void getViewHolder(NYRecyclerViewAdapterDemo.MyViewHolder holder, final int position, final String str) {
//                holder.textView.setText(str);
//                holder.itemView.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Toast.makeText(activity, list.get(position), Toast.LENGTH_SHORT).show();
//                    }
//                });
//
//                holder.button.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//
//                        adapter.removeNotifyItem(position);
//                    }
//                });
//            }
//
//            @Override
//            public NYRecyclerViewAdapterDemo.MyViewHolder createViewHolder(View view) {
//                return new NYRecyclerViewAdapterDemo.MyViewHolder(view);
//            }
//        };

//        final LinearLayoutManager layoutManager = new LinearLayoutManager(activity);
//        activity.recyclerView.setLayoutManager(layoutManager);
//        //设置Item增加、移除动画
//        activity. recyclerView.setItemAnimator(new DefaultItemAnimator());
//        //添加分割线
//        activity.recyclerView.addItemDecoration(new DividerItemDecoration(activity, DividerItemDecoration.VERTICAL));
//        activity.recyclerView.setAdapter(adapter);
//        activity.button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                adapter.insertedNotifyItem("ddd"+i++);
//            }
//        });
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
//            textView = (TextView) itemView.findViewById(R.id.textView);
//            button = (Button) itemView.findViewById(R.id.button);
        }
    }

}