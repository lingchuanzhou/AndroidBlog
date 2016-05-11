#Fragment使用详解
***
##概念：
* 在android .3.0以后，为了适配大屏幕的设备(平板 TV) 安卓提供的一个代表视图片段的对象，
	包含视图，可以做为Activity的一部添加到Activity中。
##如何绑定Fragment源代码？
* 需要在lib目录下创建 android-support-v4.jar.properites
* 配置源代码关联路径
	src=E:\\sdk_all\\sdk\\extras\\android\\support\\v4\\src
* 关闭工程，重新启动
##Fragment与Activity的比较：
		Activity									Fragment
	继承Activity 布局Layout					    继承Fragment布局Layout
	重写onCreate								 重写onCreateView
	使用<activity标签 配置					   通过 <fragment添加在布局上
	通过Intent打开								通过片段管理器打开

##静态添加Fragment步骤：

 		A：准备三个继承Fragment的子类：OneFragment、TwoFragment、ThreeFragment
 		B：准备三个相应的布局文件
 		C：在Fragment中重写onCreateView方法，将布局文件填充到container中
			return inflater.inflate(R.layout.fragment_one_layout, container, false);
			如果为true则返回container；false则返回fragment_one_layout的根节点对象。
 		D：在activity_main中添加三个<fragment/>标签，
 			并使用android:name="cn.itcast.myfragment.OneFragment",
 			引用三个Fragment子类。
 		E：运行activity。
##动态添加Fragment步骤：
 		A：准备三个继承Fragment的子类：OneFragment、TwoFragment、ThreeFragment
 		B：将MyActivity继承FragmentActivity准备好activity_main.xml，
		其中要添加一个Framelayout布局。
 		C1：添加片段操作：
 					a：通过getSupportFragmentManager取得碎片管理器
 					b：开启片段事务FragmentTransaction
 					c：实例化OneFragment
 					d：调用片段管理器的add(OneFragment)方法
 					e：commit()提交事务
 		C2：替换片段操作：
 					a：通过getSupportFragmentManager取得碎片管理器
 					b：开启片段事务FragmentTransaction
					c：实例化TwoFragment
 					d：调用片段管理器的replace(OneFragment)方法
 					e：commit()提交事务
 		C3：移除片段操作：
 					a：通过getSupportFragmentManager取得碎片管理器
 					b：开启片段事务FragmentTransaction
 					c：通过Fragment=findFragmentById(R.id.content)找到要移除的片段
 					d：调用片段管理器的remove(Fragment)方法
 					e：commit()提交事务
 
  		注意：MainActivity可以继承Activity，但为了支持低版本，则继承FragmentActivity
##Activity与Fragment的通信 
* Fragment通过getActivity()取得该片段的宿主Activity，而Activity要获取Fragment对象
可以通过FragmentManager.findFragmentById()或者FragmentManager.findFragmentByTag(tag)来获取Fragment实例

##后退栈  BackStack

* 正常情况下，从Fragment返加到上一级页面，应该是关闭当前的页面，然后再返回到上一级页面，而真实情况是，点击返回后不会返回到上级页面，而是直接退出了。
* 概念：可以把一系列对Fragment的操作都添加到返回栈中，如果没有返回栈，那么Fragment就相当于Activity的一个附属，点击返回按钮，直接上是在activity的基础上返回的，这样Activity连同Fragment就会被销毁，而有了返回栈，Fragment就被保存到了一个栈中，不会把Activity销毁掉。
* 后退栈还有一个作用：用于碎片的详情页返回。
	*	getFragmentManager().popBackStack();//返回到上个页面
##Fragment传参:
###Fragment参数传递方法一：

	在Activity中定义一个字段、然后添加set和get方法、代码如
	下、mTitle就是要传递的参数、如果是传递对象、可以把mTitle
	换成一个对象即可
	public class DemoActivity {
	
		private String mTitle;
		
		public String getmTitle() {
			return mTitle;
		}
	
		public void setmTitle(String title) {
			this.mTitle = title;
		}
	
	}
	Fragment调用方法、需要注意的是在设值的时候要进行强转一下
	((DemoActivity)getActivity()).getmTitle();

###Fragment参数传递方法二：
	在Fragment1中设置数据：
		Fragment2 fragment2 = new Fragment1();
		Bundle args = new Bundle();
		args.putString("title","标题1");
		fragment1.setArguments(args);
	在Fragment2中获取数据：
		Bundle args = getArguments();
	  
##Fragment生命周期
* onAttach() ：将fragment关联到activity时,可用于值传递
* onCreate() ：系统创建fragment的时，
* onCreateView()：填充fragment的布局文件时
* onActivityCreated()：当Activity中的onCreate方法执行完后调用		
* onStart()：和activity同时启动,此时Fragement可见;
* onResume()：和activity一致  在activity中运行是可见的
* onPause()：和activity一致，部分界面可见
* onStop()： 和activity一致，fragment不可见的。
* onDestroyView()：Fragment中的布局被移除时调用。
* onDestroy()：销毁fragment对象，跟activity类似
* onDetach()：Fragment和Activity解除关联的时候调用


