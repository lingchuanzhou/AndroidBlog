<?php
	class adminModel{
		//前台登陆模型层
		//定义表名
		public $_table = 'admin';

		//通过用户名取用户信息
		function findOne_by_username($username){
			$sql = 'select * from '.$this->_table.' where username="'.$username.'"';
			return DB::findOne($sql);
		}

		function count(){
			$sql = 'select count(*) from '.$this->_table;
			return DB::findResult($sql, 0, 0);
		}
	}
?>