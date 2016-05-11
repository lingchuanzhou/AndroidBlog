#Linux学习笔记
***
##SecureCRT联接主机失败排查方式：
	1.打开终端，登录root用户：su - root
	2.查看网卡信息：ifconfig -a
	3.比较HWaddr网卡信息与网络适配器中的MAC地址是否相符
	4.如果不符，则修改MAC地址
	5.如果MAC地址也没问题，那就重新设定ip地址：
			a：查看当前网卡信息：ifconfig -a
			b：启用ip：ifconfig eth1 up（如果IP未启用的话）
			b：修改IP地址：ifconfig eh1 192.168.56.101
	6.如果还连不上，则要检查ssh服务是否启动：
			a：重启ssh：service sshd restart
			b：查看重启是否成功：chkconfig

	7.

##如何停止一个服务进程：
* service iptables stop

##如何压缩文件
* 压缩指令：tar　-czvf　tard.tar.gz　sort.txt　tail.txt  
	参数解释：
	    a.压缩文件名(如tard.tar.gz)  
		b.被压缩文件1(如sort.txt)   
		c.被压缩文件2(如tail.txt) 
##如何解压缩文件
* 解压缩命令：tar　-xjvf　tard.tar.gz

##关机重启命令
* 关机命令
	a.init 0
	b.shutdown r,关机后立即重启
	c.shutdown h,关机后不重新启动
	d.shutdown now,立即关机
	e.halt关机后关闭电源
	f.reboot重新启动

##Vim编辑器的使用
	命令模式：
		a.进入：vi filename
	插入模式：
		b.输入：iao
	编辑模式：
		c.输入：“：”
###Vim常使用的命令集  
	1.直接退出vim,输入:q  
	2.编辑一个文件的步骤：  
		a.vi hello.txt  
		b.输入i  
		c.输入内容：hello  
		d.按ESC从编辑模式退出到命令模式  
		e.输入"：wq"保存文件
		f.输入cat hello.txt，查看hello.txt  
	3.若想继续编辑hello.txt
		a.vi hello.txt
		b.输入内容，之后操作与上相同
##用户组和组账户管理
	用户账户
		a.普通用户账户
		b.超级用户账户：root

	组账户
		a.私有组：创建用户时，没有指定组，则为私有组
		b.标准组：有指定，如Users，

	所有用户都可以在/etc/passwd文件中查找到
		每行账户包含如下信息：
			用户名：口令：用户标示号：组标示号：注释：宿主目录：命令解释器
			如：Chuanzhou:x:500:500:Chuanzhou:/home/Chuanzhou:/bin/bash

	所有用户组可以在/etc/group文件中查找到
		组名：组口令：gid：组成员
		root:x:0:root

	使用命令行工具管理账户
		useradd 用户名
		useradd –u（UID号）
		useradd –p（口令）
		useradd –g（分组）
		useradd –s（SHELL）
		useradd –d（用户目录）
		usermod –u（新UID）
		usermod –d（用户目录）
		usermod –g（组名）
		usermod –s（SHELL）
		usermod –p（新口令）
		usermod –l（新登录名）
		usermod –L (锁定用户账号密码)
		usermod –U (解锁用户账号)
		userdel 用户名 (删除用户账号)
		userdel –r 删除账号时同时删除目录
		su 用户名   更换用户，但还是原用户的操作环境
		su - 用户名 完全更换用户


	Sudo规则配置：
		root授权权限于某一用户

	文件权限管理：
		如：-rw-r--r-- 
		含义：   
			文件还是目录|所属组用户|其他用户|所有用户
			d：目录
			-：文件
			r：读
			w：写
			x：可执行
			
	查看文件权限命令：ls -l 文件名或ll
	增加权限：chmod +x t.txt
	减少权限：chmod +x t.txt
	
