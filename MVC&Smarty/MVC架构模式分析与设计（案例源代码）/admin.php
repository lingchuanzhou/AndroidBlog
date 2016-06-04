<?php
	header("Content-type: text/html; charset=utf-8");
	//url形式  index.php?controller=控制器名&method=方法名
	require_once('config.php');//各类配置文件的引入
	require_once('framework/pc.php');//
	PC::run($config);
?>