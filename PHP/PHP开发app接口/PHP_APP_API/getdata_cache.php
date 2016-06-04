<?php
//首页接口
// 假设客户端请求http://app.com/list.php?page-=1&pagesize=12
require_once('./response.php');//数据转换类
require_once('./db.php');//数据库类
require_once('./file.php');//缓存类
$page = isset($_GET['page']) ? $_GET['page'] : 1;//当前页
$pageSize = isset($_GET['pagesize']) ? $_GET['pagesize'] : 6;//每页显示的条数
if(!is_numeric($page) || !is_numeric($pageSize)) {//判断请求的参数是否合法
	return Response::show(401, '数据不合法');
}
$offset = ($page - 1) * $pageSize;//数据起始值
//从数据库中查询状态行为1的数据，从第$offset条开始取数据,共取$pageSize条
$sql = "select * from video where status = 1 order by orderby desc limit ". $offset ." , ".$pageSize;
	$videos = array();
	$cache = new File();
	if(!$videos = $cache->cacheData('index_mk_cache' . $page .'-' . $pageSize)) {
	//echo 1;exit;	
	try {
		$connect = Db::getInstance()->connect();
	} catch(Exception $e) {
		// $e->getMessage();
		return Response::show(403, '数据库链接失败');
	}
	$result = mysql_query($sql, $connect); 

	while($video = mysql_fetch_assoc($result)) {
		$videos[] = $video;//获取数据库中所有数据
	}
	if($videos) {//如果从数据库中获取videos成功,则将数据保存一份。如index_mk_cache1-6.txt
		$cache->cacheData('index_mk_cache' . $page .'-' . $pageSize, $videos, 1200);
	}
}
if($videos) {
	return Response::show(200, '首页数据获取成功', $videos);
} else {
	return Response::show(400, '首页数据获取失败', $videos);
}





















