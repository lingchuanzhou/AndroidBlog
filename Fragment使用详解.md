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

##
##后退栈  BackStack

* 引入：正常情况下，返加上一级页面，应该是关闭当前的页面，然后再返回到上一级页面.
* 