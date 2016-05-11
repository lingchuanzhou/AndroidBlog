#C语言入门:
		安排:
				基本数据类型
				基本输入输出函数
				数组
				指针
				结构体
				枚举
***
##1.初识C语言:
    #include <stdio.h> //standard input output：标准输入输出函数头  
    #include <stdlib.h>  
    main(){
       printf("小志的逗比精神永存\n");
       system("pause"); 
    }
***
##2.如何用C语言调用java程序
    #include <stdio.h>
    #include <stdlib.h>

	main(){
	       printf("hello from c\n"); 
	       
	       //表示编译Hello.class
	       system("java Hello");
	       
	       system("pause"); 
	}
* 其中Hello.class内容为:  
    public class Hello      
{  
			public static void main(String[] dd){  
				System.out.println("hello from java");  
		 }  
		}

//打印结果：  
    hello from c  
    hello from java
***
##3.C语言基本数据类型

	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       //C语言有8中数据类型：
	       char, int, float, double, long, short, signed, unsigned
	       
	       int i =3;
	       char c = 'a';
	       long long ago = 12314324;
	       short int i2 = 2;
	       long int i3 = 3243;
	       //printf("i的值为%d\n", i);
	       
	       //sizeof表示数据类型的长度
	       printf("char的长度:%d\n", sizeof(char));
	       printf("int的长度:%d\n", sizeof(int));
	       printf("float的长度:%d\n", sizeof(float));
	       printf("double的长度:%d\n", sizeof(double));
	       printf("long的长度:%d\n", sizeof(long));
	       printf("short的长度:%d\n", sizeof(short));
	       printf("long long的长度:%d\n", sizeof(long long));
	       printf("short int的长度:%d\n", sizeof(short int));
	       printf("long int的长度:%d\n", sizeof(long int));
	       
	       
	       system("pause"); 
	}
>
* java的基本数据类型长度
	* byte：1
	* short：2
	* int：4
	* long：8
	* boolean：1
	* char：2
	* float：4
	* double：8
>
* C的基本数据类型长度
	* short：2
	* int：4
	* long：4
	* char：1
	* float：4
	* double：8

>总结：char和long的长度不同  
>
___
##4.输出函数

	#include <stdio.h>
	#include <stdlib.h>
	/*
	%d  -  int
	%ld – long int
	%hd – 短整型
	%c  - char
	%f -  float
	%lf – double
	%u – 无符号数
	%x – 十六进制输出 int 或者long int 或者short int
	%o -  八进制输出
	%s – 字符串
	*/
	main(){
	       int i = -3;
	       long l = 34567;
	       char c = 'a';
	       float f = 3.1466666666;
	       double d = 3.1466666666;
	       char arr[] = "小志的二逼精神永存";
	       printf("%c\n", c);
	       printf("%d\n", i);
	       printf("%f\n", f);
	       printf("%lf\n", d);
	       printf("%u\n", i);
	       printf("%#x\n", l);
	       printf("%#o\n", l);
	       printf("%s\n", arr);
	       system("pause"); 
	}
***
##5.输入函数
	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       printf("请输入班级人数:\n");
	       int number;
	       //&：表示取出number的内存地址 
	       scanf("%d", &number);
	       
	       printf("请输入班级名称:\n");
	       char name[10];
	       scanf("%s", &name);
	      
	       printf("number的地址%#x\n", &number);
	       printf("name的地址%#x\n", &name);
	       printf("班级人数为%d，名称为%s\n", number, name); 
	       system("pause"); 
	}
***
##6.指针入门
	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       int i;
	       i = 3;
		   //&i表示取出i的地址
	       printf("%#x\n", &i);
	       system("pause"); 
	}

##7.指针中*号的使用
	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       int i = 3;
	       //定义一个保存int类型数据的地址的指针变量p ，该变量的值是一个内存地址 
	       int* p = &i;

		   //定义一个保存p变量的指针变量q，q中存放的是p的内存地址
	       int** q = &p;
	       
		   //打印i的内存地址p
	       printf("i的地址%#x\n", p);

	       //通过*p取出i的值
	       printf("i的值为%d\n", *p);
		   
		   //通过**q取出p的值
	       printf("i的值为%d\n", **q);
	       system("pause"); 
	}


