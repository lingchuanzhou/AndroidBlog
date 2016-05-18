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
		
		//���������󶨵�ʱ�򱻵���
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
		}
		//������󶨳ɹ���ʱ�򱻵���
		/**
		 * onServiceConnected������
		 * ������
		 * 		IBinder service��һ����Service.onBind()�������صĶ���
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
		//��ȡ��������İ�ť���󣬲����ü���
		startService = (Button) findViewById(R.id.start_service);
		stopService = (Button) findViewById(R.id.stop_service);
		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		//��ȡ�󶨷���İ�ť���󣬲����ü���
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
			startService(startIntent);//ͨ����ͼ��������
			break;
		case R.id.stop_service:
			Intent stopIntent = new Intent(this,MyService.class);
			stopService(stopIntent);//ͨ����ͼֹͣ����
			break;
		case R.id.bind_service:
			Intent bindIntent = new Intent(this, MyService.class);
			/**
			 * bindService������
			 * ������
			 * 		BIND_AUTO_CREATE:��ʾ��ͷ���󶨺��Զ���������
			 */
			//�����ť�󶨷���
			bindService(bindIntent, connection, BIND_AUTO_CREATE);
			break;
		case R.id.unbind_service:
			//�����ť�������
			unbindService(connection);
			break;
		default:
			break;
		}
	}
	
}
