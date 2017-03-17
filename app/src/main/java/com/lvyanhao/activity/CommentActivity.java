package com.lvyanhao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.lvyanhao.R;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by lyh on 2017/3/13.
 */

public class CommentActivity extends Activity {

    private RatingBar mRatingBar = null;
    private TextView mRatingBarLevel = null;
    private TextView comment_rate = null;
    private TextView grade = null;
    private ImageView filmPostIm;
    private ImageView blurImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        init();
        mRatingBar.setOnRatingBarChangeListener(new RatingBarListener());
    }

    private void init(){
        mRatingBar = (RatingBar)findViewById(R.id.ratingbar);//星
        mRatingBarLevel = (TextView)findViewById(R.id.mRatingBarLevel);//星个数
        comment_rate = (TextView)findViewById(R.id.comment_rate);//评级
        grade = (TextView)findViewById(R.id.grade);
    }

    private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            System.out.println("rating--->" + rating);
            if (rating == 0){
                mRatingBarLevel.setText(null);
                comment_rate.setText(null);
                grade.setText("请滑动星星打分");
            }else if (rating == 0.5){
                mRatingBarLevel.setText("1");
                comment_rate.setText("超烂啊");
                grade.setText("分");
            }else if (rating == 1){
                mRatingBarLevel.setText("2");
                comment_rate.setText("超烂啊");
                grade.setText("分");
            }else if (rating == 1.5){
                mRatingBarLevel.setText("3");
                comment_rate.setText("比较差");
                grade.setText("分");
            }else if (rating == 2){
                mRatingBarLevel.setText("4");
                comment_rate.setText("比较差");
                grade.setText("分");
            }else if (rating == 2.5){
                mRatingBarLevel.setText("5");
                comment_rate.setText("比较差");
                grade.setText("分");
            }else if (rating == 3.0){
                mRatingBarLevel.setText("6");
                comment_rate.setText("一般般");
                grade.setText("分");
            }else if (rating == 3.5){
                mRatingBarLevel.setText("7");
                comment_rate.setText("比较好");
                grade.setText("分");
            }else if (rating == 4.0){
                mRatingBarLevel.setText("8");
                comment_rate.setText("比较好");
                grade.setText("分");
            }else if (rating == 4.5){
                mRatingBarLevel.setText("9");
                comment_rate.setText("棒极了");
                grade.setText("分");
            }else if (rating == 5.0){
                mRatingBarLevel.setText("10");
                comment_rate.setText("完美！");
                grade.setText("分");
            }
        }
    }



}
