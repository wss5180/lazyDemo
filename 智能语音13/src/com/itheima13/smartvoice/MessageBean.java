package com.itheima13.smartvoice;

/**
 * 说的或者听的信息
 * @author Administrator
 *
 */
public class MessageBean {
	public static final  int ASKER = 1;
	public static final int ANSWER = 2;
	public int  messType; //消息类型 
	
	public String content;//消息内容
	
	public int picId = -1; //-1没有图片
}
