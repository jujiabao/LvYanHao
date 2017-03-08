package com.lvyanhao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.lvyanhao.R;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.CropCircleTransformation;

/**
 * Created by lyh on 2017/3/7.
 */

public class TestActivity extends Activity implements View.OnClickListener {
    private ImageView blurImageView;
    private ImageView avatarImageView;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// 默认展示最大行数3行
    private static final int SHOW_CONTENT_NONE_STATE = 0;// 扩充
    private static final int SHRINK_UP_STATE = 1;// 收起状态
    private static final int SPREAD_STATE = 2;// 展开状态
    private static int mState = SHRINK_UP_STATE;//默认收起状态

    private TextView mContentText;// 展示文本内容
    private RelativeLayout mShowMore;// 展示更多
    private ImageView mImageSpread;// 展开
    private ImageView mImageShrinkUp;// 收起

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_item_layout);
        findViews();
        initData();
        initView();
    }

    private void initView() {
        mContentText = (TextView) findViewById(R.id.content);
        mShowMore = (RelativeLayout) findViewById(R.id.show_more);
        mImageSpread = (ImageView) findViewById(R.id.more);
        mImageShrinkUp = (ImageView) findViewById(R.id.more_up);
        mShowMore.setOnClickListener(this);

    }

    public void onClick(View v) {
// TODO Auto-generated method stub
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
        avatarImageView = (ImageView) findViewById(R.id.iv_filmpost);
    }

    private void initData(){
        Glide.with(this).load(R.drawable.test)
                .bitmapTransform(new BlurTransformation(this, 75), new CenterCrop(this))
                .into(blurImageView);

       /* Glide.with(this).load(R.drawable.test_image)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView); */
//        avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.test_image));
        avatarImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.test));
    }
}
