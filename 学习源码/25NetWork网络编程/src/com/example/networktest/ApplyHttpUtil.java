package com.example.networktest;

import android.util.Log;

public class ApplyHttpUtil {
	//使用写好的HttpUtil网络请求框架进行网络请求。
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
