package com.example.networktest;

//����һ���ӿڣ�����
public interface HttpCallbackListener {
	 void onFinish(String response);
	 void onError(Exception e);
}
