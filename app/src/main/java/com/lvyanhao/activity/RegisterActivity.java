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
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.utils.MD5Util;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.UserSignupReqVo;
import com.lvyanhao.vo.UserSignupRspVo;

import java.lang.reflect.Type;
import java.security.PublicKey;

/**
 * Created by lyh on 2017/2/28.
 */

public class RegisterActivity extends Activity{

    private Context mContext;

    private Button btnRegister;

    private EditText uname;

    private EditText upwd;

    private EditText nick;

    private EditText email;

    private EditText mobile;

    private MyAsyncTask myAsyncTask;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        init();
        registListener();
    }
    private void init(){
        mContext = getApplicationContext();
        btnRegister = (Button)findViewById(R.id.register_register);
        uname = (EditText)findViewById(R.id.register_edtId);
        upwd = (EditText)findViewById(R.id.register_edtPwd);
        nick = (EditText)findViewById(R.id.register_edtNick);
        email = (EditText)findViewById(R.id.register_edtEmil);
        mobile = (EditText)findViewById(R.id.register_edtTel);
    }
    private void registListener(){
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao","====>点击注册按钮");
                myAsyncTask = new MyAsyncTask(RegisterActivity.this);
                myAsyncTask.execute();
                Log.d("lvyanhao","<====点击注册成功");
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog = null;
        private Context context;
        private Bundle bundle;
        //返回报文头部分
        private ResultDto resultDto = null;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, null, "正在注册，请稍后...", false);
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
            String suname = uname.getText().toString();
            String supwd = upwd.getText().toString();
            String snick = nick.getText().toString();
            String semail = email.getText().toString();
            String smobile = mobile.getText().toString();
            Log.d("lvyanhao", "@ 取得用户输入数据：username="+suname+"，password="+supwd+",nick="
                +snick+",semail="+semail+",smobile="+smobile);
            UserSignupReqVo reqVo = new UserSignupReqVo();
            reqVo.setSuname(suname);
            reqVo.setSupwd(MD5Util.getMD5Code(supwd));
            reqVo.setSimei(new SystemUtil(mContext).getIMEI());
            reqVo.setSnick(snick);
            reqVo.setSemail(semail);
            reqVo.setSmobile(smobile);
            Log.d("lvyanhao", "@ 拼包信息 UserSignupReqVo="+reqVo);
            //写json
            Gson gson = new Gson();
            String rsp = NetUtil.post(mContext, "/user/signup.do", gson.toJson(reqVo), "1234567890");
            Log.d("lvyanhao", "@ 服务器返回信息："+rsp);
            UserSignupRspVo rspVo = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ 拆包明细："+ resultDto.getStatus() + "，" + resultDto.getMsg());
                //拆包返回的
                Type t = new TypeToken<UserSignupRspVo>(){}.getType();
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
            if (rspVo != null) {
                bundle = new Bundle();
                bundle.putString("suname", rspVo.getSuname());
                bundle.putString("snick", rspVo.getSnick());
                bundle.putString("semail", rspVo.getSemail());
                bundle.putString("smobile", rspVo.getSmobile());
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
                    intent.putExtras(bundle);
                    intent.setClass(RegisterActivity.this, VerifyRgcodeActivity.class);
                    startActivity(intent);

//                    finish();
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
