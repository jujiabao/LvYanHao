package com.lvyanhao.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.component.MyFilmDetailCommentViewAdapter;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.vo.CommentListRefreshReqVo;
import com.lvyanhao.vo.CommentListRefreshRspVo;

import java.lang.reflect.Type;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by lyh on 2017/3/9.
 */

public class FilmDetailCommentActivity extends Activity implements View.OnClickListener  {
    private ImageView blurImageView;
    private ImageView avatarImageView;

    private static final int VIDEO_CONTENT_DESC_MAX_LINE = 3;// Ĭ��չʾ�������3��
    private static final int SHOW_CONTENT_NONE_STATE = 0;// ����
    private static final int SHRINK_UP_STATE = 1;// ����״̬
    private static final int SPREAD_STATE = 2;// չ��״̬
    private static int mState = SHRINK_UP_STATE;//Ĭ������״̬

    private TextView mContentText;// չʾ�ı�����
    private RelativeLayout mShowMore;// չʾ����
    private ImageView mImageSpread;// չ��
    private ImageView mImageShrinkUp;// ����

    private ListView listView;
    private PullToRefreshLayout ptrl;
    private PullToRefreshLayout myPullToRefreshLayout;

    private MyAsyncTask myAsyncTask;
    private Context mContext;
    List<CommentListRefreshRspVo> items;
    private MyFilmDetailCommentViewAdapter adapter;

    private CommentListRefreshReqVo reqVo;
    private CommentListRefreshRspVo rspVo;
    private List<CommentListRefreshRspVo> rspVosList;

    private boolean isFirstIn = true;

    private Bundle bundle;

