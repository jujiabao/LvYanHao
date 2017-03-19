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
        mRatingBar = (RatingBar) findViewById(R.id.ratingbar);//星
        mRatingBarLevel = (TextView) findViewById(R.id.mRatingBarLevel);//星个数
        comment_rate = (TextView) findViewById(R.id.comment_rate);//评级
        grade = (TextView) findViewById(R.id.grade);
        commentBtn = (Button) findViewById(R.id.comment);
        commentBtn.setOnClickListener(new MyCommentListenr());
        commentCancelBtn = (ImageView) findViewById(R.id.comment_cancel);
        commentCancelBtn.setOnClickListener(new CommentCancelListener());
        ed_comment = (EditText) findViewById(R.id.ed_comment);

        /**
         * 实时显示RatingBar数值
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
             * UI线程操作
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
             * 数据获取
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
     * 评分判定
     *
     * @param value
     * @return
     */
    private String clacGradeResult(float value) {
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
        } else {
            return "超级完美哦";
        }
    }

    /**
     * 提交按钮
     */
    private class MyCommentListenr implements View.OnClickListener {
        @Override
        public void onClick(View view) {
            myAsyncTask = new MyAsyncTask(CommentActivity.this);
            myAsyncTask.execute();
            //Toast.makeText(context, "提交成功！", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * 取消按钮
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

        //返回报文头部分
        private ResultDto resultDto = null;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, null, "正在提交，请稍后...", false);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface dialog) {
                    try {
                        cancel(true);//取消所有的操作
                        //主动抛出异常
                        throw new RuntimeException("用户主动取消请求");
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
            Log.d("lvyanhao-token", "取得SharedPreferences中的TOKEN值="+token);
            int flag = 99999;
            String fid = bundle.getString("fid");
            String ccontent = ed_comment.getText().toString();
            String ratingFinalValue = mRatingBarLevel.getText().toString();
            Log.d("lvyanhao", "@ 取得用户输入数据：comment=" + ccontent + "，ratingFinalValue=" + ratingFinalValue+"cfid="+fid);
            CommentFilmReqVo reqVo = new CommentFilmReqVo();
            reqVo.setCfid(fid);
            reqVo.setCcontent(ccontent);
            reqVo.setCgrade(ratingFinalValue);
            Log.d("lvyanhao", "@ 拼包信息 CommentFilmReqvo=" + reqVo);
            //写json
            Gson gson = new Gson();
            String rsp = NetUtil.post(context, "/eval/comment/cmt.do", gson.toJson(reqVo), token);
            Log.d("lvyanhao", "@ 服务器返回信息：" + rsp);
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ 拆包明细：" + resultDto.getStatus() + "，" + resultDto.getMsg());
                //拆包返回的
                Type t = new TypeToken<CommentFilmRspVo>() {
                }.getType();
                //发送请求
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ 返回报文体：data=" + data);
                rspVo = gson.fromJson(data, t);
            } catch (Exception e) {
                Toast.makeText(context, "错误信息：" + e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ 报文体明细：" + rspVo);
            if (resultDto == null) {
                resultDto = new ResultDto();
                resultDto.setStatus("-1");
                resultDto.setMsg("系统未知错误！");
                resultDto.setData(null);
            }

            flag = Integer.parseInt(resultDto.getStatus());
            publishProgress(flag);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.dismiss();// 关闭ProgressDialog
            switch (values[0]) {
                case 0:
                    if ("1".equals(rspVo.getCflag())){
                        Toast.makeText(context, "评论成功！", Toast.LENGTH_SHORT).show();
                        myOnRatingBarChangingListener.close();
                        finish();
                        overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
                    } else {
                        Toast.makeText(context, "服务器失败，请重新提交！", Toast.LENGTH_SHORT).show();
                    }
                    break;
                case 99999:
                    Toast.makeText(context, "连接网络错误！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }


    }
}