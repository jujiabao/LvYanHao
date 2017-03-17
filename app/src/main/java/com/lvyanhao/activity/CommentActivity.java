package com.lvyanhao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lvyanhao.R;

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
    private Button commentBtn;

    private Context context;

    private MyOnRatingBarChangingListener myOnRatingBarChangingListener;

    private float ratingFinalValue = 0;
    private String content = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        mRatingBar.setOnRatingBarChangeListener(new RatingBarListener());

    }

    private void init(){
        context = getApplicationContext();
        mRatingBar = (RatingBar)findViewById(R.id.ratingbar);//星
        mRatingBarLevel = (TextView)findViewById(R.id.mRatingBarLevel);//星个数
        comment_rate = (TextView)findViewById(R.id.comment_rate);//评级
        grade = (TextView)findViewById(R.id.grade);
        commentBtn = (Button) findViewById(R.id.comment);
        commentBtn.setOnClickListener(new MyCommentListenr());
        /**
         * 实时显示RatingBar数值
         */
        myOnRatingBarChangingListener = new MyOnRatingBarChangingListener(context);
        myOnRatingBarChangingListener.execute();
    }

    private class RatingBarListener implements RatingBar.OnRatingBarChangeListener{

        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating,
                                    boolean fromUser) {
            ratingFinalValue = rating * 2;
        }
    }

    private class MyOnRatingBarChangingListener {
        private Context context;
        private Handler handler;
        private Thread thread;

        public MyOnRatingBarChangingListener(Context context) {
            this.context = context;
        }

        private void execute(){
            /**
             * UI线程操作
             */
            handler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    switch (msg.what) {
                        case 1:
                            Bundle bundle = msg.getData();
                            float ratingValue = bundle.getFloat("ratingValue");
                            mRatingBarLevel.setText(""+(ratingValue*2));
                            comment_rate.setText(""+clacGradeResult(ratingValue));
                    }
                    super.handleMessage(msg);
                }
            };
            /**
             * 数据获取
             */
            thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while(true) {
                        float ratingValue = mRatingBar.getRating();
                        Message message = new Message();
                        message.what = 1;
                        Bundle bundle = new Bundle();
                        bundle.putFloat("ratingValue", ratingValue);
                        message.setData(bundle);
                        handler.sendMessage(message);
                        try {
                            Thread.sleep(50);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            thread.start();
        }

        private void close(){
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }

    }

    /**
     * 评分判定
     * @param value
     * @return
     */
    private String clacGradeResult(float value){
        if (value <= 0) {
            return "请滑动星星打分哦";
        } else if (value > 0 && value <= 1) {
            return "超烂哦";
        } else if (value > 1 && value <= 2) {
            return "比较差哦";
        } else if (value > 2 && value <= 3) {
            return "一般般哦";
        } else if (value > 3 && value <= 4) {
            return "还不错哦";
        } else if (value > 4 && value <= 4.3) {
            return "比较好哦";
        } else if (value > 4.3 && value <= 4.8) {
            return "很好哦";
        } else if (value > 4.8 && value <= 4.9) {
            return "非常棒哦";
        } else{
            return "超级完美哦";
        }
    }

    /**
     * 提交按钮
     */
    private class MyCommentListenr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "提交成功！", Toast.LENGTH_SHORT).show();
            myOnRatingBarChangingListener.close();
        }
    }
}
