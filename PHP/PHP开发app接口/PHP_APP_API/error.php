<?php
require_once('./common.php');
class ErrorLog extends Common {
	public function index() {
		$this->check();//先检验请求参数是否正确，防止出现安全问题

		$errorLog = isset($_POST['error_log']) ? $_POST['error_log'] : '';
		if(!$errorLog) {
			return Response::show(401, '日志为空');
		}
		//如果有日志错误信息从客户端发过来。
		$sql = "insert into 
					error_log(
						`app_id`,
						`did`,
						`version_id`,
						`version_mini`,
						`error_log`,
						`create_time`)
					values(
						".$this->params['app_id'].",
						'".$this->params['did']."',
						".$this->params['version_id'].",
						".$this->params['version_mini'].",
						'".$errorLog."',
						".time()."
					)";//其中的日志errorLog是文本信息，需要加''
		$connect = Db::getInstance()->connect();
		if(mysql_query($sql, $connect)) {
			return Response::show(200, '错误信息插入成功');
		} else {
			return Response::show(400, '错误信息插入失败'.$sql);
		}
	}
}

$error = new ErrorLog();
$error->index();