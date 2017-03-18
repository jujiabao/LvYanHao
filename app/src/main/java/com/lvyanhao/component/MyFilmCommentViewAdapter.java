package com.lvyanhao.component;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
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
import com.lvyanhao.utils.NetUtil;
import com.lvyanhao.utils.SystemUtil;
import com.lvyanhao.vo.CommentAgreeReqVo;
import com.lvyanhao.vo.CommentAgreeRspVo;
import com.lvyanhao.vo.CommentListLoadMoreRspVo;

import java.lang.reflect.Type;
import java.util.List;

import jp.wasabeef.glide.transformations.CropCircleTransformation;

public class MyFilmCommentViewAdapter extends BaseAdapter {
	List<CommentListLoadMoreRspVo> items;
	Context context;
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
		Button agreeBtn = (Button) view.findViewById(R.id.comment_agree_btn);
		if ("1".equals(items.get(position).getCstatus())) {
			//״̬������
			agreeBtn.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.agree_gray));
		} else {
			//״̬��������
			agreeBtn.setBackgroundDrawable(view.getResources().getDrawable(R.drawable.agree_red));
		}

		TextView cagreetime = (TextView) view.findViewById(R.id.comment_agree_times);
		cagreetime.setText(items.get(position).getCagreetime());

		agreeBtn.setOnClickListener(new MyAgreeBtnListener(position, agreeBtn, cagreetime));

		return view;
	}

	/**
	 * ���ް�ť�¼�����
	 */
	private class MyAgreeBtnListener implements View.OnClickListener {

		int position;
		Button agreeBtn;
		TextView agreeTimesTv;

		public MyAgreeBtnListener(int position, Button agreeBtn, TextView agreeTimesTv) {
			this.position = position;
			this.agreeBtn = agreeBtn;
			this.agreeTimesTv = agreeTimesTv;
		}

		@Override
		public void onClick(View view) {
//			Toast.makeText(context, "����"+items.get(position).getCcid(), Toast.LENGTH_SHORT).show();
			MyAgreeAsyncTask myAgreeAsyncTask = new MyAgreeAsyncTask(context, position, agreeBtn, agreeTimesTv);
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

		private Button agreeBtn;
		private TextView agreeTimesTv;

		//���ر���ͷ����
		private ResultDto resultDto = null;
		private CommentAgreeRspVo rspVo = null;


		public MyAgreeAsyncTask(Context context, int position, Button agreeBtn, TextView agreeTimesTv) {
			this.context = context;
			this.position = position;
			this.agreeBtn = agreeBtn;
			this.agreeTimesTv = agreeTimesTv;
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
							int i = Integer.parseInt(items.get(position).getCagreetime());
							if (i >= 0) {
								agreeTimesTv.setText(""+(++i));
								agreeTimesTv.setTextColor(Color.RED);
								items.get(position).setCagreetime(""+i);
							}
							items.get(position).setCstatus("0");
						} else {
							//ȡ�����޳ɹ�
							Toast.makeText(context, "ȡ���ɹ���", Toast.LENGTH_SHORT).show();
							agreeBtn.setBackgroundDrawable(mView.getResources().getDrawable(R.drawable.agree_gray));
							int i = Integer.parseInt(items.get(position).getCagreetime());
							if (i > 0) {
								agreeTimesTv.setText(""+(--i));
								agreeTimesTv.setTextColor(Color.GRAY);
								items.get(position).setCagreetime(""+i);
							}
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
