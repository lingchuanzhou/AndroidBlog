package com.example.networktest;

import android.util.Log;

public class ApplyHttpUtil {
	//ʹ��д�õ�HttpUtil���������ܽ�����������
	public void  requestWeb() {
		HttpUtil.sendHttpRequest("http://www.baidu.com", new HttpCallbackListener() {
			
			@Override
			public void onFinish(String response) {
				Log.v("ReturnBack", response);
			}
			
			@Override
			public void onError(Exception e) {
				// TODO Auto-generated method stub
				
			}
		});
	}
}
