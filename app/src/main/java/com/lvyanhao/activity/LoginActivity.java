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
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.utils.JSONUtil;
import com.lvyanhao.utils.MD5Util;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.RegExUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.UserLoginReqVo;
import com.lvyanhao.vo.UserLoginRspVo;

import org.json.JSONException;

import java.lang.reflect.Type;

import static android.R.attr.data;

public class LoginActivity extends Activity {

    private Button btnLogin;

    private EditText editId;

    private EditText editPwd;

    private MyAsyncTask myAsyncTask;

    private Context mContext;

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        myListener();
    }

    private void init(){
        mContext = getApplicationContext();
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        editId = (EditText) findViewById(R.id.login_edtId);
        editPwd = (EditText) findViewById(R.id.login_edtPwd);
    }

    private void myListener(){
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao", "====>点击登陆按钮");
                myAsyncTask = new MyAsyncTask(LoginActivity.this);
                myAsyncTask.execute();
                Log.d("lvyanhao", "<====点击登陆按钮成功");
            }
        });
    }
    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog = null;
        private Context context;

        private String msg = null;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, null, "正在登录，请稍后...", false);
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
            String username = editId.getText().toString();
            String password = editPwd.getText().toString();
            Log.d("lvyanhao", "@ 取得用户输入数据：username="+username+"，password="+password);
            UserLoginReqVo reqVo = new UserLoginReqVo();
            reqVo.setUname(username);
            reqVo.setUpwd(MD5Util.getMD5Code(password));
            reqVo.setImei(new SystemUtil(mContext).getIMEI());
            Log.d("lvyanhao", "@ 拼包信息 UserLoginReqVo="+reqVo);
            Gson gson = new Gson();
            String rsp = NetUtil.post("/user/login.do", gson.toJson(reqVo));
            Log.d("lvyanhao", "@ 服务器返回信息："+rsp);
            Gson rspGson = new Gson();
            ResultDto resultDto = rspGson.fromJson(rsp, ResultDto.class);
            String status = resultDto.getStatus();
            msg = resultDto.getMsg();
            Log.d("lvyanhao", "@ 拆包明细："+ status + "，" + msg);
            Type t = new TypeToken<UserLoginRspVo>(){}.getType();
            String data = rspGson.toJson(resultDto.getData());
            Log.d("lvyanhao", "@ data="+data);
            UserLoginRspVo rspVo = rspGson.fromJson(data, t);
            Log.d("lvyanhao", "@ 报文体明细："+rspVo);
            flag = Integer.parseInt(status);
            publishProgress(flag);
            return null;

        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            dialog.dismiss();// 关闭ProgressDialog
            switch (values[0]) {
                case 0:
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    Log.d("lvyanhao", "跳转成功！");
                    Intent intent = new Intent(LoginActivity.this,FilmInfoListViewActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case 99999:
                    Toast.makeText(context, "连接网络错误！", Toast.LENGTH_SHORT).show();
                    break;
                default:
                    Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
                    break;
            }
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

    };
}
