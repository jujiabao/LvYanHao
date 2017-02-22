package com.lvyanhao.activity;

import android.app.Activity;
import android.os.Bundle;
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

	public List<FilmListInfoVo> items;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listview);
		ptrl = ((PullToRefreshLayout) findViewById(R.id.refresh_view));
		ptrl.setOnRefreshListener(new MyFilmInfoListViewListener());
		listView = (ListView) findViewById(R.id.content_view);
		initListView();
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus)
	{
		super.onWindowFocusChanged(hasFocus);
		// ��һ�ν����Զ�ˢ��
		if (isFirstIn)
		{
			ptrl.autoRefresh();
			isFirstIn = false;
		}
	}

	/**
	 * ListView��ʼ������
	 */
	private void initListView()
	{
		items = new ArrayList<FilmListInfoVo>();
		for (int i = 0; i < 30; i++) {
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
		MyFilmInfoListViewAdapter adapter = new MyFilmInfoListViewAdapter(this, items);
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
				Log.d("jujiabao", "��ǰitemId="+parent.getAdapter().getItemId(position));
				Toast.makeText(FilmInfoListViewActivity.this,
						" Click on " + parent.getAdapter().getItemId(position),
						Toast.LENGTH_SHORT).show();
			}
		});
	}

}
