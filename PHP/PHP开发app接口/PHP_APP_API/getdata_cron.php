<?php
	//首页接口
// 假设客户端请求http://app.com/list.php?page-=1&pagesize=12
require_once('./response.php');//数据转换类
require_once('./file.php');//缓存类

$file = new File();
//从定时缓存文件index_cron_cahce中获取数据，index_cron_cahce由cron.php生成
$data = $file->cacheData('index_cron_cahce');
if($data) {
	return Response::show(200, '首页数据获取成功', $data);
}else{
	return Response::show(400, '首页数据获取失败', $data);
}
exit;
require_once('./db.php');
require_once('./file.php');
$page = isset($_GET['page']) ? $_GET['page'] : 1;//当前页
$pageSize = isset($_GET['pagesize']) ? $_GET['pagesize'] : 6;//每页显示的条数
if(!is_numeric($page) || !is_numeric($pageSize)) {//判断请求的参数是否合法
	return Response::show(401, '数据不合法');
}

$offset = ($page - 1) * $pageSize;//数据起始值
//从数据库中查询状态行为1的数据，从第$offset条开始取数据,共取$pageSize条
$sql = "select * from video where status = 1 order by orderby desc limit ". $offset ." , ".$pageSize;
$cache = new File();
$videos = array();
if(!$videos = $cache->cacheData('index_mk_cache' . $page .'-' . $pageSize)) {
	//如果缓存值不存在，无法通过cacheData返回数据，则表示没有缓存数据，则从数据库中去读取。
	//如果存在，则将返回值直接赋给 videos数组并取出，注:因为if()中的语句无论如何都会被运行，所以能够赋值成功
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

if($videos) {//如果存在缓存数据，则输出$videos
	return Response::show(200, '首页数据获取成功', $videos);
} else {
	return Response::show(400, '首页数据获取失败', $videos);
}
