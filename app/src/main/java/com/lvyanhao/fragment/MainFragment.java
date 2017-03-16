package com.lvyanhao.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.component.MyFilmInfoListViewAdapter;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.FilmListLoadMoreReqVo;
import com.lvyanhao.vo.FilmListLoadMoreRspVo;
import com.lvyanhao.vo.FilmListRefreshReqVo;
import com.lvyanhao.vo.FilmListRefreshRspVo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by lyh on 2017/3/5.
 */

public class MainFragment extends Fragment {

    private ListView listView;
    private PullToRefreshLayout ptrl;
    private PullToRefreshLayout myPullToRefreshLayout;
    private FilmListRefreshReqVo refreshReqVo;
    private FilmListLoadMoreReqVo loadmoreReqVo;
    private FilmListLoadMoreRspVo rspVo;
    private List<FilmListLoadMoreRspVo> rspVosList = new ArrayList<>();
   // private boolean isFirstIn = true;
    private MyAsyncTask myAsyncTask;

    private Context mContext;

    private ImageView image;

    List<FilmListRefreshRspVo> items;
    MyFilmInfoListViewAdapter adapter;

    private String token;
    private int option_flag = -1;
    private static final int IS_REFRESH = 0;
    private static final int IS_LOADMORE = 1;

    @TargetApi(Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainLayout = inflater.inflate(R.layout.activity_listview,container,false);
        mContext = getActivity().getApplicationContext();
        ptrl = ((PullToRefreshLayout)mainLayout.findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView)mainLayout.findViewById(R.id.content_view);
        image = (ImageView)mainLayout.findViewById(R.id.mimage);
        initListView();
        return mainLayout;

    }

    protected void setResource(Bitmap resource) {
        RoundedBitmapDrawable circularBitmapDrawable = RoundedBitmapDrawableFactory.create(getResources(), resource);
        circularBitmapDrawable.setCornerRadius(15);
        image.setImageDrawable(circularBitmapDrawable);
    }

