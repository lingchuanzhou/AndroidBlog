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
 * 异步消息处理机制：
 * 		异步消息处理主要有四个部分组成:
 * 		1.Message:在线程之间被传递的消息
 * 		2.Handler:处理者，主要是用于发送和处理消息的。
 * 				     它有两种方法：
 * 							a.发消息：sendMessage();
 * 							b.处理消息：handleMessage();
 * 		3.MessageQueue:消息列队，存放所有通过Handler发送的消息，
 * 						每个线程里面只会有一个MessageQueue
 * 		4.Looper:是MessageQueue的管家，调用它自身的loop()方法后，
 * 				   就会进入到一个无限的循环当做，然后每当发现MessageQueue中
 * 				   存在的一条消息，就会将它取出，并传递到Handler的
 * 				 handleMessage()方法中，每个线程也只有一个Looper对象。
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
			//接收从子线程中发送过来的消息，进行相应的业务处理
			switch (msg.what) {
			case UPDATE_TEXT:
				// 在这里进行UI操作
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
					//子线程中不允许进行UI操作
					/*text.setText("Nice to see you");*/
					Message message = new Message();
					message.what = UPDATE_TEXT;
					//将what发送到主线程的Handler对象进行处理
					handler.sendMessage(message);
				}
			}).start();
			break;
		default:
			break;
		}
	}
}