### "*"的三种用法
>
1. 乘法
2. int* p：定义一个指针变量p，p中存放一个内存地址，这个地址所存放的数据规定是int型
3. *p：取出p中保存的内存地址存放的数据

***

##8.指针使用的常见错误
	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       //野指针：没有赋值的指针 
	       int* p;
	       int i;
	       double d = 3.14;
	       p = &d; //不能把不同数据类型的指针进行相互赋值
	       //*p = 23;野指针可以查看，但是不要去赋值 
	       printf("%#x\n", p);
	       printf("%lf\n", *p);
	       system("pause"); 
	}
***
##9.值传递和引用传递

	#include <stdio.h>
	#include <stdlib.h>
	//这里的两个参数必须是指针类型数据，否则的话修改的就是swap函数里的p，q
	//类似于java，一个不同的方法会开辟不同的内存空间存放不同的变量
	void swap(int* p, int* q){
	     int temp = *p;
	     *p = *q;
	     *q = temp;
	}
	main(){
	       int i = 3;
	       int j = 5;
	       printf("i=%d\n", i);
	       printf("j=%d\n", j);
		   //将i，j的内存地址传递进去
	       swap(&i, &j);
	       printf("i=%d\n", i);
	       printf("j=%d\n", j);
	       system("pause"); 
	}

***
##10.返回多个值
>在Java中我们如果要返回值，需要指定返回值的数据类型，返回多个值需要依靠返回对象，或者返回数组，而在C语言中则无需指定返回值的类型，变量的值就被改变。

	#include <stdio.h>
	#include <stdlib.h>
	void function(int* p, int* q){
	     *p += 15;
	     *q += 15;
	}
	main(){
	       int i = 10;
	       int j = 20;
	       function(&i, &j);
	       printf("%d\n", i);
	       printf("%d\n", j);
	       system("pause"); 
	}
***
##11.如何在主函数中引用子函数的变量的内存地址？
>1.首先在主函数中定义一个指针变量  
>2.将该指针变量的地址，注意是地址，而不是内容，传递到子函数的指针参数中。  
>3.子函数中再将某变量的内存地址，赋值给指针参数  
>4.如果要获取子函数某变量的值，有一定的困难，因为和Java一样，方法中的变量用完就会被回收掉。

	#include <stdio.h>
	#include <stdlib.h>
	void function(int** p){
	     int i = 3;
	     printf("i的地址为%#x\n", &i);
	     *p = &i;
	}
	main(){
	       int* mainp;
		   //这里要传递mainp的地址，而不是mainp，否则会出现野指针，无法引用
	       function(&mainp);
			//下面这行不被注释的话，就无法及时打印i的值，因为会被系统回收掉 
	       //printf("主函数中获取i的地址为%#x\n", mainp);
	       
	       //数据幻影，和Java中一样，
	       printf("主函数中获取i的值为%d\n", *mainp);
	       system("pause"); 
	}

***

##12.数组入门
>1.C语言中数组的定义和Java中数组的定义没有太大的区别  
>2.C语言中数组名称地址和数组中第一个元素的地址相同
>3.可以将数组的地址复制给一个指针变量，指针变量可以进行运算

	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       //char arr[] = "hello"; 
	       int arr[] = {1,2,3,4,5};
	       
	       printf("%#x\n", &arr[0]);
	       printf("%#x\n", &arr[1]);
	       printf("%#x\n", &arr[2]);
	       printf("%#x\n", &arr[3]);
	       
	       printf("数组名字的地址%#x\n", &arr);
	       
	       //char* p = &arr;
	       int* p = &arr;
	       //+1表示向右偏移一个单位（int为4个字节，char为1个字节，以此类推）
	       printf("%d\n", *(p+0));
	       printf("%d\n", *(p+1));
	       printf("%d\n", *(p+2));
	       printf("%d\n", (p+2)-p); 
	       system("pause"); 
	}
##13.指针的长度

	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       char* cp;
	       int* ip;
	       short* lizhip;
	       int i;
	       char c;
	       cp = &c;
	       ip = &i;
	       //指针的长度都是4个字节 
	       printf("%d\n", sizeof(cp));
	       printf("%d\n", sizeof(ip));
	       printf("%d\n", sizeof(lizhip));
	       printf("%d\n", cp-ip);//不同数据类型的指针运算没有意义
	       system("pause"); 
	}
