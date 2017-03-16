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
//			items.add("这里是item " + i);
            FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
            filmListInfoVo.setFid(UUID.randomUUID().toString());
            filmListInfoVo.setFilmName("大闹天竺"+i);
            filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
            filmListInfoVo.setFilmLevel("7.9");
            filmListInfoVo.setFilmSimpleInfo("宝强大傻逼"+i);
            filmListInfoVo.setFilmOpenTime("2017年02月22日");
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
                Log.d("jujiabao", "当前itemId="+parent.getAdapter().getItemId(position));
                Toast.makeText(mContext,
                        " Click on " + parent.getAdapter().getItemId(position),
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    class MyListener implements PullToRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
            // 下拉刷新操作
            /**new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    //刷新先清空
                    items.clear();
                    for (int i = 0; i < 2; i++) {
                        FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
                        filmListInfoVo.setFid(UUID.randomUUID().toString());
                        filmListInfoVo.setFilmName(UUID.randomUUID().toString().substring(0,6));
                        filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
                        filmListInfoVo.setFilmLevel("7.9");
                        filmListInfoVo.setFilmSimpleInfo("大傻逼"+i);
                        filmListInfoVo.setFilmOpenTime("2017年02月22日");
                        items.add(filmListInfoVo);
                    }
                    adapter = new MyFilmInfoListViewAdapter(mContext, items);
                    listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
                    // 千万别忘了告诉控件刷新完毕了哦！
                    pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
                }
            }.sendEmptyMessageDelayed(0, 5000);**/
            option_flag = IS_REFRESH;
            Log.d("lvyanhao", "开启线程----");
            myPullToRefreshLayout = pullToRefreshLayout;
            myAsyncTask = new MyAsyncTask(mContext);
            myAsyncTask.execute();
            Log.d("lvyanhao", "线程end");
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            /**
             * 这边需要请求网络
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
                        filmListInfoVo.setFilmSimpleInfo("大傻逼"+i);
                        filmListInfoVo.setFilmOpenTime("2017年02月22日");
                        items.add(filmListInfoVo);
                    }
                    adapter = new MyFilmInfoListViewAdapter(mContext, items);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();

                    // 千万别忘了告诉控件加载完毕了哦！
                    pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

                }
            }.sendEmptyMessageDelayed(0, 5000);**/
            option_flag = IS_LOADMORE;
            Log.d("lvyanhao", "开启线程----");
            myPullToRefreshLayout = pullToRefreshLayout;
            myAsyncTask = new MyAsyncTask(mContext);
            myAsyncTask.execute();
            Log.d("lvyanhao", "线程end");
        }
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
            /*dialog = ProgressDialog.show(context, null, "正在登录，请稍后...", false);
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
            });*/
        }

        @SuppressWarnings("WrongThread")
        @Override
        protected Void doInBackground(Void... arg0) {
            Log.d("lvyanhao", "当前标志："+option_flag);
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            token = SystemUtil.getTokenValueFromSP(mContext);
            Log.d("lvyanhao-token", "取得SharedPreferences中的TOKEN值="+token);
            int flag = 99999;
            //写json
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
                Log.d("lvyanhao", "@ 拼包信息 FilmListRefreshReqVo="+ refreshReqVo);
                Log.d("lvyanhao", "@ 发往服务器信息："+gson.toJson(refreshReqVo));
                rsp = NetUtil.post(mContext, "/eval/film/refresh.do", gson.toJson(refreshReqVo), token);
//            String rsp = "{\"status\":\"0\",\"msg\":\"加载成功!\",\"data\":[{\"fid\":\"e0a29c46fd8611e6b9a99de9b5cfef3f\",\"fna\":\"美女与野兽\",\"fgrade\":null,\"fseio\":\"童话永经典，公主美流传\",\"fontm\":\"2017-03-17大陆上映\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"e0a2683efd8611e6b9a99de9b5cfef3f\",\"fna\":\"速度与激情8\",\"fgrade\":null,\"fseio\":\"车子又飚起，王者要归来\",\"fontm\":\"2017-04大陆上映\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"7563e300fd8511e6b9a99de9b5cfef3f\",\"fna\":\"一条狗的使命\",\"fgrade\":\"10.0\",\"fseio\":\"狗狗爱主人，重生不分离\",\"fontm\":\"2017-03-03大陆上映\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"7563ad40fd8511e6b9a99de9b5cfef3f\",\"fna\":\"爱乐之城\",\"fgrade\":null,\"fseio\":\"爵士钢琴家，恋爱舞天涯\",\"fontm\":\"2017-02-14大陆上映\",\"fpicurl\":\"/film/post/default.jpg\"},{\"fid\":\"d74784e2fd8411e6b9a99de9b5cfef3f\",\"fna\":\"乘风破浪\",\"fgrade\":null,\"fseio\":\"阿浪梦追逐，意外入险途\",\"fontm\":\"2017-01-28大陆上映\",\"fpicurl\":\"/film/post/default.jpg\"}]}";
                Log.d("lvyanhao", "@ 服务器返回信息："+rsp);
            } else if (option_flag == IS_LOADMORE) {
                loadmoreReqVo = new FilmListLoadMoreReqVo();
                loadmoreReqVo.setFlimit("5");
                loadmoreReqVo.setFst(""+rspVosList.size());
                Log.d("lvyanhao", "@ 拼包信息 FilmListLoadMoreReqVo="+ loadmoreReqVo);
                Log.d("lvyanhao", "@ 发往服务器信息："+gson.toJson(loadmoreReqVo));
                rsp = NetUtil.post(mContext, "/eval/film/ldmore.do", gson.toJson(loadmoreReqVo), token);
            } else {
                publishProgress(99998);
                Log.d("jujiabao", "无法判定的操作:"+option_flag);
                return null;
            }
            //返回报文具体操作
            List<FilmListLoadMoreRspVo> rspVos = null;
            try {
                resultDto = gson.fromJson(rsp, ResultDto.class);
                Log.d("lvyanhao", "@ 拆包明细："+ resultDto.getStatus() + "，" + resultDto.getMsg());
                //拆包返回的
                Type t = new TypeToken<List<FilmListLoadMoreRspVo>>(){}.getType();
                //转换报文体内容
                String data = gson.toJson(resultDto.getData());
                Log.d("lvyanhao", "@ 返回报文体：data="+data);
                rspVos = gson.fromJson(data, t);
                rspVosList.addAll(rspVos);
            } catch (Exception e) {
                e.printStackTrace();
            }
            Log.d("lvyanhao", "@ 报文体明细："+rspVos);
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
//            dialog.dismiss();// 关闭ProgressDialog
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
                    Toast.makeText(context, "连接网络错误！", Toast.LENGTH_SHORT).show();
                    myPullToRefreshLayout.refreshFinish(PullToRefreshLayout.FAIL);
                    break;
                case 99998:
                    Toast.makeText(context, "无法判定的操作！", Toast.LENGTH_SHORT).show();
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
