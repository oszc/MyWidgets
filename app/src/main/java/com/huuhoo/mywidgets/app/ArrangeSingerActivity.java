package com.huuhoo.mywidgets.app;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class ArrangeSingerActivity extends AppCompatActivity {

    public static int createViewHolder = 1;
    public static int createTextView = 1;
    public static int createRecycleView = 1;
    private static final int COLUMN_COUNT = 3;
    private static final String TAG = ArrangeSingerActivity.class.getSimpleName().substring(0, 20);
    private ListView mListView;
    private ViewPager mViewPager;
    private GridLayout mGridLayout;
    private List<String> mData;
    private List<List<String>> mSingersData;
    private int[] mImageResource = {R.drawable.tb_one, R.drawable.tb_two, R.drawable.tb_three};
    private SurfaceView sv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrange_singer);

        mData = new ArrayList<String>(10);
        for (int i = 0; i < 100; i++) {
            mData.add(i + "");
        }

        mSingersData = new ArrayList<List<String>>();

        for (int j = 0; j < 100; j++) {
            List<String> list = new ArrayList<String>();
            for (int i = 0; i < 100; i++) {
                list.add((j * 100 + i) + "");
            }
            mSingersData.add(list);
        }


        mListView = (ListView) findViewById(R.id.lv);

        initViewPager();
        initGridLayout();

        //AbsListView.LayoutParams listViewLayoutParams = getListViewLayoutParams(AbsListView.LayoutParams.WRAP_CONTENT, AbsListView.LayoutParams.WRAP_CONTENT);
        AbsListView.LayoutParams listViewLayoutParams = getListViewLayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        TextView tv = new TextView(this);
        tv.setLayoutParams(listViewLayoutParams);
        tv.setTextColor(Color.WHITE);
        mListView.addHeaderView(tv);
        mListView.setAdapter(new MyAdapter(mData));
        mListView.setAdapter(new SingersAdapter(mSingersData));


    }

    private void initGridLayout() {
        mGridLayout = new GridLayout(this);
        AbsListView.LayoutParams params = getListViewLayoutParams(AbsListView.LayoutParams.MATCH_PARENT, AbsListView.LayoutParams.WRAP_CONTENT);
        mGridLayout.setLayoutParams(params);
        mGridLayout.setColumnCount(COLUMN_COUNT);
        mGridLayout.setUseDefaultMargins(true);
        mGridLayout.addView(drawGridItem(R.mipmap.xianxiachangge, "线下唱歌"));
        mGridLayout.addView(drawGridItem(R.mipmap.yinyuejiaoxue, "音乐教学"));
        mGridLayout.addView(drawGridItem(R.mipmap.xianshanggeshou, "线上歌手"));
        mGridLayout.addView(drawGridItem(R.mipmap.shipinliaotian, "视频聊天"));
        mGridLayout.addView(drawGridItem(R.mipmap.shengyou, "声优"));
        mGridLayout.addView(drawGridItem(R.mipmap.biaobaifuwu, "表白服务"));
        mListView.addHeaderView(mGridLayout);
    }

    private TextView drawGridItem(int res, String text) {
        TextView textView = null;

        textView = new TextView(this);
        GridLayout.LayoutParams params = new GridLayout.LayoutParams();

        int parentWidth = mListView.getMeasuredWidth();
        Log.e(TAG, "drawGridItem: parentWidth:" + parentWidth);
        //  params.width = 100;
        //  params.height = 500;

        GridLayout.Spec spec = GridLayout.spec(GridLayout.UNDEFINED, GridLayout.CENTER, 1.0f);

        //params.rowSpec = spec;
        params.columnSpec = spec;
        params.rowSpec = spec;
        params.setGravity(Gravity.FILL_HORIZONTAL);

        textView.setCompoundDrawablePadding(10);
        textView.setText(text);
        textView.setTextColor(Color.WHITE);

        //  textView.setCompoundDrawables();
        textView.setCompoundDrawablesWithIntrinsicBounds(null, getResources().getDrawable(res), null, null);
        textView.setLayoutParams(params);
        textView.setGravity(Gravity.CENTER_HORIZONTAL);

        return textView;

    }

    private void initViewPager() {
        mViewPager = new ViewPager(this);
        AbsListView.LayoutParams params = getListViewLayoutParams(AbsListView.LayoutParams.MATCH_PARENT, 300);
        //AbsListView.LayoutParams params = getListViewLayoutParams(AbsListView.LayoutParams.MATCH_PARENT,100);
        mViewPager.setLayoutParams(params);

        AdvertiseViewPager adapter = new AdvertiseViewPager(mImageResource);

        mViewPager.setAdapter(adapter);
        mListView.addHeaderView(mViewPager);
        adapter.notifyDataSetChanged();
    }

    @NonNull
    private AbsListView.LayoutParams getListViewLayoutParams(int width, int height) {
        return new AbsListView.LayoutParams(width, height);
    }

    private static class MyAdapter extends BaseAdapter {
        List<String> mContent;

        public MyAdapter(List<String> mContent) {
            this.mContent = mContent;
        }

        @Override
        public int getCount() {
            return mContent.size();
        }

        @Override
        public Object getItem(int position) {
            return mContent.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = new TextView(parent.getContext());
                ListView.LayoutParams params = new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                convertView.setLayoutParams(params);
            }

            TextView tv = (TextView) convertView;
            tv.setTextColor(Color.WHITE);
            tv.setText(mContent.get(position));

            return convertView;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }
    }

    private static class AdvertiseViewPager extends PagerAdapter {

        private HashSet<ImageView> mViews = new HashSet<ImageView>();
        private int[] mData;

        public AdvertiseViewPager(int[] mData) {
            this.mData = mData;
        }

        @Override
        public int getCount() {
            return mData.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageview = null;
            if (mViews.isEmpty()) {
                imageview = new ImageView(container.getContext());
                imageview.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } else {
                imageview = mViews.iterator().next();
                mViews.remove(imageview);
            }
            imageview.setImageResource(mData[position]);
            container.addView(imageview);
            return imageview;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            ImageView iv = (ImageView) object;
            container.removeView(iv);
            mViews.add(iv);
        }
    }

    public static class PositionInfo {
        int childPosition;
        float childOffSet;
    }

    private static class SingersAdapter extends BaseAdapter {

        List<List<String>> urls = null;
        private HashMap<Integer, PositionInfo> positions = new HashMap<Integer, PositionInfo>(); //行 列



        public SingersAdapter(List<List<String>> urls) {
            this.urls = urls;
        }

        @Override
        public int getCount() {
            return urls.size();
        }

        @Override
        public Object getItem(int position) {
            return urls.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            RecyclerView recycleView;
            if (convertView == null) {
                Log.e(TAG, "getView: create recycleView:" + createRecycleView++);
                recycleView = new RecyclerView(parent.getContext());
                ListView.LayoutParams params = new ListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 270);
                recycleView.setLayoutParams(params);
                convertView = recycleView;

                LinearLayoutManager manager = new LinearLayoutManager(convertView.getContext(), LinearLayoutManager.HORIZONTAL, false);
                recycleView.setLayoutManager(manager);
            }

            recycleView = (RecyclerView) convertView;


            List<String> content = urls.get(position);
            MyRecycleAdapter adapter = (MyRecycleAdapter) recycleView.getAdapter();

            if (positions.containsKey(position)) {
                PositionInfo positionInfo = positions.get(position);
                Log.e(TAG, "getView: restore to position:" + positionInfo + "@" + position);
                //recycleView.getLayoutManager().scrollToPosition(positionInfo.childPosition);
                ((LinearLayoutManager) recycleView.getLayoutManager()).scrollToPositionWithOffset(positionInfo.childPosition,(int)positionInfo.childOffSet);
                //recycleView.getLayoutManager().scrollToPosition(0);
            } else {
                recycleView.getLayoutManager().scrollToPosition(0);
            }


            recycleView.clearOnScrollListeners();
            recycleView.addOnScrollListener(new RecyclerView.OnScrollListener() {

                @Override
                public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                    super.onScrolled(recyclerView, dx, dy);
               //     Log.e(TAG, "onScrolled: --->" + dx + ":" + dy);
                }

                @Override
                public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                    super.onScrollStateChanged(recyclerView, newState);
                    switch (newState) {
                        case RecyclerView.SCROLL_STATE_IDLE:
                            View child = recyclerView.getLayoutManager().getChildAt(0);
                            if (child != null) {


                                PositionInfo positionInfo = new PositionInfo();
                                positionInfo.childPosition = recyclerView.getLayoutManager().getPosition(child);

                                positionInfo.childOffSet = child.getX();



                                Log.e(TAG, "getView: child position:" + positionInfo.childPosition+ "@" + position +" offset x:"+positionInfo.childOffSet);
                                // child.setTag(position+":"+pos);
                                positions.put(position, positionInfo);

                            }
                            break;
                    }
                }
            });


            if (adapter == null) {
                //  Log.e(TAG, "adapter == null create adapter" );
                adapter = new MyRecycleAdapter(content);
                recycleView.setAdapter(adapter);
            } else {
                //  Log.e(TAG, "adapter != null reuse adapter" );
                adapter.setData(content);
                adapter.notifyDataSetChanged();
            }

            //  recycleView.setAdapter(adapter);
            return recycleView;
        }

    }



    private static class MyRecycleAdapter extends RecyclerView.Adapter<ArrangeSingerActivity.MyRecycleAdapter.MyViewHolder> {

        private List<String> mData;

        public void setData(List<String> mData) {
            this.mData = mData;
        }

        public MyRecycleAdapter(List<String> mData) {
            this.mData = mData;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, "onCreateViewHolder: " + createViewHolder++);
            TextView tv = new TextView(parent.getContext());
            tv.setBackgroundColor(Color.YELLOW);
            tv.setTextColor(Color.BLACK);

            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(200, 200);
            params.leftMargin = 20;
            tv.setLayoutParams(params);
            return new MyViewHolder(tv);
        }

        /*
        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            Log.e(TAG, "onCreateViewHolder: " +createViewHolder++);
            TextView tv= new TextView(parent.getContext());
            tv.setBackgroundColor(Color.YELLOW);
            tv.setTextColor(Color.BLACK);

            RecyclerView.LayoutParams params = new RecyclerView.LayoutParams(200,200);
            params.leftMargin = 20;
            tv.setLayoutParams(params);
            return new MyViewHolder(tv);
        }
        */

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {
            holder.tv.setText(mData.get(position));
        }


        @Override
        public int getItemCount() {
            return mData.size();
        }

        public static class MyViewHolder extends RecyclerView.ViewHolder {

            public TextView tv;

            public MyViewHolder(View itemView) {
                super(itemView);
                Log.e(TAG, "MyViewHolder" + createTextView++);
                tv = (TextView) itemView;
            }
        }
    }
}
