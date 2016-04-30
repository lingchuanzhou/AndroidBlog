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
	       printf("主函数中获取i的地址为%#x\n", mainp);
	       
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



