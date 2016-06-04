#PHP面向对象
***
##类的定义：
	修饰符 class 类名{
			var $变量名;
			public $变量名;
			static $变量名;
			function 方法名($参数..){
				retrun 返回值;
			}

		}
##实例化对象：
	$引用变量 = new 类名();
##调用成员方法：
	$引用变量->方法名();
##构造函数：
		function _construct($参数名..){
				初始化成员变量
			}
	或：
		修饰符 类名(){
			}
##析构函数:
	对象结束其生命周期时,类似java中finalize
	function _destrcut(){
			善后处理工作
		}
##继承：PHP不支持多继承
	class 子类 extends 基类 {
	   // 代码部分
	}
##方法重写：
	覆盖父类中的方法，私有除外
##访问控制：
* public（公有）：公有的类成员可以在任何地方被访问。
* protected（受保护）：受保护的类成员则可以被其自身以及其子类和父类访问。
* private（私有）：私有的类成员则只能被其定义所在的类访问，不能访问父类的私有属性。
###属性的访问控制：
* 类属性必须定义为公有，受保护，私有之一。如果用 var 定义，则被视为公有。
###方法的访问控制
* 类中的方法可以被定义为公有，私有或受保护。如果没有设置这些关键字，则该方法默认为公有，被private修饰的方法，不能被子类重写。
##接口：
	和Java中一样。
##常量：
	用const修饰：
				格式：const constant = '常量值';
##Static 关键字：
	格式：
		修饰符 static $变量名 = "值";
	调用方式：
		self::静态属性

##Final 关键字：
	和Java中一样。
##调用父类构造方法：
	parent::__construct()
	"><script>alert('hack')</script>">

####引号中的变量：
	单引号中的变量，会被当做文本来处理。
	如果要在引号中使用变量，请用双引号。

#### PHP empty、isset、isnull的区别

##### 1.empty

	如果 变量 是非空或非零的值，则 empty() 返回 FALSE。换句话说，”"、0、”0″、NULL、
	FALSE、array()、var $var、未定义;以及没有任何属性的对象都将被认为是空的，
	如果 var 为空，则返回 TRUE。

##### 2.isset

	如果 变量 存在(非NULL)则返回 TRUE，否则返回 FALSE(包括未定义）。
	变量值设置为：null，返回也是false;unset一个变量后，变量被取消了。
	注意，isset对于NULL值变量，特殊处理。

##### 3.is_null

	检测传入值【值，变量，表达式】是否是null,只有一个变量定义了，
	且它的值是null，它才返回TRUE . 其它都返回 FALSE 【未定义变量传入后会出错！】

#### 4.empty()和isset()的区别：
	empty()和isset()的处理对象无外乎未定义变量，0，空字符串。 

	a.如果变量为0，则empty()会返回TRUE，isset()会返回TRUE； 
	b.如果变量为空字符串，则empty()会返回TRUE，isset()会返回TRUE； 
	c.如果变量未定义，则empty()会返回TRUE，isset()会返回FLASE； 


#### extract函数：
	$a = "Original";
	$my_array = array("a" => "Cat","b" => "Dog", "c" => "Horse");
	extract($my_array);
	echo "\$a = $a; \$b = $b; \$c = $c";
	打印结果：
			a = Cat; $b = Dog; horse = Horse
	extract函数可以将数组中的key引用成变量，如"a"引用为$a。
	其中\$b是为了区分$b不以变量的方式打印。

#### implode(分隔符，数组):
	implode函数是把数组组合成字符串
		<?php
		$arr = array('Hello','World!','I','love','Shanghai!');
		echo implode(" ,",$arr);
		?> 
	打印：Hello ,World! ,I ,love ,Shanghai!
#### mysql_real_escape_string：转义 SQL 语句中使用的字符串中的特殊字符。防止黑客注入。

#### mysql_real_escape_string和addslashes的区别：
	1.mysql_real_escape_string必须是连接数据库之后才能使用.
	2.addslashes不知道任何有关MySQL连接的字符集
	3.与addslashes对比,mysql_real_escape_string同时还对\r、\n和\x1a进行转义。
	4.