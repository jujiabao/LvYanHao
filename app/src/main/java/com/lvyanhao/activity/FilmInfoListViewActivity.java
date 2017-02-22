package com.lvyanhao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ListView;
import android.widget.Toast;

import com.lvyanhao.R;
import com.lvyanhao.component.MyFilmInfoListViewAdapter;
import com.lvyanhao.component.MyFilmInfoListViewListener;
import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.vo.FilmListInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FilmInfoListViewActivity extends Activity
{
	private ListView listView;
	private PullToRefreshLayout ptrl;
	private boolean isFirstIn = true;

	List<FilmListInfoVo> items;
	MyFilmInfoListViewAdapter adapter;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		ptrl.setOnRefreshListener(new MyListener());
		listView = (ListView) findViewById(R.id.content_view);
		initListView();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		// 第一次进入自动刷新
		if (isFirstIn)
		{
			ptrl.autoRefresh();
			isFirstIn = false;
		}
	}

	/**
	 * ListView初始化方法
	 */
	private void initListView()
	{
		items = new ArrayList<FilmListInfoVo>();
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
		adapter = new MyFilmInfoListViewAdapter(this, items);
		listView.setAdapter(adapter);
		listView.setOnItemLongClickListener(new OnItemLongClickListener()
		{

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Toast.makeText(
						FilmInfoListViewActivity.this,
						"LongClick on "
								+ parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		listView.setOnItemClickListener(new OnItemClickListener()
		{

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id)
			{
				Log.d("jujiabao", "当前itemId="+parent.getAdapter().getItemId(position));
				Toast.makeText(FilmInfoListViewActivity.this,
						" Click on " + parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

	class MyListener implements PullToRefreshLayout.OnRefreshListener {

		@Override
		public void onRefresh(final PullToRefreshLayout pullToRefreshLayout) {
			// 下拉刷新操作
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{
					//刷新先清空
					items.clear();
					for (int i = 0; i < 2; i++) {
						FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
						filmListInfoVo.setFid(UUID.randomUUID().toString());
						filmListInfoVo.setFilmName(UUID.randomUUID().toString());
						filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
						filmListInfoVo.setFilmLevel("7.9");
						filmListInfoVo.setFilmSimpleInfo("大傻逼"+i);
						filmListInfoVo.setFilmOpenTime("2017年02月22日");
						items.add(filmListInfoVo);
					}
					adapter = new MyFilmInfoListViewAdapter(FilmInfoListViewActivity.this, items);
					listView.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					// 千万别忘了告诉控件刷新完毕了哦！
					pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
				}
			}.sendEmptyMessageDelayed(0, 5000);
		}

		@Override
		public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout) {
			/**
			 * 这边需要请求网络
			 */
			new Handler()
			{
				@Override
				public void handleMessage(Message msg)
				{

					/**
					 * 以下如何添加上拉加载的数据
					 */
					for (int i = 0; i < 2; i++) {
						FilmListInfoVo filmListInfoVo = new FilmListInfoVo(i);
						filmListInfoVo.setFid(UUID.randomUUID().toString());
						filmListInfoVo.setFilmName(UUID.randomUUID().toString());
						filmListInfoVo.setFilmPosterUrl("http://localhost:8080/FilmSystem/getPic.do");
						filmListInfoVo.setFilmLevel("7.9");
						filmListInfoVo.setFilmSimpleInfo("大傻逼"+i);
						filmListInfoVo.setFilmOpenTime("2017年02月22日");
						items.add(filmListInfoVo);
					}
					adapter = new MyFilmInfoListViewAdapter(FilmInfoListViewActivity.this, items);
					listView.setAdapter(adapter);
					adapter.notifyDataSetChanged();

					// 千万别忘了告诉控件加载完毕了哦！
					pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);

				}
			}.sendEmptyMessageDelayed(0, 1000);
		}
	}
}
