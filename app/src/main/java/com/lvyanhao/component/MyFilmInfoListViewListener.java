package com.lvyanhao.component;

import android.os.Handler;
import android.os.Message;

import com.lvyanhao.layout.PullToRefreshLayout;

public class MyFilmInfoListViewListener implements PullToRefreshLayout.OnRefreshListener
{

	@Override
	public void onRefresh(final PullToRefreshLayout pullToRefreshLayout)
	{
		// ����ˢ�²���
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// ǧ������˸��߿ؼ�ˢ�������Ŷ��
				pullToRefreshLayout.refreshFinish(PullToRefreshLayout.SUCCEED);
			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

	@Override
	public void onLoadMore(final PullToRefreshLayout pullToRefreshLayout)
	{
		// ���ز���
		new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				// ǧ������˸��߿ؼ����������Ŷ��
				pullToRefreshLayout.loadmoreFinish(PullToRefreshLayout.SUCCEED);
				/**
				 * �����������������ص�����
				 */

			}
		}.sendEmptyMessageDelayed(0, 5000);
	}

}
