package com.example.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener{
	private Button startService;
	private Button stopService;
	private Button bindService;
	private Button unbindService;
	
	private MyService.DownloadBinder downloadBinder;
	
	private ServiceConnection connection = new ServiceConnection() {
		
		//活动与服务解除绑定的时候被调用
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		//活动与服务绑定成功的时候被调用
		/**
		 * onServiceConnected方法：
		 * 参数：
		 * 		IBinder service：一个从Service.onBind()方法返回的对象
		 */
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadBinder = (MyService.DownloadBinder)service;
			downloadBinder.startDownload();
			downloadBinder.getProgress();
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState)  {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		//获取启动服务的按钮对象，并设置监听
		startService = (Button) findViewById(R.id.start_service);
		stopService = (Button) findViewById(R.id.stop_service);
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		//获取绑定服务的按钮对象，并设置监听
		bindService = (Button)findViewById(R.id.bind_service);
		unbindService = (Button)findViewById(R.id.unbind_service);
		bindService.setOnClickListener(this);
		unbindService.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_service:
			Intent startIntent = new Intent(this,MyService.class);	
			startService(startIntent);//通过意图启动服务
			break;
		case R.id.stop_service:
			Intent stopIntent = new Intent(this,MyService.class);
			stopService(stopIntent);//通过意图停止服务
			break;
		case R.id.bind_service:
			Intent bindIntent = new Intent(this, MyService.class);
			/**
			 * bindService方法：
			 * 参数：
			 * 		BIND_AUTO_CREATE:表示活动和服务绑定后自动创建服务
			 */
			//点击按钮绑定服务
			bindService(bindIntent, connection, BIND_AUTO_CREATE);
			break;
		case R.id.unbind_service:
			//点击按钮解除服务
			unbindService(connection);
			break;
		default:
			break;
		}
	}
	
}
