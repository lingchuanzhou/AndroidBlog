##mysql管理：
	1.mysql登陆：
	[root@host]# mysql -u root -p
	Enter password:123456
	2.mysql退出：
		mysql>exit
	
	
##一、sql语句

	1.操作数据库
	(1)创建数据库
		CREATE  DATABASE  [IF NOT EXISTS] db_name [create_specification [, create_specification] ...] 
		create_specification:??? 
		  [DEFAULT] CHARACTER SET charset_name? |   [DEFAULT] COLLATE collation_name 

		~创建一个名称为mydb1的数据库。
		    create database mydb1;
		~创建一个使用gbk字符集的mydb2数据库。
		    create database mydb2 character set gbk;
		~创建一个使用utf8字符集，并带校对规则的mydb3数据库。(校对规则：与排序有关,详见文档)
		    create database mydb3 character set utf8 collate utf8_bin;
	(2)查看数据库
		显示数据库语句：
			SHOW DATABASES
		显示数据库创建语句：
			SHOW CREATE DATABASE db_name
 
               ~查看当前数据库服务器中的所有数据库 
			show databases;
	       ~查看前面创建的mydb2数据库的定义信息
			show create database mydb3;
	(3)修改数据库
		ALTER  DATABASE  [IF NOT EXISTS] db_name??[alter_specification [, alter_specification] ...] 
		alter_specification:??? 
		    [DEFAULT] CHARACTER SET charset_name? |   [DEFAULT] COLLATE collation_name
		
		~查看服务器中的数据库，并把其中mydb2字符集修改为utf8
		alter database mydb2 character set utf8;
	(4)删除数据库
		DROP DATABASE  [IF EXISTS]  db_name 
		
		~删除前面创建的mydb1数据库 drop database mydb1;
		drop database mydb1;
	(5)选择数据库
		进入数据库:use db_name;
		查看当前所选的数据库: select database();
	2.操作表
	(1)创建表
		CREATE TABLE table_name
		(
			field1  datatype,
			field2  datatype,
			field3  datatype,
		)[character set 字符集] [collate 校对规则]
		field：指定列名　datatype：指定列类型
	MySQL常用数据类型：
		A:字符串型  
				VARCHAR：长度可变
				CHAR：长度固定
		B:大数据类型(电影，小说等)：
				BLOB、TEXT
		C:数值型
				TINYINT 、SMALLINT、INT、BIGINT、FLOAT、DOUBLE
		D:逻辑型 
				BIT(一位数据)
		E:日期型
				DATE、TIME、DATETIME、TIMESTAMP(时间戳：保存最后修改时间)

		~创建一个员工表employee 最长的长度只能为40,unique表示值不能重复,not null表示值不能为空。
			create table employee(
				id int primary key auto_increment,
				name varchar(20) unique,
				gender bit not null,
				birthday date,
				entry_date date,
				job varchar(40),
				salary double,
				resume text
			);
			
			create table student(
			id int primary key auto_increment,
			name varchar(40) unique,
			sex bit not null,
			birthday date);
	(2)查看表
		查看表结构：desc tabName
		查看当前数据库中所有表：show tables
		查看当前数据库表建表语句 show create table tabName;

	
	(3)修改表
		ALTER TABLE table_name  ADD/MODIFY/DROP/CHARACTER SET/CHANGE  (column datatype [DEFAULT expr][, column datatype]...);
		*修改表的名称：rename table 表名 to 新表名;

		~在上面员工表的基本上增加一个image列。
			alter table employee add image blob;
		~修改job列，使其长度为60。
			alter table employee modify job varchar(60);
		~删除gender列。
			alter table employee drop gender;
		~表名改为user。
			rename table employee to user;
		~修改表的字符集为gbk
			alter table table_name character set gbk;
		~列名name修改为username
			alter table table_name change name username varchar(20);
	(4)删除表
		DROP TABLE tab_name;

		~删除user表
			drop table user;
	3.操作表记录CRUD
	(1)INSERT
		INSERT INTO table [(column [, column...])] VALUES (value [, value...]);
		注意事项：
		插入的数据应与字段的数据类型相同。
		数据的大小应在列的规定范围内，例如：不能将一个长度为80的字符串加入到长度为40的列中。
		在values中列出的数据位置必须与被加入的列的排列位置相对应。
		字符和日期型数据应包含在单引号中。
		插入空值：不指定或insert into table value(null)
		如果要插入所有字段可以省写列列表，直接按表中字段顺序写值列表


		~使用insert语句向表中插入三个员工的信息:其中id为自增长，不用给值。
		insert into employee (id,name,gender,birthday,entry_date,job,salary,resume)values (null,'张飞',1,'1999-09-09','1999-10-01','打手',998.0,'老大的三弟,真的很能打');
		insert into employee values (null,'关羽',1,'1998-08-08','1998-10-01','财神爷',9999999.00,'老大的二弟,公司挣钱都指着他了');
		insert into employee values (null,'刘备',0,'1990-01-01','1991-01-01','ceo',100000.0,'公司的老大'),(null,'赵云',1,'2000-01-01','2001-01-01','保镖',1000.0,'老大贴身人');
		
		show variables like 'character%';查看编码类型：
				+--------------------------+-------------------------------------------+
				| Variable_name            | Value                                     |
				+--------------------------+-------------------------------------------+
				| character_set_client     | utf8                                      |
				| character_set_connection | utf8                                      |
				| character_set_database   | gbk                                       |
				| character_set_filesystem | binary                                    |
				| character_set_results    | utf8                                      |
				| character_set_server     | utf8                                      |
				| character_set_system     | utf8                                      |
				| character_sets_dir       | H:\MySQL\MySQL Server 5.0\share\charsets\ |
				+--------------------------+-------------------------------------------+
		set names gbk;表示设置set_client,set_connection,set_results的编码为gbk：否则将出现乱码；每开一个窗口都要单独设置一次。
									但为了避免麻烦，可以找到mysql.ini文件将default-character-set=utf8，改为default-character-set=gbk
		
	(2)UPDATE
		UPDATE 	tbl_name SET col_name1=expr1 [, col_name2=expr2 ...]?[WHERE where_definition]??
		
		UPDATE语法可以用新值更新原有表行中的各列。
		SET子句指示要修改哪些列和要给予哪些值。
		WHERE子句指定应更新哪些行。如没有WHERE子句，则更新所有的行
		

		~将所有员工薪水修改为5000元。
			update employee set salary = 5000;
		~将姓名为’张飞’的员工薪水修改为3000元。
			update employee set salary = 3000 where name='张飞';
		~将姓名为’关羽’的员工薪水修改为4000元,job改为ccc。
			update employee set salary=4000,job='ccc' where name='关羽';
		~将刘备的薪水在原有基础上增加1000元。	
			update employee set salary=salary+1000 where name='刘备';
		
		? 
	(3)DELETE
		
		如果不使用where子句，将删除表中所有数据。
		Delete语句不能删除某一列的值（可使用update）
		使用delete语句仅删除记录，不删除表本身。如要删除表，使用drop table语句。
		同insert和update一样，从一个表中删除记录将引起其它表的参照完整性问题，在修改数据库数据时，头脑中应该始终不要忘记这个潜在的问题。
			外键约束
		删除表中数据也可使用TRUNCATE TABLE 语句，它和delete有所不同，参看mysql文档。


		delete from tbl_name?[WHERE where_definition]??? 
		~删除表中名称为’张飞’的记录。
			delete from employee where name='张飞';
		~删除表中所有记录。
			delete from employee;
		~使用truncate删除表中记录:truncate 会摧毁整张表，然后再新建表，这样id会重新变成1,2,3...
			truncate table employee;
			
	(4)SELECT：查询表
	首先创建一个表并插入数据：
	create table exam(
				id int primary key auto_increment,
				name varchar(20) not null,
				chinese double,
				math double,
				english double
				);
	insert into exam values(null,'关羽',85,76,70);
	insert into exam values(null,'张飞',70,76,70);
	insert into exam values(null,'赵云',90,76,70);
	
		~1.基本查询
			SELECT [DISTINCT] *|{column1, column2. column3..} FROM	table;
			
			~查询表中所有学生的信息。
				select * from exam;
			~查询表中所有学生的姓名和对应的英语成绩。
				select name,english from exam;
			~过滤表中重复数据
				 select distinct english from exam;
			~在所有学生分数上加10分特长分显示：注意此时数据库中数据并未改变。
				select name , math+10,english+10,chinese+10 from exam;
			~统计每个学生的总分。
				select name ,english+math+chinese from exam;
			使用别名表示学生总分。
				select name as 姓名 ,english+math+chinese as 总成绩 from exam;
				select name 姓名 ,english+math+chinese 总成绩 from exam;
			select name english from exam;

		~2.使用where子句进行过滤查询
			~查询姓名为张飞的学生成绩
				select * from exam where name='张飞';
			~查询英语成绩大于90分的同学
				select * from exam where english > 90;
			~查询总分大于230分的所有同学
				select name 姓名,math+english+chinese 总分 from exam where math+english+chinese>230;
			~查询英语分数在 80－100之间的同学。
				select * from exam where english between 80 and 100;
			~查询数学分数为75,76,77的同学。
				select * from exam where math in(75,76,77);
			~查询所有姓张的学生成绩。
				select * from exam where name like '张%';
				select * from exam where name like '张__';
			~查询数学分>70，语文分>80的同学。
				select * from exam where math>70 and chinese>80;

		~3.使用order by关键字对查询结果进行排序操作
			SELECT column1, column2. column3.. FROM	table where... order by column asc|desc;
			asc 升序 -- 默认就是升序
			desc 降序

			~对语文成绩排序后输出。
				select name,chinese from exam order by chinese desc;
			~对总分排序按从高到低的顺序输出
				select name 姓名,chinese+math+english 总成绩 from exam order by 总成绩 desc;
			~对姓张的学生成绩排序输出
				select name 姓名,chinese+math+english 总成绩 from exam where name like '张%' order by 总成绩 desc;
		~4.聚合函数
			(1)Count -- 用来统计符合条件的行的个数
				~统计一个班级共有多少学生？
					select count(*) from exam;
				~统计数学成绩大于90的学生有多少个？
					select count(*) from exam where math>70;
				~统计总分大于230的人数有多少？
					select count(*)from exam where math+english+chinese > 230;
			(2)SUM -- 用来将符合条件的记录的指定列进行求和操作
				~统计一个班级数学总成绩？
					select sum(math) from exam;
				~统计一个班级语文、英语、数学各科的总成绩
					select sum(math),sum(english),sum(chinese) from exam;
				~统计一个班级语文、英语、数学的成绩总和
					select sum(ifnull(chinese,0)+ifnull(english,0)+ifnull(math,0)) from exam;
					在执行计算时,只要有null参与计算,整个计算的结构都是null
					此时可以用ifnull函数进行处理
				~统计一个班级语文成绩平均分
					select sum(chinese)/count(*) 语文平均分 from exam;
			(3)AVG -- 用来计算符合条件的记录的指定列的值的平均值
				~求一个班级数学平均分？
					select avg(math) from exam;
				~求一个班级总分平均分？
					select avg(ifnull(chinese,0)+ifnull(english,0)+ifnull(math,0)) from exam;

			(4)MAX/MIN -- 用来获取符合条件的所有记录指定列的最大值和最小值
				~求班级最高分和最低分
					select max(ifnull(chinese,0)+ifnull(english,0)+ifnull(math,0)) from exam;
					select min(ifnull(chinese,0)+ifnull(english,0)+ifnull(math,0)) from exam;
		~5.分组查询
			~对订单表中商品归类后，显示每一类商品的总价
				select product,sum(price) from orders group by product;
			~询购买了几类商品，并且每类总价大于100的商品
				select product 商品名,sum(price)商品总价 from orders group by product having sum(price)>100;
				
			where子句和having子句的区别:
				where子句在分组之前进行过滤having子句在分组之后进行过滤
				having子句中可以使用聚合函数,where子句中不能使用
				很多情况下使用where子句的地方可以使用having子句进行替代

			~查询单价小于100而总价大于150的商品的名称
				select product from orders where price<100 group by product having sum(price)>150;
	
			

		~~sql语句书写顺序: 
			select from where groupby having orderby
		~~sql语句执行顺序:
			from where select group by having order by    


		
		~~备份恢复数据库
			备份:	在cmd窗口下 mysqldump -u root -p dbName>c:/1.sql
			恢复:	方式1:在cmd窗口下 mysql -u root -p dbName<c:/1.sql
				方式2:在mysql命令下, source c:/1.sql
				要注意恢复数据只能恢复数据本身,数据库没法恢复,需要先自己创建出数据后才能进行恢复.