    private static String fid = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.detail_item_layout);
        setContentView(R.layout.activity_scrollview);
        mContext = getApplicationContext();
        bundle = this.getIntent().getExtras();
        fid = bundle.getString("fid");
        System.out.println("��ǰ��ӰID��"+fid);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.film_refresh_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView) findViewById(R.id.film_comment_view);
        initListView();
        findViews();
        initData();
        initView();
    }



    private void initView() {
        mContentText = (TextView) findViewById(R.id.film_content);
        mShowMore = (RelativeLayout) findViewById(R.id.show_more);
        mImageSpread = (ImageView) findViewById(R.id.more);
        mImageShrinkUp = (ImageView) findViewById(R.id.more_up);
        mShowMore.setOnClickListener(this);

    }

    private void initListView()
    {
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                Toast.makeText(
                        FilmDetailCommentActivity.this,
                        "LongClick on "
                                + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id)
            {
                Log.d("jujiabao", "��ǰitemId="+parent.getAdapter().getItemId(position));
                Toast.makeText(FilmDetailCommentActivity.this,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onClick(View v) {
// TODO Auto-generated method stub
        switch (v.getId()) {
            case R.id.show_more: {
                if (mState == SPREAD_STATE) {
                    mContentText.setMaxLines(VIDEO_CONTENT_DESC_MAX_LINE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.GONE);
                    mImageSpread.setVisibility(View.VISIBLE);
                    mState = SHRINK_UP_STATE;
                } else if (mState == SHRINK_UP_STATE) {
                    mContentText.setMaxLines(Integer.MAX_VALUE);
                    mContentText.requestLayout();
                    mImageShrinkUp.setVisibility(View.VISIBLE);
                    mImageSpread.setVisibility(View.GONE);
                    mState = SPREAD_STATE;
                }
                break;
            }
            default: {
                break;
            }
        }
    }

    private void findViews(){
        blurImageView = (ImageView) findViewById(R.id.iv_blur);
        avatarImageView = (ImageView) findViewById(R.id.iv_filmpost);
    }

    private void initData(){
        Glide.with(this).load(R.drawable.test)
                .bitmapTransform(new BlurTransformation(this, 75), new CenterCrop(this))
                .into(blurImageView);

       /* Glide.with(this).load(R.drawable.test_image)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(avatarImageView); */
//        avatarImageView.setImageDrawable(getResources().getDrawable(R.drawable.test_image));
        avatarImageView.setBackgroundDrawable(getResources().getDrawable(R.drawable.test));
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        // ��һ�ν����Զ�ˢ��
        /*if (isFirstIn)
        {
            myPullToRefreshLayout.autoRefresh();
            isFirstIn = false;
        }*/
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // ����ˢ�²���
            Log.d("lvyanhao", "�����߳�----");
            myPullToRefreshLayout = pullToRefreshLayout;
            myAsyncTask = new MyAsyncTask(mContext);
            myAsyncTask.execute();
            Log.d("lvyanhao", "�߳�end");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {

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
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int flag = 99999;
            CommentListRefreshReqVo reqVo = new CommentListRefreshReqVo();
            reqVo.setCfid("27d48f46fd8411e6b9a99de9b5cfef3f");
            reqVo.setClimit("5s");
            Log.d("lvyanhao", "@ ƴ����Ϣ CommentListRefreshReqVo="+reqVo);
            //дjson
            Gson gson = new Gson();
            Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(reqVo));
//            String rsp = NetUtil.post(mContext, "/user/login.do", gson.toJson(reqVo), "1234567890");
            String rsp = "{\"status\":\"0\",\"msg\":\"ˢ�³ɹ�!\",\"data\":[{\"ccid\":\"03463fd0015d11e7b9a99de9b5cfef3f\",\"cfid\":\"27d48f46fd8411e6b9a99de9b5cfef3f\",\"cava\":null,\"cunick\":\"testuser6\",\"cgrade\":\"10\",\"ccontent\":\"sdvwfjfmfaknfsjdnfs\",\"ccreatime\":\"3��ǰ\",\"cagreetime\":\"3\",\"cstatus\":\"1\",\"climit\":\"5\"},{\"ccid\":\"ee0f8928015c11e7b9a99de9b5cfef3f\",\"cfid\":\"27d48f46fd8411e6b9a99de9b5cfef3f\",\"cava\":null,\"cunick\":\"testuser1\",\"cgrade\":\"9.9\",\"ccontent\":\"casgwredfvsdvwgwer\",\"ccreatime\":\"3��ǰ\",\"cagreetime\":\"2\",\"cstatus\":\"0\",\"climit\":\"5\"},{\"ccid\":\"49ef958ef0f64d748a9985808ea8f49f\",\"cfid\":\"27d48f46fd8411e6b9a99de9b5cfef3f\",\"cava\":\"/pic/avator/default.png\",\"cunick\":\"HelloJu\",\"cgrade\":\"4\",\"ccontent\":\"�ⲻƬ�Ӳ��а���û�м���\",\"ccreatime\":\"1��ǰ\",\"cagreetime\":\"1\",\"cstatus\":\"0\",\"climit\":\"5\"},{\"ccid\":\"c9be732c015c11e7b9a99de9b5cfef3f\",\"cfid\":\"27d48f46fd8411e6b9a99de9b5cfef3f\",\"cava\":null,\"cunick\":\"testuser2\",\"cgrade\":\"9.8\",\"ccontent\":\"aashuenfkjfnsajlasdfn\",\"ccreatime\":\"3��ǰ\",\"cagreetime\":\"1\",\"cstatus\":\"1\",\"climit\":\"5\"}]}";
            Log.d("lvyanhao", "@ ������������Ϣ��"+rsp);

            List<CommentListRefreshRspVo> rspVos = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<List<CommentListRefreshRspVo>>(){}.getType();
                //��������
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ ���ر����壺data="+data);
                rspVos = gson.fromJson(data, t);
                rspVosList = rspVos;
            } catch (Exception e) {
                Toast.makeText(context, "������Ϣ��"+e.getMessage(), Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ ��������ϸ��"+rspVos);
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
//            dialog.dismiss();// �ر�ProgressDialog
            switch (values[0]) {
                case 0:
                    adapter = new MyFilmDetailCommentViewAdapter(mContext, rspVosList);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                    myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
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