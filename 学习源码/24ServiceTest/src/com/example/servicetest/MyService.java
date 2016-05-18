package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

public class MyService extends Service {
	private DownloadBinder mBinder = new DownloadBinder();
	//创建一个继承自Binder的类
	class DownloadBinder extends Binder {
		public void startDownload() {
			Log.d("MyService", "startDownload executed");
		}
		
		public int getProgress() {
			Log.d("MyService", "getProgress executed");
			return 0;
		}
	}
	
	//这是活动控制服务的桥梁：返回一个mBinder对象，调用mBinder中的方法。
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}
	
	//只在服务创建的时候被调用一次
	@Override
	public void onCreate() {
		super.onCreate();
		Log.v("MyService", "onCreate executed");
	}
	
	//每次启动服务的时候被调用
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("MyService", "onStartCommand executed");
		return super.onStartCommand(intent, flags, startId);
	}
	//服务销毁时被调用，用于回收不再使用的资源
	@Override
	public void onDestroy() {
		Log.d("MyService", "OnDestroy executed");
		super.onDestroy();
	}
}
