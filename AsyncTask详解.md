#AsyncTask
	package com.example.asynctask;
	import android.os.AsyncTask;  
	import android.widget.ProgressBar;  
	import android.widget.TextView;  
	  
>生成该类的对象，并调用execute方法之后  
	首先执行的是onProExecute方法  
	 其次执行doInBackgroup方法  
	 
##AsyncTask的三个参数之间的传递关系：  
* Params：存在于**doInBackground**方法中，可用于return result到onPostExecute
* Progress:存在于**publishProgress**与**onProgressUpdate**中
* result：存在于**doInBackground**与**onPostExecute**中

	
##具体案例：
	public class ProgressBarAsyncTask extends AsyncTask<Integer, Integer, String> {  
  
    private TextView textView;  
    private ProgressBar progressBar;  
      
      
    public ProgressBarAsyncTask(TextView textView, ProgressBar progressBar) {  
        super();  
        this.textView = textView;  
        this.progressBar = progressBar;  
    }  
    /********第1步：准备工作：当asyncTask.execute(1000);被调用后触发此方法********/
    //该方法运行在UI线程当中,并且运行在UI线程当中 可以对UI空间进行设置  
    @Override  
    protected void onPreExecute() {  
        textView.setText("开始执行异步线程");  
    }  
  
  	/***************第2步：执行耗时操作*****************/
    /**  
     * 这里的Integer参数对应AsyncTask中的第一个参数 params
     * 这里的String返回值对应AsyncTask的第三个参数  result
     * 该方法并不运行在UI线程当中，主要用于异步操作，所以在该方法中不能对UI当中的空间进行设置和修改  
     * 但是可以调用publishProgress方法触发onProgressUpdate对UI进行操作  
     */  
    @Override  
    protected String doInBackground(Integer... params) {  
        NetOperator netOperator = new NetOperator();  
        int i = 0;  
        for (i = 10; i <= 100; i+=10) {  
        	//执行10次，停顿10秒
            netOperator.operator();
  	/***************第3步：发送进度*****************/   
            publishProgress(i);  
        }  
        //执行完毕，返回result
        return i + params[0].intValue() + "";  
    }  
  
    /************第4步：更新执行进度******************/
    /**  
     * 这里的Integer参数对应AsyncTask中的第二个参数，即progress
     * 在doInBackground方法当中，，每次调用publishProgress方法都会触发onProgressUpdate执行  
     * onProgressUpdate是在UI线程中执行，所以可以对UI空间进行操作  
     */  
    @Override  
    protected void onProgressUpdate(Integer... progress) {  
        int vlaue = progress[0];  
        progressBar.setProgress(vlaue);  
    }   
      
    /************第5步：后台任务执行结束后的操作******************/
    /**  
     * 这里的String参数对应AsyncTask中的第三个参数（也就是接收doInBackground的返回值）  
     * 在doInBackground方法执行结束之后在运行，并且运行在UI线程当中 可以对UI空间进行设置  
     */  
    @Override  
    protected void onPostExecute(String result) {  
        textView.setText("异步操作执行结束" + result);  
    	}  
	}  