<?php
	//如果实际做项目开发，这个类是最新创建的，然后再创建mysql.class.php
	//各种数据库的抽象类，可能是mysql,Sqlite,SQL Server等。
	//注意：将参数都指定为sql语句，而不是其他。
class DB {
	//静态之后就可以直接这样使用，DB::query();
	public static $db;

	public static function init($dbtype, $config) {
		self::$db = new $dbtype;
		self::$db->connect($config);
	}

	public static function query($sql){
		//这里不要再继续实例化了，因为静态变量是公用的，已经被上面实例化过了。
		return self::$db->query($sql);//执行自己的具体的query方法
	}

	public static function findAll($sql){
		$query = self::$db->query($sql);
		return self::$db->findAll($query);
	}

	public static function findOne($sql){
		$query = self::$db->query($sql);
		return self::$db->findOne($query);
	}

	public static function findResult($sql, $row = 0, $filed = 0){
		$query = self::$db->query($sql);
		return self::$db->findResult($query, $row, $filed);
	}

	public static function insert($table,$arr){
		return self::$db->insert($table,$arr);
	}

	public static function update($table, $arr, $where){
		return self::$db->update($table, $arr, $where);
	}

	public static function del($table,$where){
		return self::$db->del($table,$where);
	}

}

?>