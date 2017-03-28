package com.lvyanhao.activity;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.lvyanhao.R;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by Hello.Ju on 17/3/23.
 */
public class WelcomeActivity extends Activity {

    private ProgressBar progressBar;
    private ImageView bgIv;

    private MyNetAsyncTask myNetAsyncTask;
    private Context context;
    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_welcome);
        initData();
        initView();
        initAction();
    }

    private void initData() {
        context = WelcomeActivity.this;
        token = SystemUtil.getTokenValueFromSP(context);
    }

    private void initView() {
        progressBar = (ProgressBar) findViewById(R.id.init_pb);
        bgIv = (ImageView) findViewById(R.id.wc_back);
    }

    /**
     * ��ʼ���¼�
     */
    private void initAction() {
        if (token != null && !"".equals(token)) {
            Timer timerIntent = new Timer();
            TimerTask timerTaskIntent = new TimerTask() {
                @Override
                public void run() {
                    myNetAsyncTask = new MyNetAsyncTask(context);
                    myNetAsyncTask.execute();
                }
            };
            timerIntent.schedule(timerTaskIntent, 2000);
        } else {
            gotoLogin(2000);
        }
    }

    /**
     * ��֤ʧ�ܵ���¼����
     * @param waiting
     */
    private void gotoLogin(int waiting) {
        SystemUtil.gotoActivity(context, LoginActivity.class, waiting);
    }

    /**
     * ֱ�ӵ�¼��������
     * @param waiting
     */
    private void gotoMainActivity(int waiting) {
        SystemUtil.gotoActivity(context, FragmentActivity.class, waiting);
    }

    private class MyNetAsyncTask extends AsyncTask<Void, Integer, Void> {
        private Context context;

        //���ر���ͷ����
        private ResultDto resultDto = null;

        public MyNetAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("lvyanhao-token", "ȡ��SharedPreferences�е�TOKENֵ="+token);
            int flag = 99999;
            //дjson
            Gson gson = new Gson();
            Log.d("lvyanhao", "@ ������������Ϣ��"+token);
            String rsp = NetUtil.postForCheck(context, "/debug/checkUserLoginStaus.do", token, token);
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (resultDto == null) {
                resultDto = new ResultDto();
                resultDto.setStatus("-1");
                resultDto.setMsg("ϵͳδ֪����");
                resultDto.setData(null);
            }
            //���ر��ľ������
            flag = Integer.parseInt(resultDto.getStatus());
            publishProgress(flag);
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            switch (values[0]) {
                case 0:
                    NetUtil.flag = true;
                    gotoMainActivity(0);
                    break;
                case 99999:
                    Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
                    gotoLogin(1000);
                    break;
                default:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    gotoLogin(0);
                    break;
            }
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

    };
}
