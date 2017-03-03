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
import com.lvyanhao.vo.UserVerifyMailReqVo;

import java.lang.reflect.Type;

/**
 * Created by lyh on 2017/3/1.
 */

public class VerifyRgcodeActivity extends Activity{

    private Context mContext;

    private TextView vname;

    private TextView vnick;

    private TextView vemail;

    private TextView vmobile;

    private EditText vcode;

    private Button btnsure;

    private MyAsyncTask myAsyncTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verifyrgcode);
        init();
        sureListener();
    }

    private void init(){
        mContext = getApplicationContext();
        btnsure = (Button)findViewById(R.id.verify_sure);
        vname = (TextView) findViewById(R.id.verify_edtId);
        vnick = (TextView) findViewById(R.id.verify_edtNick);
        vemail = (TextView) findViewById(R.id.verify_edtEmil);
        vmobile = (TextView) findViewById(R.id.verify_edtTel);
        vcode = (EditText)findViewById(R.id.verify_edtCode);

        Bundle bundle = this.getIntent().getExtras();
        String name = bundle.getString("suname");
        vname.setText(name);
        String nick = bundle.getString("snick");
        vnick.setText("�ǳ�:"+nick);
        String email = bundle.getString("semail");
        vemail.setText(email);
        String mobile = bundle.getString("smobile");
        vmobile.setText("�ֻ���:"+mobile);
    }



    private void sureListener(){
        btnsure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("lvyanhao","====>���ȷ��");
                myAsyncTask = new VerifyRgcodeActivity.MyAsyncTask(VerifyRgcodeActivity.this);
                myAsyncTask.execute();
                Log.d("lvyanhao","<====���ȷ�ϳɹ�");
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
            dialog = ProgressDialog.show(context, null, "������֤�����Ժ�...", false);
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
            String fmaile = vemail.getText().toString();
            String fname = vname.getText().toString();
            String fcode = vcode.getText().toString();

            Log.d("lvyanhao", "@ ȡ���û��������ݣ�code="+vcode+"name="+vname+"maile+"+vemail);

            UserVerifyMailReqVo reqVo = new UserVerifyMailReqVo();
            reqVo.setVmail(fmaile);
            reqVo.setVname(fname);
            reqVo.setVcode(fcode);
            Log.d("lvyanhao", "@ ƴ����Ϣ UserSignupReqVo="+reqVo);
            //дjson
            Gson gson = new Gson();
            String rsp = NetUtil.post(mContext, "/user/vm.do", gson.toJson(reqVo), "1234567890");
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
                    intent.setClass(VerifyRgcodeActivity.this, LoginActivity.class);
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
