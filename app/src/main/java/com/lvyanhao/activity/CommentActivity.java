package com.lvyanhao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.utils.MD5Util;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.CommentFilmReqVo;
import com.lvyanhao.vo.CommentFilmRspVo;
import com.lvyanhao.vo.UserLoginReqVo;
import com.lvyanhao.vo.UserLoginRspVo;

import java.lang.reflect.Type;

/**
 * Created by lyh on 2017/3/13.
 */

public class CommentActivity extends Activity {
    private TextView tv_filmname;
    private RatingBar mRatingBar = null;
    private TextView mRatingBarLevel = null;
    private TextView comment_rate = null;
    private TextView grade = null;
    private ImageView filmPostIm;
    private ImageView blurImageView;
    private Button commentBtn;
    private ImageView commentCancelBtn;

    private Context context;

    private MyAsyncTask myAsyncTask;

    private MyOnRatingBarChangingListener myOnRatingBarChangingListener;

    private float ratingFinalValue = 0;
    private String content = "";
    public String cflag;
    private String token;

    private Bundle bundle;
    private EditText ed_comment;

    private CommentFilmRspVo rspVo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);
        init();
        mRatingBar.setOnRatingBarChangeListener(new RatingBarListener());

    }

    private void init() {
        context = getApplicationContext();
        bundle = this.getIntent().getExtras();
        tv_filmname = (TextView) findViewById(R.id.comment_title);
        tv_filmname.setText(bundle.getString("fname"));
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);//��
        mRatingBarLevel = (TextView) findViewById(R.id.mRatingBarLevel);//�Ǹ���
        comment_rate = (TextView) findViewById(R.id.comment_rate);//����
        grade = (TextView) findViewById(R.id.grade);
        commentBtn = (Button) findViewById(R.id.comment);
        commentBtn.setOnClickListener(new MyCommentListenr());
        commentCancelBtn = (ImageView) findViewById(R.id.comment_cancel);
        commentCancelBtn.setOnClickListener(new CommentCancelListener());
        ed_comment = (EditText) findViewById(R.id.ed_comment);

        /**
         * ʵʱ��ʾRatingBar��ֵ
         */
        myOnRatingBarChangingListener = new MyOnRatingBarChangingListener(context);
        myOnRatingBarChangingListener.execute();
    }

    private class RatingBarListener implements RatingBar.OnRatingBarChangeListener {

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

        private void execute() {
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
                            mRatingBarLevel.setText("" + (ratingValue * 2));
                            comment_rate.setText("" + clacGradeResult(ratingValue));
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
                    while (true) {
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

        private void close() {
            if (!thread.isInterrupted()) {
                thread.interrupt();
            }
        }

    }

    /**
     * �����ж�
     *
     * @param value
     * @return
     */
    private String clacGradeResult(float value) {
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
        } else {
            return "��������Ŷ";
        }
    }

    /**
     * �ύ��ť
     */
    private class MyCommentListenr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            myAsyncTask = new MyAsyncTask(CommentActivity.this);
            myAsyncTask.execute();
            //Toast.makeText(context, "�ύ�ɹ���", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * ȡ����ť
     */
    private class CommentCancelListener implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            CommentActivity.this.finish();
            overridePendingTransition(R.anim.in_from_bottom, R.anim.out_to_top);
        }
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog = null;
        private Context context;

        //���ر���ͷ����
        private ResultDto resultDto = null;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, null, "�����ύ�����Ժ�...", false);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    try {
                        cancel(true);//ȡ�����еĲ���
                        //�����׳��쳣
                        throw new RuntimeException("�û�����ȡ������");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            token = SystemUtil.getTokenValueFromSP(context);
            Log.d("lvyanhao-token", "ȡ��SharedPreferences�е�TOKENֵ="+token);
            int flag = 99999;
            String fid = bundle.getString("fid");
            String ccontent = ed_comment.getText().toString();
            String ratingFinalValue = mRatingBarLevel.getText().toString();
            Log.d("lvyanhao", "@ ȡ���û��������ݣ�comment=" + ccontent + "��ratingFinalValue=" + ratingFinalValue+"cfid="+fid);
            CommentFilmReqVo reqVo = new CommentFilmReqVo();
            reqVo.setCfid(fid);
            reqVo.setCcontent(ccontent);
            reqVo.setCgrade(ratingFinalValue);
            Log.d("lvyanhao", "@ ƴ����Ϣ CommentFilmReqvo=" + reqVo);
            //дjson
            Gson gson = new Gson();
            String rsp = NetUtil.post(context, "/eval/comment/cmt.do", gson.toJson(reqVo), token);
            Log.d("lvyanhao", "@ ������������Ϣ��" + rsp);
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��" + resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<CommentFilmRspVo>() {
                }.getType();
                //��������
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ ���ر����壺data=" + data);
                rspVo = gson.fromJson(data, t);
            } catch (Exception e) {
                Toast.makeText(context, "������Ϣ��" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ ��������ϸ��" + rspVo);
            if (resultDto == null) {
                resultDto = new ResultDto();
                resultDto.setStatus("-1");
                resultDto.setMsg("ϵͳδ֪����");
                resultDto.setData(null);
            }

            flag = Integer.parseInt(resultDto.getStatus());
            publishProgress(flag);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.dismiss();// �ر�ProgressDialog
            switch (values[0]) {
                case 0:
                    if ("1".equals(rspVo.getCflag())){
                        Toast.makeText(context, "���۳ɹ���", Toast.LENGTH_SHORT).show();
                        myOnRatingBarChangingListener.close();
                        finish();
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    } else {
                        Toast.makeText(context, "������ʧ�ܣ��������ύ��", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 99999:
                    Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }


    }
}