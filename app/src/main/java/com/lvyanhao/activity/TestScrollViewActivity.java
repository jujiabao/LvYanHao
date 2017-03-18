package com.lvyanhao.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
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
import com.lvyanhao.component.MyFilmCommentViewAdapter;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.layout.PullToRefreshLayout;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.CommentListLoadMoreReqVo;
import com.lvyanhao.vo.CommentListLoadMoreRspVo;
import com.lvyanhao.vo.CommentListRefreshReqVo;
import com.lvyanhao.vo.FilmDetailReqVo;
import com.lvyanhao.vo.FilmDetailRspVo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * Created by lyh on 2017/3/12.
 */

public class TestScrollViewActivity extends BaseActivity implements View.OnClickListener {

    private ImageView blurImageView;
    private ImageView filmPostIm;

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
    private List<CommentListLoadMoreRspVo> items;
    private MyFilmCommentViewAdapter adapter;
    private Context mContext;

    private Button comment_agree_btn;

    private static String fid = null;
    private Bundle bundle;

    private String token;
    private PullToRefreshLayout myPullToRefreshLayout;
    private PullToRefreshLayout ptrl;
    private boolean isFirstIn = true;
    private MyFilmDetailAsyncTask myFilmDetailAsyncTask;
    private MyCommentListAsyncTask myCommentListAsyncTask;
    private static final int IS_REFRESH = 0;
    private static final int IS_LOADMORE = 1;
    private int option_status;
    private static final int LIMIT_PAGE = 5;
    private List<CommentListLoadMoreRspVo> rspVoList = new ArrayList<>();

    //��Ӱ����ҳ��ĳ�ʼ��
    private ImageView iv_blur;
    private ImageView iv_filmPost;
    private TextView tv_filmna;
    private TextView tv_filmena;
    private TextView tv_filmlv;
    private TextView tv_filmcount;
    private TextView tv_filmtp;
    private TextView tv_filmarea;
    private TextView tv_filmtm;
    private TextView tv_filmplaytime;
    private TextView tv_filmintro;

