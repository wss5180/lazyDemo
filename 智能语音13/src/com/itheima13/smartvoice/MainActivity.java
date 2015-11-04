package com.itheima13.smartvoice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.itheima13.utils.SmartAnswerUtils;
import com.itheima13.utils.VoiceUtils;
import com.itheima13.utils.VoiceUtils.OnResultMessageListener;

public class MainActivity extends Activity {

	private ListView lv_showdatas;
	private Button bt_speak;
	private MyAdapter myAdapter;
	
	private List<MessageBean> mDatas = new ArrayList<MessageBean>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		initView();
		SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5621b852");
		initEvent();
		
	}
	
	private class MyAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return mDatas.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ViewHolder viewHolder ;
			if (convertView == null) {
				convertView = View.inflate(getApplicationContext(), R.layout.item_lv, null);
				
				viewHolder = new ViewHolder();
				viewHolder.tv_asker = (TextView) convertView.findViewById(R.id.tv_asker);
				viewHolder.tv_answer = (TextView) convertView.findViewById(R.id.tv_answer);
				
				viewHolder.ll_answer =(LinearLayout) convertView.findViewById(R.id.ll_answer);
				viewHolder.iv_answer = (ImageView) convertView.findViewById(R.id.iv_mn);
				convertView.setTag(viewHolder);
			} else {
				viewHolder = (ViewHolder) convertView.getTag();
			}
			
			//数据
			MessageBean messageBean = mDatas.get(position);
			
			if (messageBean.messType == MessageBean.ASKER) {
				//问
				viewHolder.tv_asker.setVisibility(View.VISIBLE);
				viewHolder.ll_answer.setVisibility(View.GONE);
				//问的内容
				viewHolder.tv_asker.setText(messageBean.content);
			} else {
				//答
				viewHolder.tv_asker.setVisibility(View.GONE);
				viewHolder.ll_answer.setVisibility(View.VISIBLE);
				
				viewHolder.tv_answer.setText(messageBean.content);//答的内容
				
				if (messageBean.picId != -1) {
					//有图片
					viewHolder.iv_answer.setImageResource(messageBean.picId);
				}
			}
			return convertView;
		}
		
	}
	
	private class ViewHolder{
		TextView tv_asker;
		LinearLayout ll_answer;
		TextView tv_answer;
		ImageView iv_answer;
	}

	private void initEvent() {
		//给按钮添加事件
		bt_speak.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//VoiceUtils.speak(getApplicationContext(), "大家好", "");
				// TODO Auto-generated method stub
				VoiceUtils.listen(MainActivity.this, new OnResultMessageListener() {
					
					@Override
					public void listen(String mess) {
						// TODO Auto-generated method stub
						if (TextUtils.isEmpty(mess)) {
							return;
						}
						//mess信息  自己说的话
						MessageBean mb = new MessageBean();
						mb.messType = MessageBean.ASKER;
						mb.content = mess;
						
						//答
						MessageBean mb2 = SmartAnswerUtils.parse(mess);
						//添加到容器中
						mDatas.add(mb);
						mDatas.add(mb2);
						myAdapter.notifyDataSetChanged();
					}
				});
			}
		});
		
	}

	private void initView() {
		// TODO Auto-generated method stub
		setContentView(R.layout.activity_main);
		lv_showdatas = (ListView) findViewById(R.id.lv_datas);
		
		myAdapter = new MyAdapter();
		lv_showdatas.setAdapter(myAdapter);
		bt_speak = (Button) findViewById(R.id.bt_speak);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
