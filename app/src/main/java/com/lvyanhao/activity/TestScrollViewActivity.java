package com.lvyanhao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.lvyanhao.R;
import com.lvyanhao.component.MyFilmCommentViewAdapter;
import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.CommentListLoadMoreRspVo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by lyh on 2017/3/12.
 */

public class TestScrollViewActivity extends Activity implements View.OnClickListener {

    private ImageView blurImageView;
    private ImageView filmPostIm;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态

    private TextView mContentText;// 展示文本内容
    private RelativeLayout mShowMore;// 展示更多
    private ImageView mImageSpread;// 展开
    private ImageView mImageShrinkUp;// 收起

    private ListView listView;
    List<CommentListLoadMoreRspVo> items;
    MyFilmCommentViewAdapter adapter;
    private Context mContext;

    private Button comment_agree_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrollview);
        mContext = getApplicationContext();
        ((PullToRefreshLayout) findViewById(R.id.film_refresh_view))
                .setOnRefreshListener(new MyListener());
        listView = (ListView)findViewById(R.id.content_view);
        findViews();
        initData();
        initView();
        initListView();
    }

    private void initListView()
    {
        items = new ArrayList<CommentListLoadMoreRspVo>();
        for (int i = 0; i < 4; i++) {
//			items.add("这里是item " + i);
            CommentListLoadMoreRspVo commentListRspVo = new CommentListLoadMoreRspVo();
            commentListRspVo.setCcid(UUID.randomUUID().toString());
            commentListRspVo.setCfid(UUID.randomUUID().toString());
            commentListRspVo.setCunick("大傻逼"+i);
            commentListRspVo.setCcreatime("2017年3月12日");
            commentListRspVo.setCagreetime("100");
            commentListRspVo.setCgrade("9.8");
            commentListRspVo.setCcontent("3456756754633gsfdgd3456756754633gsfdgdf3456756754633gsfdgdff");
            items.add(commentListRspVo);
        }
        adapter = new MyFilmCommentViewAdapter(mContext, items);
        listView.setAdapter(adapter);
        listView.setFocusable(false);
        SystemUtil.setListViewHeightBasedOnChildren(listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                Toast.makeText(
                        mContext,
                        "LongClick on "
                                + position,
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Log.d("jujiabao", "当前itemId="+parent.getAdapter().getItemId(position));
                Toast.makeText(mContext,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        mContentText = (TextView) findViewById(R.id.film_content);
        mShowMore = (RelativeLayout) findViewById(R.id.show_more);
        mImageSpread = (ImageView) findViewById(R.id.more);
        mImageShrinkUp = (ImageView) findViewById(R.id.more_up);
        comment_agree_btn = (Button) findViewById(R.id.comment_agree_btn);
        mShowMore.setOnClickListener(this);

    }



    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.show_more: {
                if (mState == SPREAD_STATE) {
                    mContentText.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.GONE);
                    mImageSpread.setVisibility(View.VISIBLE);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    mContentText.setMaxLines(Integer.MAX_VALUE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.VISIBLE);
                    mImageSpread.setVisibility(View.GONE);
                    mState = SPREAD_STATE;
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void findViews(){
        blurImageView = (ImageView) findViewById(R.id.iv_blur);
        filmPostIm = (ImageView) findViewById(R.id.iv_filmpost);
    }

    private void initData(){
        Glide.with(this).load(R.drawable.test)
                .bitmapTransform(new BlurTransformation(this, 75), new CenterCrop(this))
                .into(blurImageView);
//        filmPostIm = (ImageView) findViewById(R.id.comment_avator);
//        Glide.with(this).load("http://game.gtimg.cn/images/lol/act/a20170103spring/unit.jpg").bitmapTransform(new CropCircleTransformation(this))
//                .into(filmPostIm);

       /* Glide.with(this).load(R.drawable.test_image)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(filmPostIm); */
//        filmPostIm.setImageDrawable(getResources().getDrawable(R.drawable.test_image));
        filmPostIm.setBackgroundDrawable(getResources().getDrawable(R.drawable.test));
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);

        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    for (int i = 0; i < 4; i++) {
                        CommentListLoadMoreRspVo commentListRspVo = new CommentListLoadMoreRspVo();
                        commentListRspVo.setCcid(UUID.randomUUID().toString());
                        commentListRspVo.setCfid(UUID.randomUUID().toString());
                        commentListRspVo.setCunick("大傻逼"+i);
                        commentListRspVo.setCcreatime("2017年3月12日");
                        commentListRspVo.setCagreetime("100");
                        commentListRspVo.setCgrade("9.8");
                        commentListRspVo.setCcontent("3456756754633gsfdgd3456756754633gsfdgdf3456756754633gsfdgdff");
                        items.add(commentListRspVo);
                    }
                    adapter = new MyFilmCommentViewAdapter(mContext, items);
                    listView.setAdapter(adapter);
                    SystemUtil.setListViewHeightBasedOnChildren(listView);
                    adapter.notifyDataSetChanged();
                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 2000);
        }

    }
}
