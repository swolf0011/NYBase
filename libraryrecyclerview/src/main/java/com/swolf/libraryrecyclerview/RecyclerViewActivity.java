package com.swolf.libraryrecyclerview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.swolf.libraryrecyclerview.recyclerview.NYRecyclerViewAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ly on 2017/10/13.
 */

public class RecyclerViewActivity extends AppCompatActivity {
    /**
     * 跳转
     *
     * @param activity
     */
    public static void toActivity(Activity activity) {
        activity.startActivity(new Intent(activity, RecyclerViewActivity.class));
    }
    Button button;
    RecyclerView recyclerView;
    NYRecyclerViewAdapter<String, MyViewHolder> adapter;

    List<String> list = new ArrayList<String>();

    int i = 2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view);

        for (int i = 0; i < 10; i++) {
            list.add("Abc-" + i);
        }

        button = (Button) findViewById(R.id.button);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        adapter = new NYRecyclerViewAdapter<String, MyViewHolder>(this, list, R.layout.item_main) {


            @Override
            public void getViewHolder(MyViewHolder holder, final int position,final String str) {
                holder.textView.setText(str);
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(RecyclerViewActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                    }
                });

                holder.button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        adapter.removeNotifyItem(position);
                    }
                });
            }

            @Override
            public MyViewHolder createViewHolder(View view) {
                return new MyViewHolder(view);
            }

        };

        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        //设置Item增加、移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        recyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                adapter.insertedNotifyItem("ddd"+i++);
            }
        });

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button button;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.textView);
            button = (Button) itemView.findViewById(R.id.button);
        }
    }


}