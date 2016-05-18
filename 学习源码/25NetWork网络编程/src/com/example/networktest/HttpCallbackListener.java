package com.example.networktest;

//定义一个接口，用于
public interface HttpCallbackListener {
	 void onFinish(String response);
	 void onError(Exception e);
}
