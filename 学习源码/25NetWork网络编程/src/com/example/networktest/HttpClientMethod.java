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
				// ���������UI�������������ʾ������
				responseText.setText(response);

			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// ��ʼ�����ֶ���
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
					// ͨ��ʵ��������DefaultHttpClient��ȡHttpClientʵ����
					HttpClient httpClient = new DefaultHttpClient();

					// GET����ʽ��
					// ͨ��HttpGet�ύGET����
					/*
					 * HttpGet httpGet = new HttpGet( "http://wwww.baidu.com");
					 */

					/*
					 * // ����Ϊ���ʵķ�������ַ�ǵ��Ա���get_data.xml HttpGet httpGet = new
					 * HttpGet( "http://10.0.2.2/get_data.xml");
					 */

					// ����Ϊ���ʵķ�������ַ�ǵ��Ա���get_data.xml
					HttpGet httpGet = new HttpGet(
							"http://10.0.2.2/get_data.json");
					// ִ���ύ
					HttpResponse httpResponse = httpClient.execute(httpGet);

					// POST����ʽ��
					/*
					 * HttpPost httpPost = new HttpPost("http://www.baidu.com");
					 * //ͨ��NameValuePair��������Ŵ��ύ������ List<NameValuePair> params =
					 * new ArrayList<NameValuePair>(); //��Ӵ��ύ������ params.add(new
					 * BasicNameValuePair("username","admin")); params.add(new
					 * BasicNameValuePair("password","123456")); //���ñ��뷽ʽ
					 * UrlEncodedFormEntity entity = new
					 * UrlEncodedFormEntity(params,"utf-8");
					 * httpPost.setEntity(entity); //ִ���ύ HttpResponse
					 * httpResponse = httpClient.execute(httpPost);
					 */
					if (httpResponse.getStatusLine().getStatusCode() == 200) {// �����Ӧ����200�����������Ӧ���ɹ���
						// ��ȡHttpEntity��������ݣ�
						HttpEntity httpEntity = httpResponse.getEntity();
						String response = EntityUtils.toString(httpEntity,
								"utf-8");
						
						/*
						 * Message msg = new Message(); 
						 * msg.what =SHOW_RESPONSE; 
						 * //�����������صĽ����ŵ�Message�� 
						 * msg.obj = response.toString(); handler.sendMessage(msg);
						 */
						
						/*
						 * //ʹ��Pull����XML 
						 * parseXMLWithPull(response);
						 */

						/*
						 * // ʹ��SAX����XML 
						 * parseXMLWithSAX(response);
						 */

						/*
						 * //ʹ��JSONObject����json
						 * parseJSONWithJSONObject(response);
						 */

						// ʹ��GSON����json
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
		//��jsonData�е�������ӵ�App�������档
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
			// �ѱ�д�õ�SaxParseXMLʵ�����õ�XMLReader��
			xmlReader.setContentHandler(saxParseXML);
			// ��ʼִ�н���
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
				// ��ȡ��ǰ�ڵ������
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
				// ��ʼ����ĳ���ڵ�
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
				// ��ɽ���ĳ���ڵ�
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
