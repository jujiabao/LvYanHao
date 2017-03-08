package com.lvyanhao.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lvyanhao.R;
import com.lvyanhao.vo.FilmListInfoVo;
import com.lvyanhao.vo.FilmListRefreshRspVo;

import java.util.List;

public class MyFilmInfoListViewAdapter extends BaseAdapter {
	List<FilmListRefreshRspVo> items;
	Context context;

	public MyFilmInfoListViewAdapter(Context context, List<FilmListRefreshRspVo> items) {
		this.context = context;
		this.items = items;
	}

	@Override
	public int getCount() {
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(
				R.layout.list_item_layout, null);
		TextView filmnaTv = (TextView) view.findViewById(R.id.filmna);
//		tv.setText(items.get(position));
		filmnaTv.setText(items.get(position).getFna());

		TextView filmLvTv = (TextView) view.findViewById(R.id.filmlv);
		filmLvTv.setText(items.get(position).getFgrade());

		TextView filmIntroduceTv = (TextView) view.findViewById(R.id.filminfo);
		filmIntroduceTv.setText(items.get(position).getFseio());

		TextView filmOpenTv = (TextView) view.findViewById(R.id.filmplaytime);
		filmOpenTv.setText(items.get(position).getFontm());

		return view;
	}

}
