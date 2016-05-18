package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	private DownloadBinder mBinder = new DownloadBinder();
	//����һ���̳���Binder����
	class DownloadBinder extends Binder {
		public void startDownload() {
			Log.d("MyService", "startDownload executed");
		}
		
		public int getProgress() {
			Log.d("MyService", "getProgress executed");
			return 0;
		}
	}
	
	//���ǻ���Ʒ��������������һ��mBinder���󣬵���mBinder�еķ�����
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	//ֻ�ڷ��񴴽���ʱ�򱻵���һ��
	@Override
	public void onCreate() {
		super.onCreate();
		Log.v("MyService", "onCreate executed");
	}
	
	//ÿ�����������ʱ�򱻵���
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService", "onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}
	//��������ʱ�����ã����ڻ��ղ���ʹ�õ���Դ
	@Override
	public void onDestroy() {
		Log.d("MyService", "OnDestroy executed");
		super.onDestroy();
	}
}
