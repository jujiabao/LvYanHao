package com.lvyanhao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.text.SpannableString;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import static android.R.attr.key;
import static android.R.attr.logo;

public class LoginActivity extends Activity {

    private Button btnLogin;

    private EditText editId;

    private EditText editPwd;

    private ImageView logoImageView;

    private MyAsyncTask myAsyncTask;

    private Context mContext;

    private boolean flag = true;

    private TextView registTV;

    private TextView findpwdTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        registListener();
        forgotListener();
        myListener();
    }

    private void init(){
        mContext = getApplicationContext();
        btnLogin = (Button) findViewById(R.id.login_btnLogin);
        editId = (EditText) findViewById(R.id.login_edtId);
        editPwd = (EditText) findViewById(R.id.login_edtPwd);
        logoImageView = (ImageView) findViewById(R.id.set_net_btn);
        registTV = (TextView)findViewById(R.id.login_txtRegister);
        findpwdTV = (TextView)findViewById(R.id.login_txtForgotPwd);
    }

    private void registListener(){
        registTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao","====>点击用户注册按钮");
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                Log.d("lvyanhao","<====点击用户注册按钮成功");
            }
        });
    }

    private void forgotListener(){
        findpwdTV.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao","====>点击忘记密码按钮");
                Intent intent = new Intent(LoginActivity.this,ForgotPwdActivity.class);
                startActivity(intent);
                Log.d("lvyanhao","<====点击忘记密码成功");
            }
        });
    }

    private void myListener(){
        btnLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao", "====>点击登陆按钮");
                if (!"".equals(editId.getText().toString()) && !"".equals(editPwd.getText().toString())) {
                    myAsyncTask = new MyAsyncTask(LoginActivity.this);
                    myAsyncTask.execute();
                } else {
                    Toast.makeText(LoginActivity.this, "请输入用户或密码！", Toast.LENGTH_SHORT).show();
                }
                Log.d("lvyanhao", "<====点击登陆按钮成功");
            }
        });

        logoImageView.setOnClickListener(new OnClickListener() {
            long[] mHints = new long[3];//初始全部为0
            private LinearLayout layout;
            private Button confirm;
            private Button cancel;
            private EditText ipEdt;
            private EditText portEdt;
            private SharedPreferences userSettings;
            private SharedPreferences.Editor editor;

            @Override
            public void onClick(View view) {
                layout = (LinearLayout) findViewById(R.id.set_net);
                confirm = (Button) findViewById(R.id.set_net_confirm);
                cancel = (Button) findViewById(R.id.set_net_cancel);
                ipEdt = (EditText) findViewById(R.id.ip);
                portEdt = (EditText) findViewById(R.id.port);

                //存储设置
                userSettings = getSharedPreferences("setting_net", MODE_PRIVATE);
                editor = userSettings.edit();

                //将mHints数组内的所有元素左移一个位置
                System.arraycopy(mHints, 1, mHints, 0, mHints.length - 1);
                //获得当前系统已经启动的时间
                mHints[mHints.length - 1] = SystemClock.uptimeMillis();
                if(SystemClock.uptimeMillis()-mHints[0]<=500) {
                    layout.setVisibility(View.VISIBLE);
                    SharedPreferences settings= getSharedPreferences("setting_net", 0);
                    String ip = settings.getString("ip","192.168.1.106");
                    int port = settings.getInt("port", 8888);
                    ipEdt.setText(ip);
                    portEdt.setText(port+"");
                }

                cancel.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        layout.setVisibility(View.GONE);
                    }
                });

                confirm.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            String ip = ipEdt.getText().toString();
                            String port = portEdt.getText().toString();
                            editor.putString("ip", ip);
                            editor.putInt("port", Integer.parseInt(port));
                            editor.commit();
                            Log.d("lvyanhao", "@ ip="+ip+",port="+port);
                            Toast.makeText(getApplicationContext(), "保存网络配置成功！", Toast.LENGTH_SHORT).show();
                            layout.setVisibility(View.GONE);
                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "保存网络配置失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
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
            //写json
            Gson gson = new Gson();
            String rsp = NetUtil.post(mContext, "/user/login.do", gson.toJson(reqVo), "1234567890");
            Log.d("lvyanhao", "@ 服务器返回信息："+rsp);
            UserLoginRspVo rspVo = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ 拆包明细："+ resultDto.getStatus() + "，" + resultDto.getMsg());
                //拆包返回的
                Type t = new TypeToken<UserLoginRspVo>(){}.getType();
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
                Intent intent = new Intent(LoginActivity.this,FilmInfoListViewActivity.class);
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
