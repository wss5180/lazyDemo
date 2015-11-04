package com.itheima13.utils;

import com.itheima13.smartvoice.MessageBean;
import com.itheima13.smartvoice.R;

public class SmartAnswerUtils {
	private static final String[] mvanswer = new String[]{
		"这个很美","这个如何","不行再换个","非常好","回炉深造吧"
	};
	
	private static final int[] mvpics = new int[]{
		R.drawable.meinv1,R.drawable.meinv2,R.drawable.meinv3,R.drawable.meinv4,R.drawable.meinv2
	};
	public static MessageBean parse(String word) {
		MessageBean bean = new MessageBean();
		bean.messType = MessageBean.ANSWER;
		if (word.contains("美女")) {
			int index = (int)(Math.random() * mvanswer.length);
			bean.content = mvanswer[index];
			bean.picId = mvpics[index];
		} else if (word.contains("帅哥")){
			int index = (int)(Math.random() * mvanswer.length);
			bean.content = mvanswer[index];
			bean.picId = mvpics[index];
		} else {
			int index = (int)(Math.random() * mvanswer.length);
			bean.content = "没听清楚。。。。";
			bean.picId = mvpics[index];
		}
		
		return bean;
	}
}
