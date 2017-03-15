package com.lvyanhao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.utils.MD5Util;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.vo.UserSignupRspVo;
import com.lvyanhao.vo.UserVerifyFindPwdCodeReqVo;
import com.lvyanhao.vo.UserVerifyFindPwdCodeRspVo;
import com.lvyanhao.vo.UserVerifyMailReqVo;

import java.lang.reflect.Type;

/**
 * Created by lyh on 2017/3/4.
 */

public class VerifyFindPwdActivity extends Activity{

    private Context mContext;

    private EditText vname;

    private EditText vemail;

    private EditText vpasswd;

    private EditText vcode;

    private Button btnsure;

    private MyAsyncTask myAsyncTask;

    private Bundle bundle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifypwdcode);
        init();
        sureListener();
    }

    private void init(){
        mContext = getApplicationContext();
        btnsure = (Button)findViewById(R.id.findpwd_sure);
        vname = (EditText) findViewById(R.id.findpwd_edtUname);
        vemail = (EditText) findViewById(R.id.findpwd_edtEmil);
        vpasswd = (EditText)findViewById(R.id.findpwd_edtPwd);
        vcode = (EditText)findViewById(R.id.findpwd_edtCode);

        bundle = this.getIntent().getExtras();
        String name = bundle.getString("funame");
        vname.setText("用户名："+name);
        String email = bundle.getString("femail");
        vemail.setText("邮箱："+email);
    }



    private void sureListener(){
        btnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao","====>点击确认");
                myAsyncTask = new MyAsyncTask(VerifyFindPwdActivity.this);
                myAsyncTask.execute();
                Log.d("lvyanhao","<====点击确认成功");
            }
        });
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
            dialog = ProgressDialog.show(context, null, "正在验证，请稍后...", false);
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
            int flag = 99999;
            String fname = bundle.getString("funame");
            String fmail = bundle.getString("femail");
            String fnewpwd = vpasswd.getText().toString();
            String fcode = vcode.getText().toString();

            Log.d("lvyanhao", "@ 取得用户输入数据：code="+fcode+"name="+fname+"maile+"+fmail+"fnewpwd"+vpasswd);

            UserVerifyFindPwdCodeReqVo reqVo = new UserVerifyFindPwdCodeReqVo();
            reqVo.setFname(fname);
            reqVo.setFmail(fmail);
            reqVo.setFnewpwd(MD5Util.getMD5Code(fnewpwd));
            reqVo.setFcode(fcode);
            Log.d("lvyanhao", "@ 拼包信息 UserVerifyFindPwdCodeReqVo="+reqVo);
            //写json
            Gson gson = new Gson();
            String rsp = NetUtil.post(mContext, "/user/fdpd.do", gson.toJson(reqVo), "1234567890");
            Log.d("lvyanhao", "@ 服务器返回信息："+rsp);
            UserVerifyFindPwdCodeRspVo rspVo = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ 拆包明细："+ resultDto.getStatus() + "，" + resultDto.getMsg());
                //拆包返回的
                Type t = new TypeToken<UserVerifyFindPwdCodeRspVo>(){}.getType();
                //发送请求
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ 返回报文体：data="+data);
                rspVo = gson.fromJson(data, t);
            } catch (Exception e) {
                Toast.makeText(context, "错误信息："+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ 报文体明细："+rspVo);
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
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    Log.d("lvyanhao", "跳转成功！");
                    Intent intent = new Intent();
                    intent.setClass(VerifyFindPwdActivity.this, LoginActivity.class);
                    startActivity(intent);

                    finish();
                    break;
                case 99999:
                    Toast.makeText(context, "连接网络错误！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

    };

}