***
##14.堆和栈的概念
* （1） 从静态存储区域分配。内存在程序编译的时候就已经分配好，这块内存在程序的整个运行期间都存在。例如全局变量，static 变量。
* （2） 在栈上创建。在执行函数时，函数内局部变量的存储单元都可以在栈上创建，函数执行结束时这些存储单元自动被释放。栈内存分配运算内置于处理器的指令集中，效率很高，但是分配的内存容量有限。
* （3） 从堆上分配，亦称动态内存分配。程序在运行的时候用malloc 或new 申请任意多少的内存，程序员自己负责在何时用free 或delete 释放内存。动态内存的生存期由我们决定，使用非常灵活，但问题也最多.
  堆和栈的区别:
* 1.申请方式

栈:   
由系统自动分配.例如,声明一个局部变量int  b; 系统自动在栈中为b开辟空间.例如当在调用涵数时，需要保存的变量，最明显的是在递归调用时，要系统自动分配一个栈的空间，后进先出的，而后又由系统释放这个空间.  

堆:   
需要程序员自己申请，并指明大小，在c中用malloc函数   
如char*  p1  =  (char*) malloc(10);   //14byte
但是注意p1本身是在栈中的.      

* 2  申请后系统的响应   
 栈：只要栈的剩余空间大于所申请空间，系统将为程序提供内存，否则将报异常提示栈溢出。   
堆：首先应该知道操作系统有一个记录空闲内存地址的链表，当系统收到程序的申请时，    会遍历该链表，寻找第一个空间大于所申请空间的堆结点，然后将该结点从空闲结点链表中删除，并将该结点的空间分配给程序，另外，对于大多数系统，会在这块内存空间中的首地址处记录本次分配的大小，这样，代码中的delete语句才能正确的释放本内存空间。另外，由于找到的堆结点的大小不一定正好等于申请的大小，系统会自动的将多余的那部分重新放入空闲链表中。   
 
* 3.申请大小的限制   
栈：在Windows下,栈是向低地址扩展的数据结构，是一块连续的内存的区域。这句话的意思是栈顶的地址和栈的最大容量是系统预先规定好的，在WINDOWS下，栈的大小是2M（vc编译选项中可以设置,其实就是一个STACK参数,缺省2M），如果申请的空间超过栈的剩余空间时，将提示overflow。因此，能从栈获得的空间较小。   
堆：堆是向高地址扩展的数据结构，是不连续的内存区域。这是由于系统是用链表来存储的空闲内存地址的，自然是不连续的，而链表的遍历方向是由低地址向高地址。堆的大小受限于计算机系统中有效的虚拟内存。由此可见，堆获得的空间比较灵活，也比较大。 
 
* 4.申请效率的比较：   
栈:由系统自动分配，速度较快。但程序员是无法控制的。   
堆:由malloc/new分配的内存，一般速度比较慢，而且容易产生内存碎片,不过用起来最方便.   
 
*  5.堆和栈中的存储内容   
栈:在函数调用时，第一个进栈的是主函数中后的下一条指令（函数调用语句的下一条可执行语句）的地址，然后是函数的各个参数，在大多数的C编译器中，参数是由右往左入栈的，然后是函数中的局部变量。注意静态变量是不入栈的。   
当本次函数调用结束后，局部变量先出栈，然后是参数，最后栈顶指针指向最开始存的地址，也就是主函数中的下一条指令，程序由该点继续运行。   
堆：一般是在堆的头部用一个字节存放堆的大小。堆中的具体内容有程序员安排。   
 
* 6.内存的回收
栈上分配的内存，编译器会自动收回；堆上分配的内存，要通过free来显式地收回,否则会造成内存泄漏。
  堆和栈的区别可以用如下的比喻来看出：   
使用栈就像我们去饭馆里吃饭，只管点菜（发出申请）、付钱、和吃（使用），吃饱了就走，不必理会切菜、洗菜等准备工作和洗碗、刷锅等扫尾工作，他的好处是快捷，但是自由度小。   
使用堆就像是自己动手做喜欢吃的菜肴，比较麻烦，但是比较符合自己的口味，而且自由度大。

