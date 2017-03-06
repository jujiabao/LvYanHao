package com.lvyanhao.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.lvyanhao.R;
import com.lvyanhao.activity.FilmInfoListViewActivity;
import com.lvyanhao.component.MyFilmInfoListViewAdapter;
import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.vo.FilmListInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by lyh on 2017/3/5.
 */

public class MainFragment extends Fragment {

    private ListView listView;
    private PullToRefreshLayout ptrl;
    private boolean isFirstIn = true;

    private Context mContext;

    List<FilmListInfoVo> items;
    MyFilmInfoListViewAdapter adapter;


    @TargetApi(Build.VERSION_CODES.M)
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View mainLayout = inflater.inflate(R.layout.activity_listview,container,false);
        mContext = getContext();
        ptrl = ((PullToRefreshLayout)mainLayout.findViewById(R.id.refresh_view));
        ptrl.setOnRefreshListener(new MyListener());
        listView = (ListView)mainLayout.findViewById(R.id.content_view);
        initListView();

        return mainLayout;

    }

    private void initListView()
    {
        items = new ArrayList<FilmListInfoVo>();
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
        listView.setAdapter(adapter);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener()
        {

            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id)
            {
                Toast.makeText(
                        mContext,
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
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {
                    //ˢ�������
                    items.clear();
                    for (int i = 0; i < 2; i++) {
                        FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
                        filmListInfoVo.setFid(UUID.randomUUID().toString());
                        filmListInfoVo.setFilmName(UUID.randomUUID().toString());
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
            }.sendEmptyMessageDelayed(0, 5000);
        }

        @Override
        public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
            /**
             * �����Ҫ��������
             */
            new Handler()
            {
                @Override
                public void handleMessage(Message msg)
                {

                    /**
                     * �����������������ص�����
                     */
                    for (int i = 0; i < 2; i++) {
                        FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
                        filmListInfoVo.setFid(UUID.randomUUID().toString());
                        filmListInfoVo.setFilmName(UUID.randomUUID().toString());
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
            }.sendEmptyMessageDelayed(0, 1000);
        }
    }
}
