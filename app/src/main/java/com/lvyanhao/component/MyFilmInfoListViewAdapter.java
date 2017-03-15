package com.lvyanhao.component;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvyanhao.R;
import com.lvyanhao.vo.FilmListInfoVo;
import com.lvyanhao.vo.FilmListRefreshRspVo;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

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

		ImageView avatorIm = (ImageView) view.findViewById(R.id.mimage);
		Glide.with(context).load("http://192.168.1.107:8888/LvYanHaoServer/pic/post/f6b199cbec71452ab9df3f618d052326_zj.jpeg")

				.into(avatorIm);

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
