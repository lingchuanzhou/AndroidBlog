<?php
	header("Content-type: text/html; charset=utf-8");
	/******************************************
	mysql_connect
	作	用：建立数据库连接
	参	数：数据库服务器地址，数据库用户名，密码
	返回值：1、当连接成功的时候返回mysql连接标识符
			2、当连接失败的时候返回false
	********************************************/
	if($con = mysql_connect('localhost', 'root', '123')){
		//echo "连接成功";
	}else{
		//echo "连接失败";
	}
	/******************************************
	mysql_select_db
	作	用：选择数据库
	参	数：数据库名称，（mysql连接标识符，可选）
	返回值：1、当选择成功的时候返回true
			2、当选择失败的时候返回false
	********************************************/
	if(mysql_select_db('info')){//当选择成功的时候，返回true2、当选择失败的时候返回false
		//echo "选择数据库成功";
	}else{
		//echo "选择数据库失败";
	}
	/******************************************
	mysql_query
	作	用：执行一条 MySQL 查询
	参	数：sql命令，（mysql连接标识符，可选）
	返回值：1、当执行成功的时候，目前我们知道的是，insert成功，返回true
			2、当执行失败的时候返回false
	********************************************/
	mysql_query('set names utf8');
	//if(mysql_query('insert into test(name) values("荔枝")')){//insert的时候，当插入成功，返回true。插入失败返回false
	//	echo "插入成功";
	//}else{
		/******************************************
		mysql_error
		作	用：返回上一个 MySQL 操作产生的文本错误信息
		参	数：（可选 mysql连接标识符）
		返回值：1、返回上一个 MySQL 操作产生的文本错误信息
		********************************************/
	//	echo mysql_error();
	//	echo "插入失败";
	//}
	
	$query = mysql_query('select count(name) from test');//当mysql_query 执行的sql是select语句的时候，如果执行成功，返回的是资源标识符
	print_r(mysql_result($query, 0));
	/*while($row = mysql_fetch_row($query)){//我们发现它返回出了查询到的资源的第一条数据
		echo $row[0].$row[1].'个<br />';
	}*///mysql_fetch_row 每执行一次，都从资源也就是结果集里依次取一条数据，以数组的形式返回出来，当前一次已经取到最后一条数据的时候，这一次返回空结果。返回的数组是一个一维索引数组，每一个下标与数据库里字段的排序相对应。
	/*$row1 = mysql_fetch_row($query);
	print_r($row1);
	$row2 = mysql_fetch_row($query);
	print_r($row2);
	$row3 = mysql_fetch_row($query);
	print_r($row3);
	$row4 = mysql_fetch_row($query);
	print_r($row4);
	$row5 = mysql_fetch_row($query);
	print_r($row5);*/
	
	/******************************************
		mysql_close
		作	用：函数关闭非持久的 MySQL 连接
		参	数：mysql连接标识符
		返回值：1、关闭成功true
				2、关闭失败false
		********************************************/
	mysql_close($con);
?>