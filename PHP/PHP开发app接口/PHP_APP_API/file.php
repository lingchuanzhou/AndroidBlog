<?php

class File {
	private $_dir;

	const EXT = '.txt';//设置默认的缓存文件类型

	public function __construct() {
		///默认存放缓存的文件夹地址，dirname()函数返回文件的目录名
		$this->_dir = dirname(__FILE__) . '/files/';
	}
	/**
	* 按综合方式输出通信数据
	* @param string $key 缓存文件的文件名
	* @param string $value 缓存数据
	* @param integer $cacheTime 缓存时间
	* return file_put_contents：文件字节数
	*/
	/*
	*静态缓存的原理：
	*	将缓存文件存入本地的时间加上设置缓存的时间$cacheTime和当前时间做比较，
	*	如果当前时间更大，说明已经超过了缓存时间，缓存已经失效，就不能读取缓存了
	*	如果当前时间更小，说明还没有失效，可以读取缓存。
	*/
	public function cacheData($key, $value = '', $cacheTime = 0) {//默认缓存时间为永久有效
		$filename = $this->_dir  . $key . self::EXT;//文件路径
		
		//如果传入了value值,将value值写入缓存
		if($value !== '') { 
			if(is_null($value)) {//如果传入的value值为null，则删除该缓存
				return @unlink($filename);//unlink()表示删除文件。@表示如果参数不正确，则忽略警告
			}
			$dir = dirname($filename);//设置缓存存放地址
			if(!is_dir($dir)) {//判断目录是否存在
				mkdir($dir, 0777);//若不存在则创建目录
			}

			$cacheTime = sprintf('%011d', $cacheTime);//限制cacheTime的长度为11位,如果位数不足，则在前面用0凑齐
			//写入缓存到文件中,file_put_contents只接受string类型数据，所以要json_encode，就是反json
			return file_put_contents($filename,$cacheTime . json_encode($value));//将cacheTime一并写入缓存文件中
		}

		if(!is_file($filename)) {//如果文件不存在，返回false
			return FALSE;
		} 
		//如果没有传入value值且文件key存在，则将缓存数据返回
		$contents = file_get_contents($filename);//获取缓存
		$cacheTime = (int)substr($contents, 0 ,11);//从文件中取出cacheTime
		$value = substr($contents, 11);//从文件中取出传入的value值
		if($cacheTime !=0 && ($cacheTime + filemtime($filename) < time())) {//filemtime:文件创建时间
		//如果默认缓存时间不为0且超过了缓存失效时间
			unlink($filename);
			return FALSE;
		}
		
		//将数据从json格式解析为对象格式，true表示解析为原值形式，如数组。
		return json_decode($value, true);
	}
}

$file = new File();

echo $file->cacheData('test1');