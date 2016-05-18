package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Message;
/**
 * ������ʼ��ɿ��
 * @author Administrator
 *
 */
public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		new Thread(new Runnable() {

			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
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
					
					connection.setDoInput(true);
					connection.setDoOutput(true);
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
					if(listener!=null){
						//�ص�onFinish()����
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener!=null){
						//�ص�onError()����
						listener.onError(e);
					}
				} finally {
					if (connection != null) {
						connection.disconnect();
					}
				}
			}
			
		}).start();
	}
}
