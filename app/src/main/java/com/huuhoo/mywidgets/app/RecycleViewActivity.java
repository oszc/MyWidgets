package com.huuhoo.mywidgets.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.huuhoo.mywidgets.app.utils.MyLinearLayoutManager;

import java.util.ArrayList;
import java.util.List;

public class RecycleViewActivity extends AppCompatActivity {

    @Bind(R.id.recycleview)
    RecyclerView mRecycleview;

    private List<String> mData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycle_view);
        ButterKnife.bind(this);
        initDataSheet();

        mRecycleview.setLayoutManager(new LinearLayoutManager(this));
        mRecycleview.setLayoutManager(new MyLinearLayoutManager());
        //mRecycleview.setLayoutManager(new GridLayoutManager(this,2,GridLayoutManager.HORIZONTAL,false));
        //mRecycleview.setLayoutManager(new PageGridLayoutManager());
        //mRecycleview.addItemDecoration(new DividerItemDecoration(this,DividerItemDecoration.VERTICAL_LIST));
        mRecycleview.setAdapter(new HomeAdapter());
    }

    private void initDataSheet() {
        mData = new ArrayList<String>();
        for (int i = 0; i < 100000; i++) {
            mData.add(i + "");
        }
    }

    class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder> {
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.item_home, null);
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(mRecycleview.getWidth(), ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutParams);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
        }

        @Override
        public int getItemCount() {
            return mData.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                tv = (TextView) itemView.findViewById(R.id.tv);
            }
        }
    }
}
