package com.lvyanhao.listactivity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.lvyanhao.fragment.ItemDetailFragment;
import com.lvyanhao.R;
import com.lvyanhao.dummy.DummyContent;
import com.lvyanhao.fragment.ItemsDivider;

import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * An activity representing a list of Items. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ItemDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class ItemListActivity extends AppCompatActivity {

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        View recyclerView = findViewById(R.id.item_list);
        assert recyclerView != null;
        setupRecyclerView((RecyclerView) recyclerView);

        if (findViewById(R.id.item_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        //设置分隔符
        recyclerView.addItemDecoration(new ItemsDivider(this, ItemsDivider.VERTICAL_LIST));
        SimpleItemRecyclerViewAdapter mRecyclerView = new SimpleItemRecyclerViewAdapter(DummyContent.ITEMS);
        //设置文本
        mRecyclerView.setFootViewText("上拉刷新");
        //设置适配器
        recyclerView.setAdapter(mRecyclerView);
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private final List<DummyContent.DummyItem> mValues;

        //最大显示数，要比每页显示条数多1
        private int max_count = 2;

        //是否添加了FootView
        private Boolean isFootView = false;

        //FootView的内容
        private String footViewText = "";

        //两个final int类型表示ViewType的两种类型
        private final int NORMAL_TYPE = 0;
        private final int FOOT_TYPE = 1111;

        public SimpleItemRecyclerViewAdapter(List<DummyContent.DummyItem> items) {
            mValues = items;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.item_list_content, parent, false);

            View foot_view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.foot_view, parent, false);

            Log.d(">>>viewType<<<","viewType="+viewType+",isFootView="+isFootView);

            if (viewType == FOOT_TYPE){
                return new ViewHolder(foot_view, FOOT_TYPE);
            }
            return new ViewHolder(view, NORMAL_TYPE);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, final int position) {
            //如果footview存在，并且当前位置ViewType是FOOT_TYPE
            if (isFootView && (getItemViewType(position) == FOOT_TYPE)) {
                holder.tvFootView.setText(footViewText);

                //刷新太快 所以使用Hanlder延迟两秒
                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        max_count += 5;
                        for (int i=0; i<5; i++) {
                            String id = position+"";
                            String filmId = UUID.randomUUID().toString();
                            String content = "null";
                            String filmNa = "大闹天竺" + i;
                            String filmLv = "4.6";
                            String filmInfo = "宝强取真经，争当搞笑King";
                            String filmPlayTm = "2017-01-28 本周六上映";
                            DummyContent.DummyItem dummyItem = new DummyContent.DummyItem(id, content, filmId, filmNa, filmLv, filmInfo, filmPlayTm, "测试");
                            mValues.add(dummyItem);
                        }
//                        Toast.makeText(ItemListActivity.this, mValues.get(position).details, Toast.LENGTH_SHORT).show();
                        Log.d(">>>mValues<<<", "mValues="+mValues.get(position).details);
                        notifyDataSetChanged();
                    }
                }, 2000);

            } else {
                holder.mItem = mValues.get(position);
//            holder.mIdView.setText(mValues.get(position).id);
//            holder.mContentView.setText(mValues.get(position).content);
                holder.mFilmNa.setText(mValues.get(position).filmNa);
                holder.mFilmLv.setText(mValues.get(position).filmLv);
                holder.mFilmInfo.setText(mValues.get(position).filmInfo);
                holder.mFilmPlayTm.setText(mValues.get(position).filmPlayTm);

                Resources resources = getResources();
                holder.mImage.setImageDrawable(resources.getDrawable(R.drawable.test_image));
            }

            holder.mView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(">>>>position<<<<","这是第"+position+"个，mTwoPane="+mTwoPane+",detail="+mValues.get(position).details);
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);
                        ItemDetailFragment fragment = new ItemDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.item_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, ItemDetailActivity.class);
                        intent.putExtra(ItemDetailFragment.ARG_ITEM_ID, holder.mItem.id);

                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            if (mValues.size() < max_count) {
                return mValues.size();
            }
            return max_count;
        }

        @Override
        public int getItemViewType(int position) {
            Log.d(">>>getItemViewType<<<", "getItemViewType="+position);
            if (position == max_count - 1) {
                return FOOT_TYPE;
            }
            return NORMAL_TYPE;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public View mView;
//            public final TextView mIdView;
//            public final TextView mContentView;
            public ImageView mImage;
            public TextView mFilmNa;
            public TextView mFilmLv;
            public TextView mFilmInfo;
            public TextView mFilmPlayTm;
            public DummyContent.DummyItem mItem;
            //footView的TextView属于独自的一个layout
            public TextView tvFootView;

            //初始化viewHolder，此处绑定后在onBindViewHolder中可以直接使用
            public ViewHolder(View view, int viewType) {
                super(view);
                mView = view;
                if (viewType == NORMAL_TYPE) {
                    this.mFilmNa = (TextView) view.findViewById(R.id.filmna);
                    this.mFilmLv = (TextView) view.findViewById(R.id.filmlv);
                    this.mFilmInfo = (TextView) view.findViewById(R.id.filminfo);
                    this.mFilmPlayTm = (TextView) view.findViewById(R.id.filmplaytime);
//                mIdView = (TextView) view.findViewById(R.id.id);
//                mContentView = (TextView) view.findViewById(R.id.content);
                    mImage = (ImageView) view.findViewById(R.id.mimage);
                } else if (viewType == FOOT_TYPE){
                    tvFootView = (TextView) view;
                }
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mFilmNa.getText() + "'";
            }
        }

        //创建一个方法来设置footView中的文字
        public void setFootViewText(String footViewText) {
            isFootView = true;
            this.footViewText = footViewText;
        }
    }
}
