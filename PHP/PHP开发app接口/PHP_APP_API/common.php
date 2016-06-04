<?php
/**
 * 处理接口公共业务，可以被版本升级接口继承
 */
require_once('./response.php');
require_once('./db.php');
class Common {
	public $params;
	public $app;
	public function check() {//检查从客户端传入的各请求参数是否具备访问权限,如参数是否合法，id是否存在等
		$this->params['app_id'] = $appId = isset($_POST['app_id']) ? $_POST['app_id'] : '';//
		$this->params['version_id'] = $versionId = isset($_POST['version_id']) ? $_POST['version_id'] : '';//大版本号
		$this->params['version_mini'] = $versionMini = isset($_POST['version_mini']) ? $_POST['version_mini'] : '';//小版本号
		$this->params['did'] = $did = isset($_POST['did']) ? $_POST['did'] : '';//客户端识别号
		$this->params['encrypt_did'] = $encryptDid = isset($_POST['encrypt_did']) ? $_POST['encrypt_did'] : '';//加密后的客户端识别号
		
		if(!is_numeric($appId) || !is_numeric($versionId)) {//若传递的参数不是数字
			return Response::show(401, '参数不合法');
		}
		// 判断APP是否需要加密
		$this->app = $this->getApp($appId);
		if(!$this->app) {
			return Response::show(402, 'app_id不存在');
		}
	
		if($this->app['is_encryption'] && $encryptDid != md5($did . $this->app['key'])) {//数据库中的密钥key和客户端传入的设备号did进行加密
			//如果传递了加密识别号且存在数据库中的加密识别号与传递进来的识别号不一致，则无该权限。md5是一种加密方式。
			return Response::show(403, '没有该权限');
		}
	}
	
	public function getApp($id) {//根据传递的id,从数据库中获取app相关的信息
		$sql = "select *
				from `app`
				where id = " . $id ."
				and status = 1 
				limit 1";
		$connect = Db::getInstance()->connect();
		$result = mysql_query($sql, $connect);
		return mysql_fetch_assoc($result);
	}
	
	public function getversionUpgrade($appId) {//根据传入的id，从数据库中获取app相关的版本升级信息
		$sql = "select *
				from `version_upgrade`
				where app_id = " . $appId ."
				and status = 1 
				limit 1";
		$connect = Db::getInstance()->connect();
		$result = mysql_query($sql, $connect);
		return mysql_fetch_assoc($result);
	}
	
	/**
	 * 根据图片大小组装相应图片
	 * @param string $imageUrl
	 * @param string $size
	 */
	public function setImage($imageUrl, $size) {
		if(!$imageUrl) {
			return '';
		}
		if(!$size) {
			return $imageUrl;
		}
		
		$type = substr($imageUrl, strrpos($imageUrl, '.'));
		if(!$type) {
			return '';
		}
		$path = substr($imageUrl, 0, strrpos($imageUrl, '.'));
		
		return $path . '_' . $size . $type;
	}
}
