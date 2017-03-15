package com.lvyanhao.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lvyanhao.R;
import com.lvyanhao.vo.CommentListLoadMoreRspVo;
import com.lvyanhao.vo.CommentListRefreshRspVo;

import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

public class MyFilmCommentViewAdapter extends BaseAdapter {
	List<CommentListLoadMoreRspVo> items;
	Context context;

	public MyFilmCommentViewAdapter(Context context, List<CommentListLoadMoreRspVo> items) {
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
				R.layout.detail_comment_layout, null);

		ImageView avatorIm = (ImageView) view.findViewById(R.id.comment_avator);
		Glide.with(context).load("http://static.hdslb.com/mobile/img/512.png")
				.bitmapTransform(new CropCircleTransformation(context))
				.into(avatorIm);

		TextView cunick = (TextView) view.findViewById(R.id.comment_usernick);
//		tv.setText(items.get(position));
		cunick.setText(items.get(position).getCunick());

		TextView cgrade = (TextView) view.findViewById(R.id.comment_grade);
		cgrade.setText(items.get(position).getCgrade());

		TextView ccreatime = (TextView) view.findViewById(R.id.comment_datetime);
		ccreatime.setText(items.get(position).getCcreatime());

		TextView ccontent = (TextView) view.findViewById(R.id.comment_content);
		ccontent.setText(items.get(position).getCcontent());

		TextView cagreetime = (TextView) view.findViewById(R.id.comment_agree_times);
		cagreetime.setText(items.get(position).getCagreetime());

		return view;
	}

}
