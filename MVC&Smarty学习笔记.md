#视图引擎Smarty学习笔记
***
##MVC的学习
###MVC的优势：
* 视图层：网页设计人员
* 模型层：业务逻辑熟悉人员
* 控制器：其他开发人员
###各层的介绍
* 视图：我们能看见的web界面
* 控制器：好比遥控器，向系统发出指令的工具和帮手
* 模型：按要求从数据取出数据
###MVC工作流程
* 第一步 浏览者 -> 调用控制器，对它发出指令
* 第二步 控制器 -> 按指令选取一个合适的模型
* 第三步 模型 -> 按控制器指令取相应数据
* 第四步 控制器 -> 按指令选取相应的视图
* 第五步 视图 -> 把第三步取到的数据按用户想要的样子显示出来
###开发环境搭建
EmEditor编辑器、WAMPServer。
###实例
####1.开发第一个控制器：
	命名规范：
		testcontroller.class.php
		  ↓     ↓        ↓
		 名字 控制器文件  类文件
	创建testcontroller.class.php
	<?php
		class testController{//与文件名对应
			function show() {//展示界面
				//第二步 控制器 -> 按指令选取一个合适的模型
				$testModel = new testModel();
				//第三步 模型 -> 按控制器指令取相应数据
				$data = $testModel->get();
				//第四步 控制器 -> 按指令选取相应的视图
				$testView = new $testView();
				//第五步 视图 -> 把第三步取到的数据按用户想要的样子显示出来
				$testView->display($data);
			}
		}
	?>
####2.开发第一个模型层：
	命名规范：
	testModel.class.php
	 ↓      ↓    ↓
 	文件名称 模型文件  类文件	
	创建testModel.class.php
	<?php
		class testModel{//与文件名对应
			function get() {//获取数据
				return "hello world";
			}
		}
	?>
####3.开发第一个视图层：
	命名规范：
	testView.class.php
	<?php
		class testView{//与文件名对应
			function display($data) {//显示数据
				echo $data;
			}
		}
	?>

#### 4.综合演示运行流程：

	新建test.php文件
	<?php
	//引入文件：
	require_once('testcontroller.class.php');
	require_once('testModel.class.php');
	require_once('testView.class.php');
	//第一步：浏览者 -> 调用控制器，对它发出指令
	//创建控制器
	$testController = new testController();
	$testController->show();//接下来返回到show方法中看怎么继续执行。
	?>
#### 5.文件目录结构
	mvc
	 |--libs(类库目录)
		 |--Controller(控制器目录)
				|testController.class.php
		 |--Model(模型目录)
			  |testModel.class.php
		 |--ORG(第三方类库)
			 |--plugins
			 |--sysplugins
			 |--debug.tpl
			 |--Smarty.class.php
			 |--SmartyBC.class.php
		 |--View(视图目录)
			 |--testView.class.php
	 |--config.php(配置文件)
	 |--index.php(入口文件)
	 |--function.php
