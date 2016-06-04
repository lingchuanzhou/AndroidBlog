<?php

require_once('./common.php');
class Init extends Common {
	public function index() {//处理版本升级
		$this->check();
		// 获取版本升级信息
		$versionUpgrade = $this->getversionUpgrade($this->app['id']);
		if($versionUpgrade) {
			//假如当前type值不为0，且客户端版本号version_id小于服务端版本号，则升级。
			if($versionUpgrade['type'] && $this->params['version_id'] < $versionUpgrade['version_id']) {
				//将当前type值赋给is_upload，用于客户端判断是否需要更新。
				$versionUpgrade['is_upload'] = $versionUpgrade['type'];
			}else {
				$versionUpgrade['is_upload'] = 0;
			}
			return Response::show(200, '版本升级信息获取成功', $versionUpgrade);
		} else {
			return Response::show(400, '版本升级信息获取失败');
		}
	}
}

$init = new Init();
$init->index();