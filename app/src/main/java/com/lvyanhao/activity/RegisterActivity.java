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
                Log.d("lvyanhao","====>���ע�ᰴť");
                myAsyncTask = new MyAsyncTask(RegisterActivity.this);
                myAsyncTask.execute();
                Log.d("lvyanhao","<====���ע��ɹ�");
            }
        });
    }

    class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog = null;
        private Context context;
        private Bundle bundle;
        //���ر���ͷ����
        private ResultDto resultDto = null;

        public MyAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = ProgressDialog.show(context, null, "����ע�ᣬ���Ժ�...", false);
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
            int flag = 99999;
            String suname = uname.getText().toString();
            String supwd = upwd.getText().toString();
            String snick = nick.getText().toString();
            String semail = email.getText().toString();
            String smobile = mobile.getText().toString();
            Log.d("lvyanhao", "@ ȡ���û��������ݣ�username="+suname+"��password="+supwd+",nick="
                +snick+",semail="+semail+",smobile="+smobile);
            UserSignupReqVo reqVo = new UserSignupReqVo();
            reqVo.setSuname(suname);
            reqVo.setSupwd(MD5Util.getMD5Code(supwd));
            reqVo.setSimei(new SystemUtil(mContext).getIMEI());
            reqVo.setSnick(snick);
            reqVo.setSemail(semail);
            reqVo.setSmobile(smobile);
            Log.d("lvyanhao", "@ ƴ����Ϣ UserSignupReqVo="+reqVo);
            //дjson
            Gson gson = new Gson();
            String rsp = NetUtil.post(mContext, "/user/signup.do", gson.toJson(reqVo), "1234567890");
            Log.d("lvyanhao", "@ ������������Ϣ��"+rsp);
            UserSignupRspVo rspVo = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<UserSignupRspVo>(){}.getType();
                //��������
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ ���ر����壺data="+data);
                rspVo = gson.fromJson(data, t);
            } catch (Exception e) {
                Toast.makeText(context, "������Ϣ��"+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ ��������ϸ��"+rspVo);
            if (resultDto == null) {
                resultDto = new ResultDto();
                resultDto.setStatus("-1");
                resultDto.setMsg("ϵͳδ֪����");
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
            dialog.dismiss();// �ر�ProgressDialog
            switch (values[0]) {
                case 0:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    Log.d("lvyanhao", "��ת�ɹ���");
                    Intent intent = new Intent();
                    intent.putExtras(bundle);
                    intent.setClass(RegisterActivity.this, VerifyRgcodeActivity.class);
                    startActivity(intent);

//                    finish();
                    break;
                case 99999:
                    Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
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
