

#多站点虚拟主机的配置方式：
	1.打开httpd-vhosts.conf,F:\WorkSpace\wamp\bin\apache\
	apache2.4.9\conf\extra\httpd-vhosts.conf，往里面添加VirtualHost标签:
		<VirtualHost *:80>
		    DocumentRoot "F:/PHP/test01"
		    ServerName test01.com
		</VirtualHost>
	
	2.打开httpd.conf文件，F:\WorkSpace\wamp\bin\apache\apache2.4.9\conf\httpd.conf，将#Include conf/extra/httpd-vhosts.conf去掉注释

	3.继续在httpd.conf文件进行修改：
		a.将<Directory/>标签中的内容修改成：
						<Directory />
					    Options FollowSymLinks
					    AllowOverride None
					    Order deny,allow
						# Deny from all
					    Allow from all
						#允许所有访问
					    Satisfy all
						</Directory>
		b.找到<Directory "F:\PHP">标签，在#onlineoffline tag - 
		don't remove后面添加：
					    Order Deny,Allow
					    Allow from all
					    #Allow from 127.0.0.1
	4.在F:/PHP/test01文件夹下添加index.php，然后再浏览器中访问test01.com
##VirtualHost标签介绍
	<VirtualHost *:80>
	    ServerAdmin webmaster@dummy-host2.example.com//设置管理员邮箱地址
	    DocumentRoot "c:/Apache24/docs/dummy-host2.example.com"//文件目录，指向网站的代码放的目录
	    ServerName dummy-host2.example.com//主机名
	    ErrorLog "logs/dummy-host2.example.com-error.log"//错误日志
	    CustomLog "logs/dummy-host2.example.com-access.log" common//日常日志
	</VirtualHost>