##crontab启用定时任务：
			命令符：minute hour day month weekday [username] cmd
			举例：
				a.30 2 * * 1 (cd /home/itcast/test; make)
					表示每周一凌晨2:30到/home/hexy/test目录中运行make命令
				b.55 23 * * 0-3,6 /home/itcast/backup.sh
					除了周四、周五之外，每天晚上11:55运行/home/itcast/backup.sh脚本
				c.问题：
					每隔两分钟发送一次当前登录用户数量给用户itcast如何实现？
					0-59/2 * * * * root (echo current date `date`>/tmp/count;echo the current user count is:`who | wc -l`>>/tmp/count;write itcast < /tmp/count)



##Linux网络
###（一）ISO/OSI七层模型
	MAC地址：是计算机的物理通信地址，即网卡，负责局域网通信，在物理层被封装
	IP地址：负责外网通信，在网络层被封装

* 1.物理层：设备之间的比特流的传输，物理接口，电气特性，网线，网卡
* 2.数据链路层：成帧，用MAC地址访问媒介，错误检测与修正
* 3.网络层：写入双方的IP地址、选路（选择经过哪些路由器）
* 4.传输层：确定传输协议（TCP/UDP）是否可靠，传输前的错误检测，流量监控，确定发送端口8080等
* 5.会话层：判断数据是否会能够进行网络传递
* 6.表示层：通过ASCII等将0101数据解码解密成文件，图片、视频等
* 7.应用层：提供用户传输的接口，浏览器（163.com）
* 总结：  
	1.应用层(写信)
	2.表示层(解码解密信件)
	3.会话层(判断信件是否可传输)
	4.传输层(确定传输协议TCP或UDP是否可靠，及端口号)
	5.网络层(发信人ip地址，收件人ip地址)
	6.数据链路层（确定传输到哪里的MAC地址）
	7.物理层(文件的比特流传输通道)
