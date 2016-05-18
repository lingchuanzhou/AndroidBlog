package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import android.os.Message;
/**
 * 网络访问集成框架
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
					// 发送POST请求的方式：
					/*connection.setRequestMethod("POST");
					DataOutputStream out = new DataOutputStream(
							connection.getOutputStream());
					out.writeBytes("username=admin&password=123456");*/
					
					// 发送GET请求
					connection.setRequestMethod("GET");
					// 设置连接超时毫秒数
					connection.setConnectTimeout(8000);
					// 设置读取超时毫秒数
					connection.setReadTimeout(8000);
					
					connection.setDoInput(true);
					connection.setDoOutput(true);
					// 获取网络相应返回的流。
					InputStream in = connection.getInputStream();
					// 下面对获取到的输入流进行处理
					BufferedReader reader = new BufferedReader(
							new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while ((line = reader.readLine()) != null) {
						response.append(line);
					}
					if(listener!=null){
						//回调onFinish()方法
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					if(listener!=null){
						//回调onError()方法
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
