#ListView详解
***
##ListView的优化：内存优化
###1.ConverView的优化
	A.优化原因：inflate()的反复被调用
			用户不断的上下滚屏的过程中，会不断的调用getView方法，
			因此就会不断的调用inflate方法创造View对象，最终可能会造成内存溢出。
	B.优化步骤：
			A:将getView的返回值改为convertView
		  	B:判断：如果没有列表项对象的缓存，就将inflate方法得到的对象引用给convertView，
			否则：直接使用convertView。
		 	
	C.关键代码：
			a:创建一个HoldView类,将需要findViewById的控件添加为其成员变量
			b:在if语句中实例化HoldView，并通过findViewById将其成员变量进行实例化
			c:然后通过convertView.setTag(holderView);将对象添加到convertView一起缓存
			d:当使用缓存时，调用convertView.getTag()，把holderView再取出来
			e:然后通过set/get方法设置成员变量,即控件的各种属性。

###2.ViewHolder的优化
	a.优化原因：findViewById()反复被调用
	b.优化步骤：
				A:
