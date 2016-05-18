package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class HttpURLConnectionMethod extends Activity implements OnClickListener {
	public static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				// ���������UI�������������ʾ������
				responseText.setText(response);

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʼ�����ֶ���
		setContentView(R.layout.activity_main);
		sendRequest = (Button) findViewById(R.id.send_request);
		responseText = (TextView) findViewById(R.id.response_text);
		sendRequest.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.send_request) {
			sendRequestWithHttpURLConnection();
		}
	}

	public void toHttpClient(View v){
		Intent intent = new Intent(this, HttpClientMethod.class);
		startActivity(intent);
	}
	
	private void sendRequestWithHttpURLConnection() {
		// �����̷߳�����������
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL("http://www.baidu.com");
					connection = (HttpURLConnection) url.openConnection();
					// ����POST����ķ�ʽ��
					/*connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(
							connection.getOutputStream());
					out.writeBytes("username=admin&password=123456");*/
					
					// ����GET����
					connection.setRequestMethod("GET");
					// �������ӳ�ʱ������
					connection.setConnectTimeout(8000);
					// ���ö�ȡ��ʱ������
					connection.setReadTimeout(8000);
					// ��ȡ������Ӧ���ص�����
					InputStream in = connection.getInputStream();
					// ����Ի�ȡ�������������д���
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}

					Message message = new Message();
					message.what = SHOW_RESPONSE;
					// �����������صĽ����ŵ�Message��
					message.obj = response.toString();
					// �����ݷ��ظ����߳�
					handler.sendMessage(message);
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}

		}).start();
	}
}
