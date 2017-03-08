package com.lvyanhao.component;

import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;

import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.vo.FilmListInfoVo;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class MyFilmInfoListViewListener implements PullToRefreshLayout.OnRefreshListener
{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 下拉刷新操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件刷新完毕了哦！
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// 加载操作
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// 千万别忘了告诉控件加载完毕了哦！
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				/**
				 * 以下如何添加上拉加载的数据
				 */

			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
