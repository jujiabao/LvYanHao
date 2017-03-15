package com.lvyanhao.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lvyanhao.R;
import com.lvyanhao.vo.CommentListRefreshReqVo;
import com.lvyanhao.vo.CommentListRefreshRspVo;
import com.lvyanhao.vo.FilmListRefreshRspVo;

import java.util.List;

public class MyFilmDetailCommentViewAdapter extends BaseAdapter {
	List<CommentListRefreshRspVo> items;
	Context context;

	public MyFilmDetailCommentViewAdapter(Context context, List<CommentListRefreshRspVo> items) {
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
				R.layout.list_item_dc_layout, null);
		TextView filmnaTv = (TextView) view.findViewById(R.id.filmna);
//		tv.setText(items.get(position));
		filmnaTv.setText(items.get(position).getCcid());

		TextView filmLvTv = (TextView) view.findViewById(R.id.filmlv);
		filmLvTv.setText(items.get(position).getCcontent());

		TextView filmIntroduceTv = (TextView) view.findViewById(R.id.filminfo);
		filmIntroduceTv.setText(items.get(position).getCgrade());

		TextView filmOpenTv = (TextView) view.findViewById(R.id.filmplaytime);
		filmOpenTv.setText(items.get(position).getClimit());

		return view;
	}

}