    private void initListView()
    {
        /**items = new ArrayList<FilmListRefreshRspVo>();
        for (int i = 0; i < 1; i++) {
//			items.add("������item " + i);
            FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
            filmListInfoVo.setFid(UUID.randomUUID().toString());
            filmListInfoVo.setFilmName("��������"+i);
            filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
            filmListInfoVo.setFilmLevel("7.9");
            filmListInfoVo.setFilmSimpleInfo("��ǿ��ɵ��"+i);
            filmListInfoVo.setFilmOpenTime("2017��02��22��");
            items.add(filmListInfoVo);
        }
        adapter = new MyFilmInfoListViewAdapter(mContext, items);
        listView.setAdapter(adapter);**/
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                rspVo = (FilmListLoadMoreRspVo) parent.getAdapter().getItem(position);
                Toast.makeText(
                        mContext,
                        "LongClick on "
                                + rspVo.getFid(),
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

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // ����ˢ�²���
            /**new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    //ˢ�������
                    items.clear();
                    for (int i = 0; i < 2; i++) {
                        FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
                        filmListInfoVo.setFid(UUID.randomUUID().toString());
                        filmListInfoVo.setFilmName(UUID.randomUUID().toString().substring(0,6));
                        filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
                        filmListInfoVo.setFilmLevel("7.9");
                        filmListInfoVo.setFilmSimpleInfo("��ɵ��"+i);
                        filmListInfoVo.setFilmOpenTime("2017��02��22��");
                        items.add(filmListInfoVo);
                    }
                    adapter = new MyFilmInfoListViewAdapter(mContext, items);
                    listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
                    // ǧ������˸��߿ؼ�ˢ�������Ŷ��
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 5000);**/
            option_flag = IS_REFRESH;
            Log.d("lvyanhao", "�����߳�----");
            myPullToRefreshLayout = pullToRefreshLayout;
            myAsyncTask = new MyAsyncTask(mContext);
            myAsyncTask.execute();
            Log.d("lvyanhao", "�߳�end");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            /**
             * �����Ҫ��������
             */
            /**new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {


                    for (int i = 0; i < 2; i++) {
                        FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
                        filmListInfoVo.setFid(UUID.randomUUID().toString());
                        filmListInfoVo.setFilmName(UUID.randomUUID().toString().substring(0,6));
                        filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
                        filmListInfoVo.setFilmLevel("7.9");
                        filmListInfoVo.setFilmSimpleInfo("��ɵ��"+i);
                        filmListInfoVo.setFilmOpenTime("2017��02��22��");
                        items.add(filmListInfoVo);
                    }
                    adapter = new MyFilmInfoListViewAdapter(mContext, items);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    // ǧ������˸��߿ؼ����������Ŷ��
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

                }
            }.sendEmptyMessageDelayed(0, 5000);**/
            option_flag = IS_LOADMORE;
            Log.d("lvyanhao", "�����߳�----");
            myPullToRefreshLayout = pullToRefreshLayout;
            myAsyncTask = new MyAsyncTask(mContext);
            myAsyncTask.execute();
            Log.d("lvyanhao", "�߳�end");
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
            /*dialog = ProgressDialog.show(context, null, "���ڵ�¼�����Ժ�...", false);
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
            });*/
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("lvyanhao", "��ǰ��־��"+option_flag);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            token = SystemUtil.getTokenValueFromSP(mContext);
            Log.d("lvyanhao-token", "ȡ��SharedPreferences�е�TOKENֵ="+token);
            int flag = 99999;
            //дjson
            Gson gson = new Gson();
            String rsp = "";
            if (option_flag == IS_REFRESH) {
                int limit = rspVosList.size();
                Log.d("lvyanhao", "limit="+limit);
                if (limit == 0) {
                    limit = 5;
                }
                rspVosList.clear();
                refreshReqVo = new FilmListRefreshReqVo();
                refreshReqVo.setFlimit(""+limit);
                Log.d("lvyanhao", "@ ƴ����Ϣ FilmListRefreshReqVo="+ refreshReqVo);
                Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(refreshReqVo));
                rsp = NetUtil.post(mContext, "/eval/film/refresh.do", gson.toJson(refreshReqVo), token);
//            String rsp = "{\"status\":\"0\",\"msg\":\"���سɹ�!\",\"data\":[{\"fid\":\"e0a29c46fd8611e6b9a99de9b5cfef3f\",\"fna\":\"��Ů��Ұ��\",\"fgrade\":null,\"fseio\":\"ͯ�������䣬����������\",\"fontm\":\"2017-03-17��½��ӳ\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"e0a2683efd8611e6b9a99de9b5cfef3f\",\"fna\":\"�ٶ��뼤��8\",\"fgrade\":null,\"fseio\":\"�������������Ҫ����\",\"fontm\":\"2017-04��½��ӳ\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"7563e300fd8511e6b9a99de9b5cfef3f\",\"fna\":\"һ������ʹ��\",\"fgrade\":\"10.0\",\"fseio\":\"���������ˣ�����������\",\"fontm\":\"2017-03-03��½��ӳ\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"7563ad40fd8511e6b9a99de9b5cfef3f\",\"fna\":\"����֮��\",\"fgrade\":null,\"fseio\":\"��ʿ���ټң�����������\",\"fontm\":\"2017-02-14��½��ӳ\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"d74784e2fd8411e6b9a99de9b5cfef3f\",\"fna\":\"�˷�����\",\"fgrade\":null,\"fseio\":\"������׷����������;\",\"fontm\":\"2017-01-28��½��ӳ\",\"fpicurl\":\"/film/post/default.jpg\"}]}";
                Log.d("lvyanhao", "@ ������������Ϣ��"+rsp);
            } else if (option_flag == IS_LOADMORE) {
                loadmoreReqVo = new FilmListLoadMoreReqVo();
                loadmoreReqVo.setFlimit("5");
                loadmoreReqVo.setFst(""+rspVosList.size());
                Log.d("lvyanhao", "@ ƴ����Ϣ FilmListLoadMoreReqVo="+ loadmoreReqVo);
                Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(loadmoreReqVo));
                rsp = NetUtil.post(mContext, "/eval/film/ldmore.do", gson.toJson(loadmoreReqVo), token);
            } else {
                publishProgress(99998);
                Log.d("jujiabao", "�޷��ж��Ĳ���:"+option_flag);
                return null;
            }
            //���ر��ľ������
            List<FilmListLoadMoreRspVo> rspVos = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
                //������ص�
                Type t = new TypeToken<List<FilmListLoadMoreRspVo>>(){}.getType();
                //ת������������
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ ���ر����壺data="+data);
                rspVos = gson.fromJson(data, t);
                rspVosList.addAll(rspVos);
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
//            dialog.dismiss();// �ر�ProgressDialog
            switch (values[0]) {
                case 0:
                    adapter = new MyFilmInfoListViewAdapter(mContext, rspVosList);
                    listView.setAdapter(adapter);
                    if (option_flag == IS_LOADMORE) {
                        listView.setSelection(listView.getCount() - 5);
                    }
                    adapter.notifyDataSetChanged();
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

}
