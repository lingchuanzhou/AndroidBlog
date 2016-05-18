package com.example.networktest;

import java.io.StringReader;
import java.util.List;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.util.Xml;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class HttpClientMethod extends Activity implements OnClickListener {
	public static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String) msg.obj;
				// 在这里进行UI操作，将结果显示到界面
				responseText.setText(response);

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// 初始化布局对象
		setContentView(R.layout.activity_http_client_method);
		sendRequest = (Button) findViewById(R.id.HttpClient_send_request);
		responseText = (TextView) findViewById(R.id.HttpClient_response_text);
		sendRequest.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.HttpClient_send_request) {
			sendRequestWithHttpClient();
		}
	}

	private void sendRequestWithHttpClient() {
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// 通过实例化子类DefaultHttpClient获取HttpClient实例。
					HttpClient httpClient = new DefaultHttpClient();

					// GET请求方式：
					// 通过HttpGet提交GET请求
					/*
					 * HttpGet httpGet = new HttpGet( "http://wwww.baidu.com");
					 */

					/*
					 * // 更换为访问的服务器地址是电脑本机get_data.xml HttpGet httpGet = new
					 * HttpGet( "http://10.0.2.2/get_data.xml");
					 */

					// 更换为访问的服务器地址是电脑本机get_data.xml
					HttpGet httpGet = new HttpGet(
							"http://10.0.2.2/get_data.json");
					// 执行提交
					HttpResponse httpResponse = httpClient.execute(httpGet);

					// POST请求方式：
					/*
					 * HttpPost httpPost = new HttpPost("http://www.baidu.com");
					 * //通过NameValuePair集合来存放待提交的数据 List<NameValuePair> params =
					 * new ArrayList<NameValuePair>(); //添加待提交的数据 params.add(new
					 * BasicNameValuePair("username","admin")); params.add(new
					 * BasicNameValuePair("password","123456")); //设置编码方式
					 * UrlEncodedFormEntity entity = new
					 * UrlEncodedFormEntity(params,"utf-8");
					 * httpPost.setEntity(entity); //执行提交 HttpResponse
					 * httpResponse = httpClient.execute(httpPost);
					 */
					if (httpResponse.getStatusLine().getStatusCode() == 200) {// 如果响应码是200，则请求和响应都成功了
						// 获取HttpEntity里面的数据：
						HttpEntity httpEntity = httpResponse.getEntity();
						String response = EntityUtils.toString(httpEntity,
								"utf-8");
						
						/*
						 * Message msg = new Message(); 
						 * msg.what =SHOW_RESPONSE; 
						 * //将服务器返回的结果存放到Message中 
						 * msg.obj = response.toString(); handler.sendMessage(msg);
						 */
						
						/*
						 * //使用Pull解析XML 
						 * parseXMLWithPull(response);
						 */

						/*
						 * // 使用SAX解析XML 
						 * parseXMLWithSAX(response);
						 */

						/*
						 * //使用JSONObject解析json
						 * parseJSONWithJSONObject(response);
						 */

						// 使用GSON解析json
						parseJSONWithGSON(response);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

			}

		}).start();
	}

	private void parseJSONWithGSON(String jsonData) {
		Gson gson = new Gson();
		//将jsonData中的数据添加到App对象里面。
		List<App> appList = gson.fromJson(jsonData, new TypeToken<List<App>>(){}.getType());
		for(App app: appList) {
			Log.d("MainActivity", "id is " + app.getId());
			Log.d("MainActivity", "name is " + app.getName());
			Log.d("MainActivity", "version is " + app.getVersion());
		}
	}

	private void parseJSONWithJSONObject(String jsonData) {
		try {
			JSONArray jsonArray = new JSONArray(jsonData);
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = new JSONObject();
				String id = jsonObject.getString("id");
				String name = jsonObject.getString("name");
				String version = jsonObject.getString("version");
				Log.d("MainActivity", "id is " + id);
				Log.d("MainActivity", "name is " + name);
				Log.d("MainActivity", "version is " + version);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	private void parseXMLWithSAX(String xmlData) {
		try {
			SAXParserFactory factory = SAXParserFactory.newInstance();
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			SaxParseXML saxParseXML = new SaxParseXML();
			// 把编写好的SaxParseXML实例设置到XMLReader中
			xmlReader.setContentHandler(saxParseXML);
			// 开始执行解析
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void parseXMLWithPull(String xmlData) {
		// TODO Auto-generated method stub
		try {
			XmlPullParser xmlPullParser = Xml.newPullParser();
			xmlPullParser.setInput(new StringReader(xmlData));
			int eventType = xmlPullParser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while (eventType != XmlPullParser.END_DOCUMENT) {
				// 获取当前节点的名称
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
				// 开始解析某个节点
				case XmlPullParser.START_TAG: {
					if ("id".equals(nodeName)) {
						id = xmlPullParser.nextText();
					} else if ("name".equals(nodeName)) {
						name = xmlPullParser.nextText();
					} else if ("version".equals(nodeName)) {
						version = xmlPullParser.nextText();
					}
					break;
				}
				// 完成解析某个节点
				case XmlPullParser.END_TAG: {
					if ("app".equals(nodeName)) {
						Log.d("MainActivity", "id is " + id);
						Log.d("MainActivity", "name is " + name);
						Log.d("MainActivity", "version is " + version);
					}
					break;
				}
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

}
