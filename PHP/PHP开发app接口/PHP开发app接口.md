# PHP开发app接口
***
## 封装通信接口数据的方法
### JSON方式封装接口数据
#### 1.方法：json_encode($value)
	注：该函数只能接受UTF-8编码的数据。
	iconv('utf-8','GBK','$data'):将$data从utf-8的编码格式转换为GBK编码。
	详见：response.php

#### 2.通信数据标准格式
		code		状态码(200,400等)
		message		提示信息(邮箱格式不正确等)
		data		返回数据
	
		JSON
		   |--code:	200
		   |--message:	"数据返回成功"
		   |--data
		   		|--id:	1
		   		|--name:	"lingchuanzhou"
#### 3.代码示例
	/**
	* 按json方式输出通信数据
	* @param integer $code 状态码
	* @param string $message 提示信息
	* @param array $data 数据
	* return string
	*/
	public static function json($code, $message = '', $data = array()) {
		
		if(!is_numeric($code)) {
			return '';
		}

		$result = array(
			'code' => $code,
			'message' => $message,
			'data' => $data
		);

		echo json_encode($result);
		exit;
	}
	
### XML方式封装接口数据
#### 1)组装字符串
##### 1.1)字符串方式生成xml数据
	public static funciton xml(){
		header("Content-Type:text/xml");
		$xml  = "<?xml version='1.0' encoding='UTF-8' ?>\n";
		$xml .= "<root>\n";
		$xml .= "<code>200</code>\n";
		$xml .= "<message>数据返回成功</message>\n";
		$xml .= "<data>\n";
		$xml .= "<id>1</id>\n";
		$xml .= "<name>singwa</name>\n";
		$xml .= "</data>\n";
		$xml .= "</root>\n";
	
		echo $xml;
	}
 
##### 1.2)xml方式封装接口数据方法
	封装方法：详见response.php
	xmlEncode($code,$message="",$data=array())
	data数据分析
	1.array('index' => 'api');
	2.array(1,7,89)转换为:
		<item id="0">1</item>
		<item id="1">7</item>
		<item id="2">89</item>
		
#### 2)使用系统类
		DomDocument
		XMLWriter
		SimpleXML
### 综合通信方式封装数据接口
	封装方法：详见response.php
	show($code,$message,$data=array(),$type=' json')

## 核心技术
### 缓存技术
#### 1.静态缓存
	保存在磁盘上的静态文件，用PHP生成数据放入静态文件中
#### 2.Memcache redis缓存
	1.Memcache和Redis都是用来管理数据的
	2.它们的数据都是存放在内存里
	3.Redis可以定期将数据备份到磁盘（永久化）
	4.Memcache只是简单的key/value缓存
	5.Redis不仅仅支持简单的k/v类型的数据，同时还提供list，set，hash等数据结构的存储

	如何操作数据：
	Redis数据操作
		1.开启redis客户端-
			redis-server.exe redis.windows.conf
			开启Redis客户端新cmd窗口redis-cli.exe
		2.设置缓存值 - set key value
		3.获取缓存数据 - get key
		4.设置过期时间 - setex key 10 value
		5.删除缓存 - del key
		
	PHP操作Redis
		1.安装phpredis扩展
		2.PHP连接redis服务-connect(redis所在主机ip地址,redis端口号)
		3.设置缓存
		4.获取缓存
		<?php
			$redis = new Redis();
			$redis->connect('127.0.0.1',6379);
			$redis->set('singwa',123);
		
	PHP操作Memcache
		1.安装memcache扩展
		2.连接服务-connect('memcache_host',11211);
		3.set 设置缓存
		4.get 获取缓存



### 定时任务
	1.定时任务服务提供crontab命令来设定服务
	2.crontab -e	 编辑某个用户cron服务的详细内容
	3.crontab -l 列出某个用户cron服务的详细内容
	4.crontab -r 删除某个用户的cron服务


## APP接口实例

### 4.1单例模式连接数据库
#### 1.掌握单例模式设计
	单例模式三大原则：(单例就是一个类，只能拥有一个实例！)
	1.构造函数需要标记为非public[只能声明成私有模式private]（防止外部使用new操作符创建对象），
	单例类不能在其他类中实例化，只能被其自身实例化；
	2.拥有一个保存类的实例的静态成员变量$_instance;
	3.拥有一个访问这个实例的公共的静态方法
#### 2.PHP如何连接数据库
	详见：db.php

### 4.2首页接口开发以及客户端app演示
#### 4.2.1首页接口方案一：详见list.php
	-流程
		http请求->服务器->查询数据>返回数据
### 4.3 app版本升级以及app演示
#### 4.3.1 版本升级分析以及数据表设计
#### 4.3.2 版本升级接口开发以及app演示
### 4.4 app错误日志接口
	1、app强退
	2、数据加载失败
	3、app潜在问题
	