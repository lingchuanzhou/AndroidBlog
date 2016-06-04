#PHP与Mysql关系
##phpmyadmin新建数据库无权限：
	解决步骤：
		1.主要要保持mysql数据库密码与wamp中mysql密码一致
		2.打开F:\WorkSpace\wamp\apps\phpmyadmin4.1.14\libraries\config.default.php,
		修改$cfg['Servers'][$i]['password'] = '123456';
		3.重启phpmyadmin，如果还是无法新建数据库，则打开F:\WorkSpace\wamp\apps\
		phpmyadmin4.1.14\config.inc.php,修改$cfg['Servers'][$i]['password']='123456';
		4.重启phpmyadmin，如果还是无法新建数据库，则修改phpmyadmin用户组中root用户的密码。
		5.总之就是这三处地方，反复折腾，有的地方无需设置密码，而有的地方则需要。
		
###PHP操作mysql:
	1.连接数据库:mysql_connect(主机名,用户名,密码)
		<?php
			header("content-type:text/html;charset=GBK");
			if($con=mysql_connect('localhost','root','duwei123')){
			//1.当连接成功的时候，返回mysql标识符
			//2.当连接失败的时候，返回false
			echo '连接成功';
			}else{
			echo '连接失败';
			}
	2.关闭数据库:mysql_close(标识符)
		mysql_close($con);
	3.选择数据库:bool mysql_select_db(数据库名)
		mysql_select_db('info');
		//1.当连接成功的时候，返回true
		//2.当连接失败的时候，返回false
	4.创建数据库:bool mysql_query(数据库名,标识符)
		$sql = 'CREATE DATABASE RUNOOB';
		$retval = mysql_query( $sql, $conn );
	5.创建数据表:bool mysql_query(数据库名,标识符)
		//定义sql语句
		$sql = "CREATE TABLE runoob_tbl( ".
       "runoob_id INT NOT NULL AUTO_INCREMENT, ".
       "runoob_title VARCHAR(100) NOT NULL, ".
       "runoob_author VARCHAR(40) NOT NULL, ".
       "submission_date DATE, ".
       "PRIMARY KEY ( runoob_id )); ";
		//选中该表
		mysql_select_db( 'RUNOOB' );
		//创建表
		mysql_query($sql,$con);
	6.删除数据表:bool mysql_query(数据库名,标识符)
		//定义sql语句
		$sql = "DROP TABLE runoob_tbl";
		//删除表
		mysql_query($sql,$con);
	7.mysql异常:
			mysql_error:用于捕获异常消息
	8.插入表数据:bool mysql_query(数据库名,标识符)
		//定义sql语句
		$sql = "INSERT INTO runoob_tbl ".
       "(runoob_title,runoob_author, submission_date) ".
       "VALUES ".
       "('title','author','2015-5-19')";
		//插入数据
		mysql_query($sql,$con);
	9.mysql_query(set names utf8):防止乱码
	10.查询表数据:
		配合mysql_query('select * from table')，如果这条语句执行成功，返回的将是结果的地址引用。
		A：mysql_fetch_row():
			每执行一次，都从资源，也就是结果集里取一条数据，以数组的形式返回，
			当所有数据都被取完之后，就会返回null。返回的数组是一个一维索引数组，
			每一个下标与数据库里字段的排序相对应。
		B：mysql_fetch_array():
			与mysql_fetch_row的区别：
			1.mysql_fetch_row取一条数据产生一个索引数组
			2.mysql_fetch_array默认状态下取一条数据产生一个索引数组和一个关联数组，
			3.因此mysql_fetch_array可以通过字段名来取值，无需依靠数字下标取值。
		  mysql_fetch_array的第二个参数：
			1.MYSQL_ASSOC-关联数组
			2.MYSQL_NUM-数字数组(索引数组)
			3.MYSQL_BOTH-默认

		/*************关联数组***************/
		mysql_fetch_array($query,MYSQL_ASSOC);
		/*************索引数组***************/
		mysql_fetch_row($query);
		mysql_fetch_array($query,MYSQL_NUM);
		/***********关联+索引数组*************/
		mysql_fetch_array($query,MYSQL_BOTH);
		mysql_fetch_array($query);
		
		C：mysql_fetch_assoc():
		和mysql_fetch_array($query,MYSQL_ASSOC);效果一致。
		
		D：mysql_fetch_object():
		它输出的结果是一个对象。
		$query = mysql_query('select * from data');
		$arr = mysql_fetch_object($query);
		print_r($arr);
		$arr->name;
		输出：
			stdClass Object([id]=>1 [name]=>菠萝蜜 [num]=>2 [price]=>10)
			菠萝蜜

	11.mysql_num_rows:返回结果集中行的数目。
	用于做if判断：
		if($query && mysql_num_rows($query)):
		表示有结果并且结果集中的条目不为0，才往下继续执行。
		
	12.mysql_result:返回结果集中一个字段的值
	用法：mysql_result(query结果集,结果集序列号,'字段名'/字段序列);

	13.mysql_affected_rows(连接标识符conn):返回收到影响的行数
	返回前一次Insert,update,delete影响的记录数。
	14.位于esc键下有一个`符号，用于标示一些变量为非sql关键词,如`from`，那么from就不是一个关键词了。
	15.mysql_insert_id(): 函数返回上一步 INSERT 操作产生的 ID。

###mysql支持limit
	select * from tablename limit 0,1
	即取出第一条记录。
	
	select * from tablename limit 1,1
	第二条记录
	
	select * from tablename limit 10,20
	从第11条到31条（共计20条）
	
		
		