二、多表设计多表查询
	1.外键约束
		表是用来保存显示生活中的数据的，而现实生活中数据和数据之间往往具有一定的关系，我们在使用表来存储数据时，可以明确的声明表和表之前的依赖关系，命令数据库来帮我们维护这种关系，向这种约束就叫做外键约束

	
		create table dept(
			id int primary key auto_increment,
			name varchar(20)
		);
		insert into dept values(null,'财务部'),(null,'人事部'),(null,'销售部'),(null,'行政部');

		create table emp(
			id int primary key auto_increment,
			name varchar(20),
			dept_id int,
			foreign key(dept_id) references dept(id)
		);
		insert into emp values(null,'奥巴马',1),(null,'哈利波特',2),(null,'本拉登',3),(null,'朴乾',3);

	2.多表设计
		一对多:在多的一方保存一的一方的主键做为外键
		一对一:在任意一方保存另一方的主键作为外键
		多对多:创建第三方关系表保存两张表的主键作为外键,保存他们对应关系

	3.多表查询
		笛卡尔积查询:
			将两张表的记录进行一个相乘的操作查询出来的结果就是笛卡尔积查询,如果左表有n条记录,右表有m条记录,笛卡尔积查询出有n*m条记录,其中往往包含了很多错误的数据,所以这种查询方式并不常用
			select * from dept,emp;
			
		内连接查询:查询的是左边表和右边表都能找到对应记录的记录
			select * from dept,emp where dept.id = emp.dept_id;
			select * from dept inner join emp on dept.id=emp.dept_id;
		
		外连接查询:
			左外连接查询:在内连接的基础上增加左边表有而右边表没有的记录
				select * from dept left join emp on dept.id=emp.dept_id;
			
			右外连接查询:在内连接的基础上增加右边表有而左边表没有的记录
				select * from dept right join emp on dept.id=emp.dept_id;
			全外连接查询:在内连接的基础上增加左边表有而右边表没有的记录和右边表有而左表表没有的记录
				select * from dept full join emp on dept.id=emp.dept_id; -- mysql不支持全外连接
				可以使用union关键字模拟全外连接:
				select * from dept left join emp on dept.id = emp.dept_id
				union
				select * from dept right join emp on dept.id = emp.dept_id;
