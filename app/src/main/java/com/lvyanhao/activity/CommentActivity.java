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
        mRatingBar = (RatingBar)findViewById(R.id.ratingbar);//��
        mRatingBarLevel = (TextView)findViewById(R.id.mRatingBarLevel);//�Ǹ���
        comment_rate = (TextView)findViewById(R.id.comment_rate);//����
        grade = (TextView)findViewById(R.id.grade);
        commentBtn = (Button) findViewById(R.id.comment);
        commentBtn.setOnClickListener(new MyCommentListenr());
        /**
         * ʵʱ��ʾRatingBar��ֵ
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
             * UI�̲߳���
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
             * ���ݻ�ȡ
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
     * �����ж�
     * @param value
     * @return
     */
    private String clacGradeResult(float value){
        if (value <= 0) {
            return "�뻬�����Ǵ��Ŷ";
        } else if (value > 0 && value <= 1) {
            return "����Ŷ";
        } else if (value > 1 && value <= 2) {
            return "�Ƚϲ�Ŷ";
        } else if (value > 2 && value <= 3) {
            return "һ���Ŷ";
        } else if (value > 3 && value <= 4) {
            return "������Ŷ";
        } else if (value > 4 && value <= 4.3) {
            return "�ȽϺ�Ŷ";
        } else if (value > 4.3 && value <= 4.8) {
            return "�ܺ�Ŷ";
        } else if (value > 4.8 && value <= 4.9) {
            return "�ǳ���Ŷ";
        } else{
            return "��������Ŷ";
        }
    }

    /**
     * �ύ��ť
     */
    private class MyCommentListenr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            Toast.makeText(context, "�ύ�ɹ���", Toast.LENGTH_SHORT).show();
            myOnRatingBarChangingListener.close();
        }
    }
}