    //���۰�ť
    private Button btn_want;
    private Button btn_comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_scrollview);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_with_left_btn);
        mContext = getApplicationContext();
        bundle = this.getIntent().getExtras();
        fid = bundle.getString("fid");
        System.out.println("��ǰ��ӰID��"+fid);
        ptrl = ((PullToRefreshLayout) findViewById(R.id.film_refresh_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView)findViewById(R.id.content_view);
        findViews();
        initData();
        initView();
        initListView();
        comment();
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
                        mContext,
                        "LongClick on "
                                + position,
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
                Toast.makeText(mContext,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void initView() {
        ((TextView)findViewById(R.id.title_name)).setText(bundle.getString("fname"));
        ((Button)findViewById(R.id.head_left_btn)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
            }
        });
        mContentText = (TextView) findViewById(R.id.film_content);
        mShowMore = (RelativeLayout) findViewById(R.id.show_more);
        mImageSpread = (ImageView) findViewById(R.id.more);
        mImageShrinkUp = (ImageView) findViewById(R.id.more_up);
        comment_agree_btn = (Button) findViewById(R.id.comment_agree_btn);
        mShowMore.setOnClickListener(this);
        //��Ӱ�������ĳ�ʼ��
        iv_blur = (ImageView) findViewById(R.id.iv_blur);
        iv_filmPost = (ImageView) findViewById(R.id.iv_filmpost);
        tv_filmna = (TextView) findViewById(R.id.filmna);
        tv_filmena = (TextView) findViewById(R.id.filmena);
        tv_filmlv = (TextView) findViewById(R.id.filmlv);
        tv_filmcount = (TextView) findViewById(R.id.filmcount);
        tv_filmtp = (TextView) findViewById(R.id.filmtp);
        tv_filmarea = (TextView) findViewById(R.id.filmarea);
        tv_filmtm = (TextView) findViewById(R.id.filmtm);
        tv_filmplaytime = (TextView) findViewById(R.id.filmplaytime);
        tv_filmintro = (TextView) findViewById(R.id.film_content);
        //��ť��ʼ��
        btn_want = (Button) findViewById(R.id.btn_want);
        btn_comment = (Button) findViewById(R.id.btn_comment);
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus)
    {
        super.onWindowFocusChanged(hasFocus);
        // ��һ�ν����Զ�ˢ��
        if (isFirstIn)
        {
            /**
             * ��һ�ε������ʱ���������߳�ˢ��ҳ��
             */
            ptrl.autoRefresh();
            isFirstIn = false;
        }
    }

    //����
    public void comment(){
        btn_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TestScrollViewActivity.this,CommentActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("fname", tv_filmna.getText().toString());
                intent.putExtras(bundle);
                startActivity(intent);
                overridePendingTransition(R.anim.in_from_top, R.anim.out_to_bottom);
            }
        });
    }

    public void onClick(View v) {
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
        filmPostIm = (ImageView) findViewById(R.id.iv_filmpost);
    }

    private void initData(){
        Glide.with(this).load(R.drawable.test_image)
                .bitmapTransform(new BlurTransformation(this, 75), new CenterCrop(this))
                .into(blurImageView);
//        filmPostIm = (ImageView) findViewById(R.id.comment_avator);
//        Glide.with(this).load("http://game.gtimg.cn/images/lol/act/a20170103spring/unit.jpg").bitmapTransform(new CropCircleTransformation(this))
//                .into(filmPostIm);

       /* Glide.with(this).load(R.drawable.test_image)
                .bitmapTransform(new CropCircleTransformation(this))
                .into(filmPostIm); */
//        filmPostIm.setImageDrawable(getResources().getDrawable(R.drawable.test_image));
        /*filmPostIm.setBackgroundDrawable(getResources().getDrawable(R.drawable.test));*/
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            option_status = IS_REFRESH;
            myPullToRefreshLayout = pullToRefreshLayout;
            /**
             * ִ�е�Ӱ�����ȡ�߳�
             */
            myFilmDetailAsyncTask = new MyFilmDetailAsyncTask(mContext);
            myFilmDetailAsyncTask.execute();

            /**
             * ִ�������б�����߳�
             */
            myCommentListAsyncTask = new MyCommentListAsyncTask(mContext);
            myCommentListAsyncTask.execute();
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            option_status = IS_LOADMORE;
            myPullToRefreshLayout = pullToRefreshLayout;
            myCommentListAsyncTask = new MyCommentListAsyncTask(mContext);
            myCommentListAsyncTask.execute();
        }
    }

    /**
     * ��Ӱ�����б�
     */
    class MyFilmDetailAsyncTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog = null;
        private Context context;

        //���ر���ͷ����
        private ResultDto resultDto = null;
        private FilmDetailRspVo rspVo = null;

        public MyFilmDetailAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            token = SystemUtil.getTokenValueFromSP(mContext);
            Log.d("lvyanhao-token", "ȡ��SharedPreferences�е�TOKENֵ="+token);
            int flag = 99999;
            //��Ӱ����
            FilmDetailReqVo reqVo = new FilmDetailReqVo();
            reqVo.setFid(fid);
            Log.d("lvyanhao", "@ ƴ����Ϣ FilmDetailReqVo="+ reqVo);
            //дjson
            Gson gson = new Gson();
            Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(reqVo));
            String rsp = NetUtil.post(mContext, "/eval/film/detail.do", gson.toJson(reqVo), token);
            //���ر��ľ������
            rspVo = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<FilmDetailRspVo>(){}.getType();
                //ת������������
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ ���ر����壺data="+data);
                rspVo = gson.fromJson(data, t);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ ��������ϸ��"+rspVo);
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
            switch (values[0]) {
                case 0:
                    /**
                     * ���ñ�����˹ģ��Ч��
                     */
                    Glide.with(context).load("http://"+ SystemUtil.getIpAndPortFromSP(context).get("ip")+":"+SystemUtil.getIpAndPortFromSP(context).get("port")+"/LvYanHaoServer"+rspVo.getFpicurl())
                            .error(R.color.black)
                            .bitmapTransform(new BlurTransformation(context, 75), new CenterCrop(context))
                            .into(iv_blur);
                    /**
                     * ���õ�Ӱ����
                     */
                    Glide.with(context)
                            .load("http://"+ SystemUtil.getIpAndPortFromSP(context).get("ip")+":"+SystemUtil.getIpAndPortFromSP(context).get("port")+"/LvYanHaoServer"+rspVo.getFpicurl())
                            .error(R.drawable.film_not_found)
                            .into(iv_filmPost);
                    tv_filmna.setText(rspVo.getFna());
                    tv_filmena.setText(rspVo.getFena());
                    tv_filmlv.setText(rspVo.getFgrade());
                    tv_filmcount.setText(rspVo.getFcount());
                    tv_filmtp.setText(rspVo.getFtp());
                    tv_filmarea.setText(rspVo.getFarea());
                    tv_filmtm.setText(rspVo.getFdura());
                    tv_filmplaytime.setText(rspVo.getFontm());
                    tv_filmintro.setText(rspVo.getFintro());

                    myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    break;
                case 99999:
                    Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
                    myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
                case 99998:
                    Toast.makeText(context, "�޷��ж��Ĳ�����", Toast.LENGTH_SHORT).show();
                    myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
                default:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
            }
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

    };

    /**
     * ���������б�
     */
    class MyCommentListAsyncTask extends AsyncTask<Void, Integer, Void> {
        private ProgressDialog dialog = null;
        private Context context;

        //���ر���ͷ����
        private ResultDto resultDto = null;
        private List<CommentListLoadMoreRspVo> rspVos = new ArrayList<>();

        public MyCommentListAsyncTask(Context context) {
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            token = SystemUtil.getTokenValueFromSP(mContext);
            Log.d("lvyanhao-token", "ȡ��SharedPreferences�е�TOKENֵ="+token);
            int flag = 99999;
            //дjson
            Gson gson = new Gson();
            String rsp = "";
            if (option_status == IS_REFRESH) {
                int limit = rspVos.size();
                Log.d("lvyanhao", "limit="+limit);
                if (limit == 0) {
                    limit = LIMIT_PAGE;
                }
                rspVoList.clear();
                CommentListRefreshReqVo reqVo = new CommentListRefreshReqVo();
                reqVo.setCfid(fid);
                reqVo.setClimit(""+limit);
                Log.d("lvyanhao", "@ ƴ����Ϣ CommentListRefreshReqVo="+ reqVo);
                Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(reqVo));
                rsp = NetUtil.post(mContext, "/eval/comment/refresh.do", gson.toJson(reqVo), token);
            } else if (option_status == IS_LOADMORE) {
                CommentListLoadMoreReqVo reqVo = new CommentListLoadMoreReqVo();
                reqVo.setCfid(fid);
                reqVo.setCst(""+rspVoList.size());
                reqVo.setClimit(""+LIMIT_PAGE);
                Log.d("lvyanhao", "@ ƴ����Ϣ CommentListLoadMoreReqVo="+ reqVo);
                Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(reqVo));
                rsp = NetUtil.post(mContext, "/eval/comment/ldmore.do", gson.toJson(reqVo), token);
            } else {
                publishProgress(99998);
                Log.d("jujiabao", "�޷��ж��Ĳ���:"+option_status);
                return null;
            }
            //���ر��ľ������
            rspVos = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<List<CommentListLoadMoreRspVo>>(){}.getType();
                //ת������������
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ ���ر����壺data="+data);
                rspVos = gson.fromJson(data, t);
                rspVoList.addAll(rspVos);
            } catch (Exception e) {
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
            switch (values[0]) {
                case 0:
                    adapter = new MyFilmCommentViewAdapter(context, rspVoList);
                    listView.setAdapter(adapter);
                    if (option_status == IS_LOADMORE) {
                        listView.setSelection(listView.getCount() - (2*LIMIT_PAGE-1));
                    }
                    adapter.notifyDataSetChanged();
                    SystemUtil.setListViewHeightBasedOnChildren(listView);
                    if (option_status == IS_LOADMORE)
                        myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                    break;
                case 99999:
                    Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
                    if (option_status == IS_LOADMORE)
                        myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
                case 99998:
                    Toast.makeText(context, "�޷��ж��Ĳ�����", Toast.LENGTH_SHORT).show();
                    if (option_status == IS_LOADMORE)
                        myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
                default:
                    Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
                    if (option_status == IS_LOADMORE)
                        myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
            }
        }

        @Override
        protected void onCancelled(Void result) {
            super.onCancelled(result);
        }

    };

}
