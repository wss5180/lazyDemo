package com.itheima13.utils;

import android.content.Context;
import android.os.Bundle;

import com.google.gson.Gson;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SynthesizerListener;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.cloud.ui.RecognizerDialogListener;
import com.itheima13.utils.Word.WS;

public class VoiceUtils {

	public interface OnResultMessageListener {
		void listen(String mess);//语音转换的结果
	}
	public static void listen(Context context,final OnResultMessageListener listener) {

		// 1.创建SpeechRecognizer对象，第二个参数：本地听写时传InitListener
		RecognizerDialog iatDialog = new RecognizerDialog(context,
				new InitListener() {

					@Override
					public void onInit(int arg0) {
						// TODO Auto-generated method stub

					}
				});
		// 2.设置听写参数，同上节
		// 3.设置回调接口
		iatDialog.setListener(new RecognizerDialogListener() {

			@Override
			public void onResult(RecognizerResult arg0, boolean arg1) {
				// TODO Auto-generated method stub
				//System.out.println(arg0.getResultString());
				//解析json
				
				String mess = parseData(arg0.getResultString());
				listener.listen(mess);
			}

			@Override
			public void onError(SpeechError arg0) {
				// TODO Auto-generated method stub

			}
		});
		// 4.开始听写
		iatDialog.show();

	}

	protected static String parseData(String resultString) {
		// TODO Auto-generated method stub
		Gson gson = new Gson();
		Word word = gson.fromJson(resultString, Word.class);
		String mess = "";
		for (WS mWs:word.ws){
			mess += mWs.cw.get(0).w;
		}
		if (mess.length() == 1 && mess.charAt(0) == '。' ) {
			return "";
		}
		return mess;
	}

	public static void speak(Context context, String mess, String person) {

		// 1.创建SpeechSynthesizer对象, 第二个参数：本地合成时传InitListener
		SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context,
				null);
		// 2.合成参数设置，详见《科大讯飞MSC API手册(Android)》SpeechSynthesizer 类
		if (person == null || person.length() == 0) {
			mTts.setParameter(SpeechConstant.VOICE_NAME, "vixm");// 设置发音人
		} else {
			mTts.setParameter(SpeechConstant.VOICE_NAME, person);// 设置发音人
		}
		mTts.setParameter(SpeechConstant.SPEED, "50");// 设置语速
		mTts.setParameter(SpeechConstant.VOLUME, "80");// 设置音量，范围0~100
		mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); // 设置云端
		// 设置合成音频保存位置（可自定义保存位置），保存在“./sdcard/iflytek.pcm”
		// 保存在SD卡需要在AndroidManifest.xml添加写SD卡权限
		// 如果不需要保存合成音频，注释该行代码
		mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, "./sdcard/iflytek.pcm");
		// 3.开始合成
		mTts.startSpeaking(mess, new SynthesizerListener() {

			@Override
			public void onSpeakResumed() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSpeakProgress(int arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSpeakPaused() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSpeakBegin() {
				// TODO Auto-generated method stub

			}

			@Override
			public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onCompleted(SpeechError arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onBufferProgress(int arg0, int arg1, int arg2,
					String arg3) {
				// TODO Auto-generated method stub

			}
		});
	}
}
