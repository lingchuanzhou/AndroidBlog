#Android四大组件之BroadCastReceiver
***
##一、静态注册广播接收器
###原理：
	一旦广播接收器里面的过滤器接收到了符合过滤条件的消息，
	就会调用onReceive方法，进行相应的业务处理。
###实现流程：
	1. 编写类，继承BroadcastReceiver，并且重写onReceive（）
	2. 注册广播接收器
	<receiver android:name="cn.itcast.timechangelistener.TimeChangeReceiver" >
	3. 订阅广播事件
	<intent-filter>
	<action android:name="android.intent.action.TIME_SET" />
	<action android:name="android.intent.action.TIMEZONE_CHANGED" />
	</intent-filter>

##二、动态注册广播接收器
###原理：
* 自己定义以及订阅相应的广播接收器
###实现流程：
	1.声明一个广播接收器对象receiver，继承自BroadcastReceiver。
	2.订阅广播事件,还可以自定义广播事件:
		IntentFilter filter=new IntentFilter();
		//订阅广播事件
		filter.addAction(Intent.ACTION_TIME_CHANGED);
		//系统描述的广播事件类型
		filter.addAction(Intent.ACTION_TIMEZONE_CHANGED);
		//自己定义的广播事件类型
		filter.addAction("cn.itcast.ANDROID");
	3.在onCreate里注册广播接收器：
		registerReceiver(receiver, filter);
	4.在onDestroy里设置注销广播接收器：
		unregisterReceiver(receiver);
	5.发送广播：有可能是系统发送的，也有是自己定义的广播
		public void send(View v){
		Intent intent=new Intent();
		//自定义广播
		intent.setAction("cn.itcast.ANDROID");
		sendBroadcast(intent);
		}
	6.接收广播，系统将自动调用重写的onReceive
		public void onReceive(Context context, Intent intent) {....}
