<?php
	header("Content-type: text/html; charset=utf-8");
	//url��ʽ  index.php?controller=��������&method=������
	require_once('config.php');//���������ļ�������
	require_once('framework/pc.php');//
	PC::run($config);
?>