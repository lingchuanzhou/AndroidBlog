<?php
	$currentdir = dirname(__FILE__);//当前框架的目录地址
	include_once($currentdir.'/include.list.php');//调用include.list.php
	foreach($paths as $path){
		//将include.list.php中包含的各类引用头，一一罗列出来
		include_once($currentdir.'/'.$path);
	}
	class PC{
		public static $controller;//获取当前所运行的控制器名称
		public static $method;//获取当前执行控制器中的哪一个方法
		private static $config;//获取整个网站的配置信息
		private static function init_db(){//初始化数据库
		//这里的mysql可以灵活的换成其他如sqlite等数据库
			DB::init('mysql', self::$config['dbconfig']);
		}
		private static function init_view(){//视图引擎初始化
		//这里的Smarty可以灵活的换成其他的视图引擎
			VIEW::init('Smarty', self::$config['viewconfig']);
		}
		private static function init_controllor(){//控制器初始化
			self::$controller = isset($_GET['controller'])?daddslashes($_GET['controller']):'index';
		}
		private static function init_method(){//如果没有传递方法，将使用默认的index方法
			self::$method = isset($_GET['method'])?daddslashes($_GET['method']):'index';
		}
		public static function run($config){//将上面配置好的方法运行起来
			self::$config = $config;//将各类config(config['dbconfig'],config['viewconfig'])进行初始化
			self::init_db();
			self::init_view();
			self::init_controllor();
			self::init_method();
			C(self::$controller, self::$method);//实例化控制器,控制器里包含了实例化视图层与模型层的方法
		}
	}
?>