##三、软件更新通知案例
	
	package cn.itcast.softupdate;
	
	import java.io.InputStream;
	
	import org.xmlpull.v1.XmlPullParser;
	
	import android.app.Notification;
	import android.app.NotificationManager;
	import android.app.PendingIntent;
	import android.content.BroadcastReceiver;
	import android.content.Context;
	import android.content.Intent;
	import android.content.pm.PackageInfo;
	import android.content.pm.PackageManager;
	import android.net.ConnectivityManager;
	import android.net.NetworkInfo;
	import android.util.Xml;
	import android.widget.RemoteViews;
	import android.widget.Toast;
	/**2016年4月8日17:30:32
	 * 当移动网络状态发生改变时，发送软件更新通知。
	 * @author Administrator
	 *
	 */
	public class SoftUpdateReciever extends BroadcastReceiver {
	
		@Override
		public void onReceive(Context context, Intent intent) {
			/**实现软件更新通知的步骤：
			 *   1. 检测网络状态 ，是否有网络连接
			 *   2. 解析服务端的version.xml 获取新版本
			 *   3. 获取本应用的当前版本
			 *   4. 假如有新版本，发通知
			 */
			try{
			int currentVersion=0;//当前版本
			int newVersion=0;//新版本
				/**************************1.在AndroidManifes中：a.注册Reciever和订阅广播，b.添加网络访问权限和网络状态改变权限*****/
				/**************************2. 检测网络状态 ，是否有网络连接**********************/
			if(isAvailable(context)){  //1.检测网络状态 ，是否有网络连接
				/**************************3.解析version.xml文件 ,该文件可以来自网络 或者是本地 或者是本应用**********************/
				InputStream is=context.getResources().openRawResource(R.raw.version);
				newVersion=parseVersion(is);
				System.out.println("解析的新版本:"+newVersion);
				/**************************4.获取本应用的当前版本**********************/
				//3.获取本应用的当前版本
				currentVersion=getCurrentVersion(context);
				System.out.println("当前的版本:"+currentVersion);
				
				if(newVersion>currentVersion){//4.假如有新版本，发通知
					
					//第一步：获取通知管理服务
					NotificationManager notificationManager=
							(NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
					//第二步：构建通知栏通知
				   /**构建通知
				    * icon:通知的图标
				    * tickerText：滚动提示信息
				    * when:发通知的时间
				    */
					Notification notification=new Notification(R.drawable.emoji_080, "新版本,就是任性", 
							System.currentTimeMillis());
					notification.defaults=Notification.DEFAULT_SOUND;//默认是声音提示
					notification.flags=Notification.FLAG_AUTO_CANCEL;//自动清除，只要点击了通知内容框，则自动清除
					
					//第五步：创建一个调用第三方应用的意图
					Intent laterIntent=new Intent();
					//调用第三方的应用 ，隐式调用   ，显示调用第三方的应用
					laterIntent.setClassName("cn.itcast.diyadapter", "cn.itcast.diyadapter.DiyAdapterActivity");
					
					//第四步：通过延迟意图器，获取第三方应用处理通知
					/**延迟意图，满足某种条件的意图，当点击了内容框，而执行的意图
					 * context：上下文
					 * requestCode:请求码   : 当前没有用到
					 * Intent ：要执行的意图
					 * flags：标记：用于更新通知条数
					 *  
					 */
					PendingIntent contentIntent=PendingIntent.getActivity(context, 0, laterIntent, PendingIntent.FLAG_UPDATE_CURRENT);
					
					//第三步：设置通知稍后的事件信息，即当下拉的通知状态栏后要设置的通知内容框
					/**setLatestEventInfo(Context, CharSequence, CharSequence, PendingIntent)：
					 * 设置通知稍后的事件信息，即当下拉的通知状态栏后要设置的通知内容框
					 * 参数：
					 * 		context：上下文
					 * 		contentTitle：内容框标题
					 * 		contentText：内容信息
					 * 		contentIntent：内容意图 ，当点击了内容框后，执行的意图
					 */
					notification.setLatestEventInfo(context, "", "新版本，更坑，更耗流量!", contentIntent);
					 //第六步：发送通知
					//发通知   id:该通知在通知管理器的序号
					notificationManager.notify(1, notification);
				}
	
			}
			}catch(Exception e){
				e.printStackTrace();
			}
	
		}
		/*******************************取得本应用当前版本：getCurrentVersion()*********************************/
		private int getCurrentVersion(Context context) throws Exception{
			//取得包管理
			PackageManager packageManager=context.getPackageManager();
			//取得当前应用的包信息
			/**getPackageInfo(String packageName, int flags):
			 * 相关参数：
			 * 		String packageName(包名)：context.getPackageName()
			 * 		int flags：一个标记，主要是通过四大组件进行标记
			 */
			PackageInfo packageInfo=packageManager.getPackageInfo
					(context.getPackageName(), PackageManager.GET_ACTIVITIES);
			return packageInfo.versionCode;
		}
		
		/*******************************通过pull解析 xml文件流：parseVersion()*********************************/
	   private int parseVersion(InputStream is)  throws Exception{
			XmlPullParser parser=Xml.newPullParser();
			parser.setInput(is, "UTF-8");
			int eventCode=parser.getEventType();
			while(eventCode!=XmlPullParser.END_DOCUMENT){
				switch (eventCode) {
				case XmlPullParser.START_TAG:
					if(parser.getName().equals("versionCode")){
						return Integer.parseInt(parser.nextText());
					}
					
					break;
	
				default:
					break;
				}
				eventCode=parser.next();//指向下一个节点
			}
			return 0;
		}
	   
	   /**************************检测网络是否可用方法 ：isAvailable()**********************/
		private boolean isAvailable(Context context) {
			//取得连接管理服务
			ConnectivityManager connectivityManager=
					(ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
			//取得网络信息
			NetworkInfo networkInfo=connectivityManager.getActiveNetworkInfo();
			if(networkInfo!=null&&networkInfo.isAvailable()){
				return true;
			}
			return false;
		}
	
	}