###（二）、TCP/IP协议四层
	应用层：对应OSI的应用层、表示层、会话层
	传输层：对应OSI的传输层		
	国际互联层：对应OSI的网络层		
	网络接口层：对应OSI的数据链路层、物理层
		arp用于地址协议解析，是IP地址与MAC地址的桥梁
		cmd中输入：arp -a
		接口: 192.168.0.101 --- 0xc
		 Internet 地址         物理地址              类型
		 192.168.0.1           8c-f2-28-29-e1-46     动态
		 192.168.0.100         64-9a-be-c0-f7-dd     动态
		 192.168.0.255         ff-ff-ff-ff-ff-ff     静态
		 224.0.0.22            01-00-5e-00-00-16     静态
		 224.0.0.252           01-00-5e-00-00-fc     静态
		 234.123.12.1          01-00-5e-7b-0c-01     静态
		 239.255.255.250       01-00-5e-7f-ff-fa     静态
		 255.255.255.255       ff-ff-ff-ff-ff-ff     静态
	TCP：阻塞式的响应，三次握手协议保证通信流畅
	UDP：实时响应
	TCP/IP 协议
	TCP/IP 是不同的通信协议的大集合。
	协议族
	TCP/IP 是基于 TCP 和 IP 这两个最初的协议之上的不同的通信协议的大集合。
	TCP - 传输控制协议
	TCP 用于从应用程序到网络的数据传输控制。
	TCP 负责在数据传送之前将它们分割为 IP 包，然后在它们到达的时候将它们重组。
	IP - 网际协议（Internet Protocol）
	IP 负责计算机之间的通信。
	IP 负责在因特网上发送和接收数据包。
	HTTP - 超文本传输协议(Hyper Text Transfer Protocol)
	HTTP 负责 web 服务器与 web 浏览器之间的通信。
	HTTP 用于从 web 客户端（浏览器）向 web 服务器发送请求，并从 web 服务器向 web 客户端返回内容（网页）。
	HTTPS - 安全的 HTTP（Secure HTTP）
	HTTPS 负责在 web 服务器和 web 浏览器之间的安全通信。
	作为有代表性的应用，HTTPS 会用于处理信用卡交易和其他的敏感数据。
	SSL - 安全套接字层（Secure Sockets Layer）
	SSL 协议用于为安全数据传输加密数据。
	SMTP - 简易邮件传输协议（Simple Mail Transfer Protocol）
	SMTP 用于电子邮件的传输。
	MIME - 多用途因特网邮件扩展（Multi-purpose Internet Mail Extensions）
	MIME 协议使 SMTP 有能力通过 TCP/IP 网络传输多媒体文件，包括声音、视频和二进制数据。
	IMAP - 因特网消息访问协议（Internet Message Access Protocol）
	IMAP 用于存储和取回电子邮件。
	POP - 邮局协议（Post Office Protocol）
	POP 用于从电子邮件服务器向个人电脑下载电子邮件。
	FTP - 文件传输协议（File Transfer Protocol）
	FTP 负责计算机之间的文件传输。
	NTP - 网络时间协议（Network Time Protocol）
	NTP 用于在计算机之间同步时间（钟）。
	DHCP - 动态主机配置协议（Dynamic Host Configuration Protocol）
	DHCP 用于向网络中的计算机分配动态 IP 地址。
	SNMP - 简单网络管理协议（Simple Network Management Protocol）
	SNMP 用于计算机网络的管理。
	LDAP - 轻量级的目录访问协议（Lightweight Directory Access Protocol）
	LDAP 用于从因特网搜集关于用户和电子邮件地址的信息。
	ICMP - 因特网消息控制协议（Internet Control Message Protocol）
	ICMP 负责网络中的错误处理。
	ARP - 地址解析协议（Address Resolution Protocol）
	ARP - 用于通过 IP 来查找基于 IP 地址的计算机网卡的硬件地址。
	RARP - 反向地址转换协议（Reverse Address Resolution Protocol）
	RARP 用于通过 IP 查找基于硬件地址的计算机网卡的 IP 地址。
	BOOTP - 自举协议（Boot Protocol）
	BOOTP 用于从网络启动计算机。
	PPTP - 点对点隧道协议（Point to Point Tunneling Protocol）
	PPTP 用于私人网络之间的连接（隧道）。
	IP地址：
		分类：
			A类地址：10.0.0.0～10.255.255.255 
			B类地址：172.16.0.0～172.31.255.255 
			C类地址：192.168.0.0～192.168.255.255

		00000000.00000000.00000000.00000000
		11111111.11111111.11111111.11111111
	
		0.0.0.0
		255.255.255.255
	子网掩码：用来区分不同网段的IP，掩码不同，主机就属于不同的网段，
	网段中的第一个地址是网络地址，最后一个地址是广播地址，
	ip和子网掩码进行与运算得出网络地址，

	网关作用：通俗的认为，区分内网和外网
	局域网内：通过交换机进行数据交换。交换机是低级设备，不认识Ip只识别mac，数据链路层设备
	网间或者不同网段：通过路由器（网关）进行数据交换。外网转内网，内网转外网
	网关实现网络的层次，网关之后还有网关
	内网不能直接访问内网
	作用：
	1）网关在所有内网计算机访问的不是本网段的数据包时使用，只要不是局域网内交换数据就一定要经过网关，即网关分开内网和外网
	2）把内网Ip转公网Ip（），公网Ip转内网Ip

	网络地址 = IP & 子网掩码
	广播地址 = 网络地址的主机位全为1
	
##SSH协议
	双钥加密：
		假设A要分享一个加密文件给B，如何用双钥进行加密传输？
		a.A设置一个密码，同时生成一个公钥A和私钥A
		b.B也生成一个密码和公钥B、私钥B
		c.A再用公钥A和B的公钥B将要传输的文件进行加密
		d.B只需要用自己的密码和私钥B就可以将文件进行解码
		e.同样的，若A也要解密这个被加密的文件，也需要用私钥A和自己的密码进行解密