##Fragment与Activity的生命周期对比：

	开始启动：
	05-07 05:55:08.553: I/Log(1990): oncreate
	05-07 05:55:08.553: I/Log(1990): onAttach_Fragment
	05-07 05:55:08.553: I/Log(1990): onCreate_Fragment
	05-07 05:55:08.553: I/Log(1990): onCreateView_Fragment
	05-07 05:55:08.553: I/Log(1990): onActivityCreated_Fragment
	05-07 05:55:08.553: I/Log(1990): onStart
	05-07 05:55:08.553: I/Log(1990): onStart_Fragment
	05-07 05:55:08.553: I/Log(1990): onResume
	05-07 05:55:08.553: I/Log(1990): onResume_Fragment
	
	按下home按键
	05-07 05:55:28.725: I/Log(1990): onPause_Fragment
	05-07 05:55:28.725: I/Log(1990): onPause
	05-07 05:55:29.221: I/Log(1990): onStop_Fragment
	05-07 05:55:29.221: I/Log(1990): onStop

	再回到界面
	05-07 05:55:49.441: I/Log(1990): onRestart
	05-07 05:55:49.441: I/Log(1990): onStart
	05-07 05:55:49.441: I/Log(1990): onStart_Fragment
	05-07 05:55:49.441: I/Log(1990): onResume
	05-07 05:55:49.441: I/Log(1990): onResume_Fragment

	销毁activity
	05-07 05:59:02.293: I/Log(1990): onPause_Fragment
	05-07 05:59:02.293: I/Log(1990): onPause
	05-07 05:59:02.757: I/Log(1990): onStop_Fragment
	05-07 05:59:02.757: I/Log(1990): onStop
	05-07 05:59:02.757: I/Log(1990): onDestroyView_Fragment
	05-07 05:59:02.757: I/Log(1990): onDestroy_Fragment
	05-07 05:59:02.757: I/Log(1990): onDetach_Fragment
	05-07 05:59:02.757: I/Log(1990): onDestroy

	可以看出 当现实fragment的时候都先执行activity方法，当销毁的
	时候都是现执行 fragment的方法，这样更好理解fragment是嵌套在
	activity中 

##Phone与Pad如何兼容？
###开发思想：
* 依据两个相同的xml文件中的不同布局来判断到底使用哪一个xml
###开发步骤：
	a：新建一个layout_large文件夹，将activity_main.xml拷贝
	进去。
	b：然后采用静态配置Fragment的方式，将另外的三个
	Fragment1、Fragment2、Fragment3重新配置一遍
	c：这时会有两个相同名称的activity_main.xml，这时需要依据xml文件中是否存在Fragment1、Fragment2、Fragment3
	这三个片段来是否存在来判断到底启用哪一个activity_main.xml，以及运行在平板上还是手机上。
##Activity升级成Fragment的开发
	1.重新继承FragmentActivity
	2.onCreate改为onCreateView
	3.setContentView改为inflate
	4.findViewById使用View.findViewById
	5.无法使用onClick标记对控件进行监听，需要
	View.findViewById，然后再setOnclickListener()
	5.传值的修改：
			a：Intent传值改为使用Bundle传值
			b：或者使用getActivity()，代码如下：
			Intent i = new Intent();
				i.setClass(getActivity().getApplicationContext(), 
				NewActivity.class);
				startActivity(i);
				getActivity().finish();

##Fragment的一些bug
	1.getActivity()为空
		原因：当前的Fragment显示在FragmentActivity，导致原
		Fragment与FragmentActivity失联
		解决方式：进入页面时直接保存，context = getActivity();
	2.Fragment有状态丢失的特点
		原因：当Fragment显示时会调用 onCreateView方法,
		每次都创建新布局
		解决方案：做一个非判断
				if(view ==null)
				{
				}
	3.java.lang.IllegalStateException: The specified child 
	already has a parent. You must call removeView() on the 
	child's parent first.
		原因：一个View被两次添加进定个布局，就会报出这个异常
		解决方案：解除当前Fragment的视图与指定布局间的引用关系，
		ViewGroup parent = (ViewGroup) view.getParent();
		parent.removeView(view);

###最终解决方案：
	让所有的Fragment继承一个自定义好的BaseFragment，  
	详见F:\Andriod开发\安卓开发\安卓学习总结\Fragment\FixFragmentBug.java

##ViewPager集成Fragment
###概念：可以看成界面的集合，但是以左右滑动的方式完成翻页，以Adapter作为内容
* PagerAdapter
   |--FragmentPagerAdapter
###开发步骤：
	a：创建底部的导航条
	b：添加点击事件，完成高亮显示
	c：布局一个ViewPager
	d：创建FragmentPagerAdapter
	e：设置适配器