***
##15.动态内存分配
	#include <stdio.h>
	#include <stdlib.h>
	#include <malloc.h>//此处要导包 
	
	main(){
	       int i = 3;
	       int arr[10];
	       //申请10个字节的堆内存赋值给整型指针变量p 
	       int* p = malloc(sizeof(int) * 10); 
	       //为指针变量赋值 
	       *p = 10;
	       *(p+1) = 20;
	       *(p+2) = 30; 
	      
	       printf("%#x\n", p);      
	       printf("%d\n", *p);
	        //释放内存 
	       free(p);
	       system("pause"); 
	}
	
***
##16.学生管理系统

	#include <stdio.h>
	#include <stdlib.h>
	#include <malloc.h>
	main(){
	       printf("请输入学生人数:");
	       int count;
	       scanf("%d", &count);
	       
	       //根据学生人数去动态申请堆内存 
	       int* p = malloc(sizeof(int) * count);
	       
	       int i;
	       for(i = 0; i < count; i++){
	             printf("请输入第%d个学生学号：", i);
	             scanf("%d", p+i);
	       }
	       
	       printf("请输入新增的学生人数：");
	       int newCount;
	       scanf("%d", &newCount);
	       p = realloc(p, sizeof(int) * (count + newCount));
	       
	       for(i = count; i < count + newCount; i++){
	             printf("请输入第%d个学生学号：", i);
	             scanf("%d", p+i);
	       }
	       
	       for(i = 0; i < count + newCount; i++){
	            printf("第%d个学生的学号是：%d\n", i, *(p+i)); 
	       }
	       system("pause"); 
	}
***
##17.多级指针
>要明白指针变量不仅可以存放指针，而且其自身也是一个地址引用

	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       int i = 3;
	       int* p = &i;
	       int** q = &p;
	       int*** l = &q;
	       
		   //以下3中输出方式打印的结果都是3
	       printf("i的值为%d\n", ***l);
		   printf("i的值为%d\n", **q);
		   printf("i的值为%d\n", *p);

	       system("pause"); 
	}

***
##18.结构体
	#include <stdio.h>
	#include <stdlib.h>
	void study(){
	     printf("吃饭睡觉打李志\n");
	}
	//定义一个结构体 
	struct student{
	       int age;
	       int height;
	       char sex;
	       
	       //结构体中不能定义函数，但可以定义函数指针
	       void (*studyP)(); 
	}
	
	main(){
	       //创建结构体的变量 
	       struct student st = {20, 180, 'm', study};
	       printf("%d\n", st.age);//20
	       printf("结构体的长度%d\n", sizeof(st));
	       //12，尽管char为1个字节，但是为了方便做位移运算，会补齐char的长度 
	       
	       //调用函数指针有三种写法 
	       //第1种： 
	       st.studyP();
	       //第2种 
	       //定义一个指针变量stp，stp指向st 
	       struct student* stp = &st;
	       //取出指针变量的内容：student ，调用结构体中的函数指针 
	       (*stp).studyP();
	       
	       //第3种 ：直接用->符号引用 
	       stp->studyP();
	       system("pause"); 
	}

***
##19.联合体
	#include <stdio.h>
	#include <stdlib.h>
	
	main(){
	       //定义一个联合体 
	       union{long long i; short s; char c} un;
	       un.i = 3;
	       printf("%d\n", un.i);
	       //联合体的长度为其最长数据类型的长度 
	       printf("联合体的长度%d\n", sizeof(un));
	       system("pause"); 
	}
***
##20.枚举
	#include <stdio.h>
	
	enum WeekDay
	{
	Monday = 10,Tuesday,Wednesday,Thursday,Friday,Saturday,Sunday
	};
	
	int main(void)
	{
	  //定义day为Sunday;
	  enum WeekDay day = Sunday;
	  printf("%d\n",day);//16
	  system("pause");
	  return 0;
	}
***
##21.自定义类型
	#include <stdio.h>
	#include <stdlib.h>
	//关键字typeef 
	typedef int haha;
	main(){
	       haha i = 3;
	       printf("%d\n", i);
	       system("pause"); 
	}
***
##22.JNI.h



