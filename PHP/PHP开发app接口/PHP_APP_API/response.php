<?php

class Response {
	const JSON = "json";
	/**
	* 按综合方式输出通信数据
	* @param integer $code 状态码
	* @param string $message 提示信息
	* @param array $data 数据
	* @param string $type 数据类型:json/xml
	* return string
	*/
	public static function show($code, $message = '', $data = array(), $type = self::JSON) {
		if(!is_numeric($code)) {
			return '';
		}

		$type = isset($_GET['format']) ? $_GET['format'] : self::JSON;

		$result = array(
			'code' => $code,
			'message' => $message,
			'data' => $data,
		);

		if($type == 'json') {
			self::json($code, $message, $data);
			exit;
		} elseif($type == 'array') {
			var_dump($result);
		} elseif($type == 'xml') {
			self::xmlEncode($code, $message, $data);
			exit;
		} else {
			// TODO
		}
	}
	/**
	* 按json方式输出通信数据
	* @param integer $code 状态码
	* @param string $message 提示信息
	* @param array $data 数据
	* return string
	*/
	public static function json($code, $message = '', $data = array()) {
		
		if(!is_numeric($code)) {
			return '';
		}

		$result = array(
			'code' => $code,
			'message' => $message,
			'data' => $data
		);

		echo json_encode($result);
		exit;
	}

	/**
	* 按xml方式输出通信数据
	* @param integer $code 状态码
	* @param string $message 提示信息
	* @param array $data 数据
	* return string
	*/
	public static function xmlEncode($code, $message, $data = array()) {
		if(!is_numeric($code)) {//当code不是数字，将其置空
			return '';
		}

		$result = array(
			'code' => $code,
			'message' => $message,
			'data' => $data,
		);

		header("Content-Type:text/xml");//设置浏览器响应方式为xml
		$xml = "<?xml version='1.0' encoding='UTF-8'?>\n";
		$xml .= "<root>\n";

		$xml .= self::xmlToEncode($result);

		$xml .= "</root>";
		echo $xml;
	}


	public static function xmlToEncode($data) {//将传入的信息转换为xml

		$xml = $attr = "";
		foreach($data as $key => $value) {
			if(is_numeric($key)) {//如果key是数字,如array(1,7,89)，
					/*则可转换为:
					<item id="0">1</item>
					<item id="1">7</item>
					<item id="2">89</item>*/
				$attr = " id='{$key}'";//其中{ }用于区分变量
				$key = "item";
			}
			$xml .= "<{$key}{$attr}>";
			//如果value值是数组，则递归一次，再次解析成xml节点
			$xml .= is_array($value) ? self::xmlToEncode($value) : $value;
			$xml .= "</{$key}>\n";
		}
		return $xml;
	}
}

