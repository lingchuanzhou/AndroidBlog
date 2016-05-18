package com.example.threadtest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * �첽��Ϣ������ƣ�
 * 		�첽��Ϣ������Ҫ���ĸ��������:
 * 		1.Message:���߳�֮�䱻���ݵ���Ϣ
 * 		2.Handler:�����ߣ���Ҫ�����ڷ��ͺʹ�����Ϣ�ġ�
 * 				     �������ַ�����
 * 							a.����Ϣ��sendMessage();
 * 							b.������Ϣ��handleMessage();
 * 		3.MessageQueue:��Ϣ�жӣ��������ͨ��Handler���͵���Ϣ��
 * 						ÿ���߳�����ֻ����һ��MessageQueue
 * 		4.Looper:��MessageQueue�Ĺܼң������������loop()������
 * 				   �ͻ���뵽һ�����޵�ѭ��������Ȼ��ÿ������MessageQueue��
 * 				   ���ڵ�һ����Ϣ���ͻὫ��ȡ���������ݵ�Handler��
 * 				 handleMessage()�����У�ÿ���߳�Ҳֻ��һ��Looper����
 * 				
 * 					
 *
 */
public class MainActivity extends Activity implements OnClickListener {
	public static final int UPDATE_TEXT = 1;
	private TextView text;
	private Button changeText;
	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			//���մ����߳��з��͹�������Ϣ��������Ӧ��ҵ����
			switch (msg.what) {
			case UPDATE_TEXT:
				// ���������UI����
				text.setText("Nice to meet you");
				break;
			default:
				break;
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		text = (TextView) findViewById(R.id.text);
		changeText = (Button) findViewById(R.id.change_text);
		changeText.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_text:
			new Thread(new Runnable() {
				public void run() {
					//���߳��в��������UI����
					/*text.setText("Nice to see you");*/
					Message message = new Message();
					message.what = UPDATE_TEXT;
					//��what���͵����̵߳�Handler������д���
					handler.sendMessage(message);
				}
			}).start();
			break;
		default:
			break;
		}
	}
}