#### 6.入口程序
* 在网上经常被成为单一入口机制，一个网站的所有页面都是index.php?xxx这样的形式。
##### 6.1.建立MVC各层调用函数M、V、C

	创建函数文件function.php
	<?php
	//1.建立控制器调用函数：
		创建C($name,$method)方法：
		$name:控制器名称，$method：控制器方法
	function C($name,$method){
		//引入控制器文件
		require_once('/libs/Controller/'.$name.'Controller.class.php');
		//实例化控制器
		//$testContreller = new testContreller();
		//$testContreller->show();
		//eval函数用于将字符串转换为可执行的php语句
		eval('$obj = new'.$name.'Contreller();$obj->'.$method.'():');
		/****
		eval()函数调用简单但是不安全
		可替换为下面代码：
		$controller = $name.'controller';
		$obj = new $controller();
		$obj ->$method(); 
			*****/			
	}

	//2.继续建立一个模型调用函数
	function M($name){
		require_once('/libs/Model/'.$name.'Model.class.php');
		//eval('$obj = new'.$name.'Model();');
		$model = $name."Model";
		$obj = new $Model();
		return $obj;
	}

	//3.建立一个视图调用函数
	function V($name){
		require_once('/libs/View/'.$name.'View.class.php');
		eval('obj = new '.$name.'View();');
		//eval方便但是不安全，可以用以下代替：
		$view = $name.'View';
		$obj = new $view();
	}
	//4.添加一个对信息安全保障的过滤函数
	function daddslashes($str){
		//!get_magic_quotes_gpc():判断过滤配置是否打开，若没有打开则调用addslashes函数转义str，否则系统会自动过滤str
		return (!get_magic_quotes_gpc())?addslashes($str):$str;
	?>
##### 6.1.2入口文件改造与功能总结
	1.统一入口文件为首的Url格式
	例如index.php?controller=控制器&mothod=方法名
	1.1打开index.php,并清空里面的所有内容
	<?php
	//url形式：index.php?controller=控制器&method=方法名

	//定义允许访问的控制名和方法名
	$controllerAllow=array('test','index');
	$methodAllow=array('test','index',show);
	

	//获取参数并过滤,并判断参数是否包含在允许访问的控制名或方法里
	$controller = in_array($_GET['controller'],$controllerAllow)?daddslashes($_GET['controller']):'index';

	$method = in_array($_GET['method'],$methodAllow)?daddslashes($_GET['method']):'index';

	//调用C函数
	require_once('function.php');
	C($controller,$method);
	
	?>

	1.2修改testcontroller.class.php
	<?php
		class testController{//与文件名对应
			function show() {//展示界面
				//第二步 控制器 -> 按指令选取一个合适的模型
				//注释掉testModel的实例化，因为在M函数中已实例化
				//$testModel = new testModel();
				$testModel = M('test');
				//第三步 模型 -> 按控制器指令取相应数据
				$data = $testModel->get();
				//第四步 控制器 -> 按指令选取相应的视图
				//注释掉testView的实例化，因为在V函数中已实例化
				//$testView = new $testView();
				$testView = V('test');
				//第五步 视图 -> 把第三步取到的数据按用户想要的样子显示出来
				$testView->display($data);
			}
		}
	?>
	1.3运行实例
		在浏览器窗口输入：http://mvc.test/index.php?controller=test&method=show
	2.在入口文件里使用安全的调用函数方法，主要由以下步骤：
	（1）利用超全局变量$_GET[]来接收url里的参数。
	（2）get_magic_quotes_gpc进行魔法函数判断，如果为true,说明已经含	有转义功能，如果没有，则需要使用转义函数addslashes($str)将字符串转义
	（3）设置控制器白名单和方法白名单。
	 (4)使用in_array函数判断参数是否在白名单里
##Smarty的使用和配置
	1.将压缩包smarty-3.1.29，解压。
	2.将包中的libs文件夹复制到名为test的web应用的根目录下。
	3.在test中新建四个文件夹：templates、templates_c、configs、cache
	4.在test文件夹下新建名为index.php的文件：
		<?php
		//引入smarty核心类文件
		require_once("./libs/Smarty.class.php");
		//实例化smarty对象
		$smarty = new Smarty();
		//设置模板的标签标识
		$smarty->left_delimiter = "<{";
		$smarty->right_delimiter = "}>";
		
		//设置有关于smarty四个文件夹的有关配置
		//html模板的地址
		$smarty->template_dir = 'F:/PHP/test01/templates';
		//配置目录
		$smarty->config_dir = 'F:/PHP/test01/config';
		//缓存
		$smarty->cache_dir = 'F:/PHP/test01/cache';
		//模板编译生成的文件
		$smarty->compile_dir = 'F:/PHP/test01/templates_c';
		
		//（1）开启缓存 
		$smarty->caching = true;
		//（2）缓存时间 
		$smarty->cache_lifetime = 120;	
		//设置变量和值
		$smarty->assign('helloworld',10000);
		//引用模板文件
		$smarty->display('index.tpl');
	5.前往templates文件夹下，新建index.tpl模板文件：
		<{$helloworld}>
	6.在网页中输入test.com，发现输出10000。

##Smarty语法：
###一、变量调节器：
	1.首字母大写：capitalize
	示例：{$articleTitle|capitalize}
	2.字符串连接 cat
	示例：{$articleTitle|cat:"yesterday."}
	3.日期格式化：data_format
    示例：
		如：$smarty->assign('time',time());
		转为：{$yesterday|date_format}
		或：{$yesterday|date_format：":"%A,%B,%E...}
	4.为未赋值或为空的变量指定默认值default
	示例：{$articleTitle|default:"no title"}
	5.转码：escape
		用于html转码，url转码，在没有转码的变量上转换单引号，
		为十六进制转码，十六进制美化，或者javascript转码，
		默认是html转码。
	示例：
		$smarty->assign('url','http://www.imooc.com/course/video/?mid=680')
		{$url|escape:"url"}
	6.小写lower大写upper
		将变量字符串小大写
		示例：{￥articleTitle|lower} {$articleTitle|upper}
	7.所有的换行符将被替换成<br/>,nl2br功能同php中的nl2br()函数一样。
	示例：{$articleTitle|nl2br}
###二、条件判断
	（1）基本句式
		{if $name eq "Fred"}
		Welcome Sir.
		{elseif $name eq "Wilma"}
		Welcome Ma'am.
		{else}
		Welcome,whatever you are.
		{/if}
		（2）条件修饰符有很多，请记得简单的几个:
				eq（==） neq（！=）gt（>）lt（<）
		（3）修饰词时必须和变量或常量用空格格开
###三、Samrty循环
	例：
		有这样一个二维数组：
			$articlelist=array(
					array(
							"title"=>"第一篇文章",
							"author"=>"小王",
							"content"=>"第一篇文章该写点啥呢"
						),
					array(
							"title"=>"第二篇文章",
							"author"=>"小李",
							"content"=>"第二篇文章该写点啥呢"
						)
					);
####1.使用section循环
	（1）section、sectionelse
	功能多，参数多。或许不是太实用。是smarty用来做循环操作的函数之一。
	（2）了解基本属性name和loop
	a:循环格式：
		{section name=[循环变量] loop=[需要遍历的变量] }
		{/section}
	b:循环示例：
		{section name=article loop=$articlelist }
			{$articlelist[article].title}
			{$articlelist[article].author}
			{$articlelist[article].content}
		{/section}
	
	section其他的属性：
	（1）start 循环执行的初始位置。如果该值为负数，开始位置从数组的尾部算起。例如：
	 如果数组中有7个元素，指定start为-2，那么指向当前数组的索引为5.
	 非法值（超过了循环数组的下限）将被自动调整为最接近的合法值。
	（2）step 该值决定循环的步长。例如指定step=2将只遍历下标为0、2、4等的元素。
	 如果step为负值，那么遍历数组的时候从后向前遍历。
	（3）max 设定循环最大执行次数。
	（4）show 决定是否显示该循环。
####2.使用foreach循环
	a:循环格式：
		{foreach item=[循环变量] from=[遍历的变量]}
		{foreachelse}[没有数据遍历时候处理的程序]
		{/foreach}
	
	b:循环示例：
		{foreach $articlelist as $article}
			{$article.title}
			{$article.author}
			{$article.content}
			<br/>
		{foreachelse}
			{*当数据为空时执行*}
			当前没有文章
		{/foreach}
		也可以将第一句改为：
		{foreach item=article from =$articlelist}

###三、smarty模板的引用
	（1）include用法和php里的include差不多<br><br>
	（2）smarty的include还具备自定义属性的功能
	例如：
		{include file="header.tpl" title="网站标题" table_bgcolor="c0c0c0"}
		{include file="header.tpl" sitename="慕课网"}
		{*将sitename这个变量传到header.tpl文件中去*}
		sitename:只能传递参数给待包含模板

###四、Smarty类与对象的赋值与使用：
	使用assign把一个类的对象以变量形式赋值到smarty模板里使用
	1.在test.php文件中有这么一个类：
		class My_Object {
				function meth1($params) {
					return $params[0].'已经'.$params[1]
				}
			}
		//实例化类：
		$myobj = new My_Object();
		//用assign引用该类到test.tpl文件中：
		$smarty->assign('myobj',$myobj);
		$smarty->display('test.tpl');
	2.在test.tpl使用该类：
		{$myobj—>meth1(array('苹果','熟了'))}
	3.运行test.php文件，打印出：苹果已经熟了
###五、在smarty模板中使用php内置函数和自定义函数
* 使用场景：当我们觉得smarty中的变量调节器不够用的时候
####1.可以使用php内置函数：
* 运用格式：{变量|php内置函数：参数2：参数3...}
#####案例1：
	//1.将当前时间赋值到一个time变量里面
	$smarty->assign('time',time());
	//2.启用test.tpl模板
	$smarty->display('test.tpl');
	在test.tpl文件中编辑：
	//注意：在php中date函数的两个参数顺序是：日期格式、时间戳。
	{"Y-m-d"|date:$time}
	注意：当要使用php内置函数时，|前是第一个参数，：后是第二、第三..个参数

#####案例2：
	//test.php：
	$smarty->assign('str','abcdefg');
	$smarty->display('test.tpl');
	//test.tpl:
	//原php的str_replace用法：str_replace('d','h',$str);	
	{'d'|str_replace:'h',$str}

####2.可以自定义函数，并用registerPlugin注册到smarty模板里使用。
	registerPlungin('函数类型'，'在smarty模板中函数名','注册到smarty中的函数名');
	函数类型：function/modifier/block
	//test.php：
	function_test($params){
		print_r($params);
		$p1 = $params['p1'];
		$p2 = $params['p2'];
		return '传入的参数1值为'.$p1.',传入的参数2值为'.$p2;
	}

	$smarty->registerPlugin('functoin','f_test','test');
	$smarty->display('test.tpl');

	//test.tpl:参数p1，p2被打包成了params数组，传入方法中。
	{f_test p1='abc' p2='edf' c='haha' name='xiaoming};

	//打印结果：
	Array([p1]=>abc [p2]=>edf [c]=>haha [name]=>xiaoming)
	传入的参数1的值为abc，传入的参数2的值为edf
####3.Smarty函数插件
* 存在疑问：在讲上述的1和2的时候，我们发现在最终模板里调用函数的时候格式完全不一样，为了解决这个疑问，我们开始学习新的知识，函数插件
#####3.1什么是Smarty的插件：
* Smarty的插件本质上是function函数
#####3.2Smarty插件常用类型
* functions 函数插件
* modifiers 修饰插件,就是变量调节器
* block functions 区块插件
* 可以打开lib目录下的plungins目录，里面有很多定义好的函数，它们都是以function、block、modifiers开头
#####3.3如何来制作，使用插件
	(1)使用registerPlugin方法注册写好的自定义函数
	(2)将写好的插件放入Smarty解压目录中的lib目录下的plungins中
	(3)php的内置函数，可以自动以修饰插件(变量调节器插件)的
	形式在模板里使用,与变量调节器的用法类似
######3.3.1自定义function函数
	1.打开lib目录下的plungins目录，新建function.test1.php文件
	2.编辑该文件：
		<?php
			//函数命名规则：smarty_函数类型_函数名(参数数组)
			function smarty_function_test1($params){
				//$params是一个数组：
				//array('参数1'=>'参数值','参数2'=>'参数值',....)
				//$参数1 = $params['参数1'];
				//$参数2 = $params['参数2'];
				$width = $params['width'];
				$height = $params['height'];
				$area = $width*$height;
				//返回长方形的面积
				return $area;
			}
		?>
	3.test1.php
		//载入模板
		$smarty->display('area.tpl');
	4.在templates中新建area.tpl文档
		//调用test函数
		{test1 width=150,height=200}
		//打印结果：30000
######3.3.2自定义modifier函数
	1.打开lib目录下的plungins目录，新建modifier.test2.php文件
		<?php
			smarty_函数类型_函数名(参数1,参数2,..)
			function smarty_modefier_test2($utime,$format){
				//返回格式化的日期
				return date($format,$utime);
			}
		?>
	2.test2.php
		//载入模板
		$smarty->assign('time',time());
		$smarty->display('datetime.tpl');
	3.在templates中新建datetime.tpl文档
		//使用格式：{参数1|函数名:参数2:参数3...}
		{$time|test2:'Y-m-d H:i:s'}

######3.3.2自定义block函数
	1.打开lib目录下的plungins目录，新建block.test3.php文件
		<?php
			//函数命名规则：smarty_函数类型_函数名(参数数组)
			function smarty_block_test3($params,$content){
				$replace = $params['replace'];
				$maxnum = $params['maxnum'];
				if($replace == 'true') {
					//中文符转换为英文符
					$content = str_replace('，',',',$content);
					$content = str_replace('。','.',$content);
				}
				//对传递进来的$str进行字符串截取
				$content = substr($content,0,$maxnum);
				return $content;
			}
		?>
	2.test3.php
		$smarty->assign('str','hello，my name is LINGCHAUNZHOU。');
		$smarty->display('content.tpl');
	3.在templates中新建content.tpl文档
		{test3 replace='true' maxnum=29}
		{$str}
		{/test3}
#####调用格式总结：
* 1.php内置函数：{参数1|php内置函数：参数2：参数3...}
* 2.registerPlugin：{函数名 参数1 参数2 参数3...}
* 3.自定义function：{函数名 参数1 参数2 参数3...}			
* 4.自定义modifier：{参数1|函数名:参数2:参数3...}
* 5.自定义block：  
				{函数名 参数1 参数2..}  
				{要操作的变量}  
				{/函数名}
*** 
###六、Smarty实例
####制作一个函数，简化第三方类调用过程
	简化：smarty引入——>实例化——>配置
	新建function.php
	function ORG($path,$name,$params=array()){
		//path是路径，name是第三方类名，params是该类初始化的时候要指定、赋值的属性，
		格式为array(属性名=>属性值,属性名2=>属性值2...)

		//将要引用的smarty类库(包含Smarty.class.php、plugins等文件)放在web应用的libs/ORG目录下
		require_once('libs/ORG/'.$path.$name.'.class.php');
		//eval('$obj = new '.$name.'();');
		obj = new $name();
		if(!empty($params)){
		foreach($params as $key=>$value){
			//eval('$obj->'.$key.'=\''.$value.'\';');
			$obj->$key = $value;
			}
		}
		return $obj;
	}
	新建index.php
	<?php
	//url形式 index.php?controller=控制器名&method=方法名
	require_once('function.php');
	$view = ORG('Smarty/','Smarty',array('left_delimiter'=>'{','right_delimiter'=>'}','template_dir'=>'tpl','compile_dir'=>'template_c'));
	$controller = $_GET['controller'];
	$method = $_GET['method'];
	C($controller,$method);
	?>
	但是这样依然不便于维护，array数组很长，
	所以我们将它配置到config.php中，新建config.php
	<?php
		$viewconfig = array('left_delimiter'=>'{','right_delimiter'=>'}','template_dir'=>'tpl','compile_dir'=>'template_c');
	?>
	最终的index.php为：
	<?php
	//url形式 index.php?controller=控制器名&method=方法名
	require_once('function.php');
	require_once('config.php');
	$view = ORG('Smarty/','Smarty',$viewconfig);
	$controller = $_GET['controller'];
	$method = $_GET['method'];
	C($controller,$method);
	?>
	运用配置好的成果：
	新建testController.class.php
	class testController{
		function show() {
		//控制器的作用是调用模型，并调用视图.将模型产生产
		生的数据传递给视图.并让相关视图去显示。
			global $view;
			//$testModel = new testModel();
			$testModel = M('test');
			$data = $testModel->get();
			//$testView = V('test');
			//$testView = display($data);
			$view ->assign('str','哈哈哈');
			$view ->display('test.tpl');
		}
	}
	新建test.tpl，写入：{$str}


###整理思路：
	1.模型层：获取并返回底层的数据(get data; retrun data;)
	2.控制层：调用模型层返回的数据并展示到视图层(如testController中的show方法)
	3.视图层：用于展示数据(echo "hello";)
	4.如何统一访问入口？index.php?xxxx
		a.根据传入的参数$name,$method，调用不同的控制层($nameController)及其相应的函数(如$nameController.method)
		b.因此要统一命名规范。如控制层统一为：$nameController，具体参考function.php
		c.此时要通过传入的参数达到调用不同的系统层的目的，就要将MVC三个层用函数封装起来，分别封装为M、V、C函数
		d.再在C函数中调用模型层和视图层，最终将数据展示给用户。
	5.如何将第三方库运用到MVC中？
		1.首先要知道第三方库如Smarty是一个用于展现数据的框架，所以可以看做是一个视图层。
		2.既然是视图层，就会被控制层所调用，同时也在控制层实例化，包括视图层、
		模型层都是在控制层实例化的。视图层，就会被控制层所调用。
	
	