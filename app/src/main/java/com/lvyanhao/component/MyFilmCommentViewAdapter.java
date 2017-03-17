package com.lvyanhao.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lvyanhao.R;
import com.lvyanhao.dto.ResultDto;
import com.lvyanhao.pullableview.PullToRefreshLayout;
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.CommentAgreeReqVo;
import com.lvyanhao.vo.CommentAgreeRspVo;
import com.lvyanhao.vo.CommentListLoadMoreReqVo;
import com.lvyanhao.vo.CommentListLoadMoreRspVo;
import com.lvyanhao.vo.CommentListRefreshReqVo;
import com.lvyanhao.vo.CommentListRefreshRspVo;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;
import jp.wasabeef.glide.transformations.CropSquareTransformation;

public class MyFilmCommentViewAdapter extends BaseAdapter {
	List<CommentListLoadMoreRspVo> items;
	Context context;
	Button agreeBtn;
	View mView;

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
		mView = view;

		ImageView avatorIm = (ImageView) view.findViewById(R.id.comment_avator);
		Glide.with(context).load("http://static.hdslb.com/mobile/img/512.png")
				.bitmapTransform(new CropCircleTransformation(context))
				.into(avatorIm);

		TextView cunick = (TextView) view.findViewById(R.id.comment_usernick);
		cunick.setText(items.get(position).getCunick());

		RatingBar ratingBar = (RatingBar) view.findViewById(R.id.comment_grade_bar);
		ratingBar.setStepSize(0.2f);
		try {
			ratingBar.setRating(Float.parseFloat(items.get(position).getCgrade())/2);
		} catch (Exception e) {
			ratingBar.setRating(5/2);
		}

		TextView cgrade = (TextView) view.findViewById(R.id.comment_grade);
		cgrade.setText(items.get(position).getCgrade());

		TextView ccreatime = (TextView) view.findViewById(R.id.comment_datetime);
		ccreatime.setText(items.get(position).getCcreatime());

		TextView ccontent = (TextView) view.findViewById(R.id.comment_content);
		ccontent.setText(items.get(position).getCcontent());

		//���õ��ް�ť״̬
		agreeBtn = (Button) view.findViewById(R.id.comment_agree_btn);
		if ("1".equals(items.get(position).getCstatus())) {
			//״̬������
			agreeBtn.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.agree_gray));
		} else {
			//״̬��������
			agreeBtn.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.agree_red));
		}
		agreeBtn.setOnClickListener(new MyAgreeBtnListener(position));

		TextView cagreetime = (TextView) view.findViewById(R.id.comment_agree_times);
		cagreetime.setText(items.get(position).getCagreetime());

		return view;
	}

	/**
	 * ���ް�ť�¼�����
	 */
	private class MyAgreeBtnListener implements View.OnClickListener {

		int position;

		public MyAgreeBtnListener(int position) {
			this.position = position;
		}

		@Override
		public void onClick(View view) {
//			Toast.makeText(context, "����"+items.get(position).getCcid(), Toast.LENGTH_SHORT).show();
			MyAgreeAsyncTask myAgreeAsyncTask = new MyAgreeAsyncTask(context, position);
			myAgreeAsyncTask.execute();
		}
	}

	/**
	 * ���޷������߼�ʵ��
	 */
	private class MyAgreeAsyncTask extends AsyncTask<Void, Integer, Void> {
		private ProgressDialog dialog = null;
		private Context context;
		private int position;

		//���ر���ͷ����
		private ResultDto resultDto = null;
		private CommentAgreeRspVo rspVo = null;


		public MyAgreeAsyncTask(Context context, int position) {
			this.context = context;
			this.position = position;
		}

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@SuppressWarnings("WrongThread")
		@Override
		protected Void doInBackground(Void... arg0) {
			String token = SystemUtil.getTokenValueFromSP(context);
			Log.d("lvyanhao-token", "ȡ��SharedPreferences�е�TOKENֵ="+token);
			int flag = 99999;
			CommentAgreeReqVo reqVo = new CommentAgreeReqVo();
			reqVo.setCfid(items.get(position).getCfid());
			reqVo.setCcid(items.get(position).getCcid());
			System.out.println("@ ��ǰ״̬��"+items.get(position).getCstatus());
			System.out.println("@ ��ǰ����ID��"+items.get(position).getCcid());
			System.out.println("@ ��ǰ��ӰID��"+items.get(position).getCfid());
			if ("1".equals(items.get(position).getCstatus())) {
				//��δ����
				reqVo.setCstatus("1");
			} else {
				//�Ѿ�������
				reqVo.setCstatus("0");
			}
			//дjson
			Gson gson = new Gson();
			Log.d("lvyanhao", "@ ƴ����Ϣ CommentAgreeReqVo="+ reqVo);
			Log.d("lvyanhao", "@ ������������Ϣ��"+gson.toJson(reqVo));
			String rsp = NetUtil.post(context, "/eval/comment/agree.do", gson.toJson(reqVo), token);
			//���ر��ľ������
			rspVo = null;
			try {
				resultDto = gson.fromJson(rsp, ResultDto.class);
				Log.d("lvyanhao", "@ �����ϸ��"+ resultDto.getStatus() + "��" + resultDto.getMsg());
				//������ص�
				Type t = new TypeToken<CommentAgreeRspVo>(){}.getType();
				//ת������������
				String data = gson.toJson(resultDto.getData());
				Log.d("lvyanhao", "@ ���ر����壺data="+data);
				rspVo = gson.fromJson(data, t);
			} catch (Exception e) {
				e.printStackTrace();
			}
			Log.d("lvyanhao", "@ ��������ϸ��"+rspVo);
			if (resultDto == null) {
				resultDto = new ResultDto();
				resultDto.setStatus("-1");
				resultDto.setMsg("ϵͳδ֪����");
				resultDto.setData(null);
			}
			flag = Integer.parseInt(resultDto.getStatus());
			publishProgress(flag);
			return null;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
			switch (values[0]) {
				case 0:
					if ("1".equals(rspVo.getFlag())) {
						if ("1".equals(rspVo.getCstatus())) {
							//���޲����ɹ�
							Toast.makeText(context, "���޳ɹ���", Toast.LENGTH_SHORT).show();
							agreeBtn.setBackgroundDrawable(mView.getResources().getDrawable(R.drawable.agree_red));
							items.get(position).setCstatus("0");
						} else {
							//ȡ�����޳ɹ�
							Toast.makeText(context, "ȡ���ɹ���", Toast.LENGTH_SHORT).show();
							agreeBtn.setBackgroundDrawable(mView.getResources().getDrawable(R.drawable.agree_gray));
							items.get(position).setCstatus("1");
						}
					} else {
						Toast.makeText(context, "����ʧ�ܣ�", Toast.LENGTH_SHORT).show();
					}
					break;
				case 99999:
					Toast.makeText(context, "�����������", Toast.LENGTH_SHORT).show();
					break;
				default:
					Toast.makeText(context, resultDto.getMsg(), Toast.LENGTH_SHORT).show();
					break;
			}
		}

		@Override
		protected void onCancelled(Void result) {
			super.onCancelled(result);
		}

	};

}
