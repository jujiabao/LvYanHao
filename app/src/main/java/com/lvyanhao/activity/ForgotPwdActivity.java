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
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.UserSignupReqVo;
import com.lvyanhao.vo.UserSignupRspVo;
import com.lvyanhao.vo.UserVerifyFindPwdCodeReqVo;
import com.lvyanhao.vo.UserVerifyFindPwdCodeRspVo;

import java.lang.reflect.Type;

/**
 * Created by lyh on 2017/2/28.
 */

public class ForgotPwdActivity extends Activity{

    private Context mContext;

    private Button btnfindpwd;

    private EditText uname;

    private EditText email;

    private ForgotPwdActivity.MyAsyncTask myAsyncTask;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpwd);
        init();
        findListener();
    }

    private void init(){
        mContext = getApplicationContext();
        btnfindpwd = (Button)findViewById(R.id.forget_findpwd);
        uname = (EditText) findViewById(R.id.forget_edtId);
        email = (EditText) findViewById(R.id.forget_edtEmail);
    }


    private void findListener(){
        btnfindpwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao","====>����һ����밴ť");
                myAsyncTask = new ForgotPwdActivity.MyAsyncTask(ForgotPwdActivity.this);
                myAsyncTask.execute();
                Log.d("lvyanhao","<====����һ�����ɹ�");
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
            dialog = ProgressDialog.show(context, null, "�����һأ����Ժ�...", false);
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
            String funame = uname.getText().toString();
            String femail = email.getText().toString();
            Log.d("lvyanhao", "@ ȡ���û��������ݣ�username="+funame+"email"+femail);
            UserVerifyFindPwdCodeReqVo reqVo = new UserVerifyFindPwdCodeReqVo();
            reqVo.setFname(funame);
            reqVo.setFmail(femail);
            Log.d("lvyanhao", "@ ƴ����Ϣ UserVerifyFindPwdCodeReqVo="+reqVo);
            //дjson
            Gson gson = new Gson();
            String rsp = NetUtil.post(mContext, "/user/sdfdpd.do", gson.toJson(reqVo), "1234567890");
            Log.d("lvyanhao", "@ ������������Ϣ��"+rsp);
            UserVerifyFindPwdCodeRspVo rspVo = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<UserVerifyFindPwdCodeRspVo>(){}.getType();
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
                bundle.putString("funame", rspVo.getFname());
                bundle.putString("femail", rspVo.getFmail());
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
                    intent.setClass(ForgotPwdActivity.this, VerifyFindPwdActivity.class);
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
