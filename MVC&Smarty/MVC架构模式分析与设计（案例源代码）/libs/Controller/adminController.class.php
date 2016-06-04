<?php
	class adminController{

		public $auth;

		public function __construct(){
			session_start();
			if(!(isset($_SESSION['auth']))&&(PC::$method!='login')){
				$this->showmessage('请登录后在操作！', 'admin.php?controller=admin&method=login');
			}else{
				$this->auth = isset($_SESSION['auth'])?$_SESSION['auth']:array();
			}
		}

		public function index(){//用于显示新闻首页
			$newsobj = M('news');
			$newsnum = $newsobj->count();//新闻数量
			VIEW::assign(array('newsnum'=>$newsnum));
			VIEW::display('admin/index.html');
		}
		public function login(){//登陆业务逻辑
			if(!isset($_POST['submit'])){
				//显示登录界面
				//登录处理的业务逻辑放在admin里面
				//admin同表名的模型(adminModel.class.php)：从数据库里取用户信息
				//auth模型()：进行用户信息核对
				VIEW::display('admin/login.html');
			}else{
				$this->checklogin();
			}
		}

		public function logout(){//退出登录
			unset($_SESSION['auth']);
				$this->showmessage('退出成功！', 'admin.php?controller=admin&method=login');
		}

		public function newsadd(){//添加新闻操作
			if(!isset($_POST['submit'])){//没有post程序，就显示添加修改界面
				$data = $this->getnewsinfo();//取新闻数据
				VIEW::assign(array('data'=>$data));
				VIEW::display('admin/newsadd.html');
			}else{
				$this->newssubmit();
			}
		}

		public function newslist(){
			$data = $this->getnewslist();
			VIEW::assign(array('data'=>$data));
			VIEW::display('admin/newslist.html');
		}

		public function newsdel(){
			if($_GET['id']){
				$this->delnews();
				$this->showmessage('删除新闻成功！', 'admin.php?controller=admin&method=newslist');
			}
		}

		private function checklogin(){//登陆验证
			if(empty($_POST['username'])||empty($_POST['password'])){
				$this->showmessage('登录失败！', 'admin.php?controller=admin&method=login');
			}
			$username = daddslashes($_POST['username']);
			$password = daddslashes($_POST['password']);
			$authobj = M('auth');
			if($auth = $authobj->checkauth($username, $password)){
				$_SESSION['auth'] = $auth;
				$this->showmessage('登录成功！', 'admin.php?controller=admin&method=index');
			}else{
				$this->showmessage('登录失败！', 'admin.php?controller=admin&method=login');
			}
		}

		private function getnewsinfo(){//获取将要修改的新闻信息
			if(isset($_GET['id'])){//如果有id说明是修改文章
				$id = intval($_GET['id']);//转换为数字
				$newsobj = M('news');
				return $newsobj->findOne_by_id($id);
			}else{
				return array();
			}
		}

		private function getnewslist(){
			$newsobj = M('news');
			return $newsobj->findAll_orderby_dateline();
		}

		private function delnews(){
			$newsobj = M('news');
			return $newsobj->del_by_id($_GET['id']);
		}

		private function newssubmit(){
			extract($_POST);				
			if(empty($title)||empty($content)){
				$this->showmessage('请把新闻标题、内容填写完整再提交！', 'admin.php?controller=admin&method=newsadd');
			}
			$title = daddslashes($title);
			$content = daddslashes($content);
			$author = daddslashes($author);
			$from = daddslashes($from);
			$newsobj = M('news');
			$data = array(
				'title'=>$title,
				'content'=>$content,
				'author'=>$author,
				'from'=>$from,
				'dateline'=>time()
			);
			if($_POST['id']!=''){
				$newsobj ->update($data, intval($_POST['id']));
				$this->showmessage('修改成功！', 'admin.php?controller=admin&method=newslist');
			}else{
				$newsobj ->insert($data);
				$this->showmessage('添加成功！', 'admin.php?controller=admin&method=newslist');
			}
		}

		private function showmessage($info, $url){
			echo "<script>alert('$info');window.location.href='$url'</script>";
			exit;
		}
	}
?>