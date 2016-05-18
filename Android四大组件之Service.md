#Android四大组件之Service
***
##一、全局服务（unbound service ）
###概念：没有约束的服务
####特点：
	1.一旦服务开启，调用者和服务没有任何关系。
	2.哪怕调用者被destroy了，服务依旧在运行。
	3.不能调用服务内部中的方法。
###使用方法：
	1.创建服务：
			继承Service类，注册Service，重写onStartCommand方法。
	2.启动服务
		Intent intent=new Intent(this,MusicService.class);
		//设置要传输的数据（参数、指令）
		intent.putExtra(MusicService.CMD, MusicService.MUSIC_PLAY);
		//启动Service服务
		startService(intent);//此方法属于Context
	3.调用onStartCommand方法，getIntExtra处理相应业务
	4.销毁服务Service.onDestory()
		


##二、绑定服务
###概念：有约束的服务，可以被Activity控制
####特点：使用代理对象
###使用方法：
	服务的部署：
	1.新建服务MyService，继承Service类，注册Service。
	2.在MyService中新建代理对象MyBinder，继承IBinder子类Binder。
	3.在MyBinder封装MyService的业务方法，这样才能实现代理效果。
	4.重写IBinder onBind(Intent intent)方法，返回MyBinder。
	调用者的部署：
	1.通过Activity.bindService方法实现绑定服务
		Intent intent=new Intent(this, BindService.class);
		bindService(intent, conn, Context.BIND_AUTO_CREATE);
		参数：
			/*1.service： intent
			 *2.conn：ServiceConnection ：它是Activity与服务的桥梁，
			 * 通过该接口可以获取到服务的实例或者是代理对象
			 *3.flags：活动绑定后是否自动创建服务
			 */
	2.创建一个ServiceConnection，用于服务和调用者的通讯桥梁：
		a.重写onServiceDisconnected,处理服务失联时业务
		b.重写onServiceConnected，成功绑定服务后被调用：
			//声明服务的代理对象，实现了Iservice接口
			private IService myBinder;
			public void onServiceConnected(ComponentName name, IBinder service) {
			//在该方法对调用者的myBinder成员变量（即是服务的代理对象）赋值
			Log.v(TAG, "onServiceConnected");
			//获取onBind返回的代理对象
			myBinder=(IService) service;
			isBind=true;
			}
	3.使用代理调用Service相应的业务：myBinder.playMusic();
	4.销毁界面时的解绑服务unbindService：
		@Override
		protected void onDestroy() {//如果销毁界面时没有解绑conn链接系统将会抛异常。
			/*if(conn!=null){//如果连接还没有销毁的情况下
				unbindService(conn);//解绑服务
			}*/
			unbindService(conn);//解绑服务
			super.onDestroy();
		}

##三、全局服务于绑定服务的区别：
	全局服务：
	  		1.全局服务在onStartCommand方法中处理业务
	  		2.任何活动只要调用了该服务就会调用onStartCommand中的业务方法
	  		3.生命周期：
	  				a.Activity.startService()
	  				b.Service.onCreate()
	  				c.Service.onStartCommand()
	  				d.服务运行
	  				e.Service.onDestory()
	绑定服务：
	  		1.绑定服务内部有一个自定义的代理对象，该对象中定义了相应的业务方法
	  		2.只有返回一个代理对象给活动，相应的活动才能够调用代理对象的方法
	  		3.生命周期：
	  				a.Activity.bindService()
	  				b.Service.onCreate()
	  				c.Service.onBind()
	  				d.服务运行
	  				e.Service.OnUnbind()
	  				f.Service.onDestory()


##四、意图服务
	IntentService  :意图服务
	    这是一个Service的子类,该子类开辟子线程处理所有批次的用户请求,
	    当每一批次请求处理完毕,服务自动结束，假如有新的批次请求,
		则再次创建服务,开辟子线程。
	    你需要做的只是实现onHandleIntent()方法即可.依据Intent的请求指令执行相应的业务。

    特点：
      1. 编写类，继承IntentService，并且通过构造方法设置该服务的标识
      2. IntentService只能通过startService()调用，而不能通过bindService调用
      3. 必须重写onHandleIntent()方法，该方法在子线程运行
      4.当每一批次的请求队列执行完毕，服务自动销毁，
		假如有新的批次的请求，重写创建
        服务实例，重写开启一个线程执行请求
 