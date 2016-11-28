package com.example.a5alumno.ejercicio9_backgroundmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_MAIN_ACTIVITY = MainActivity.class.getSimpleName();
    private ProgressBar mProgressbar;
    private static final String FINISH_THREAD= "Thread finished";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartThread = (Button)this.findViewById(R.id.btn_start_thread);
        btnStartThread.setOnClickListener(this);
        Button btnStartAsyncTask = (Button)this.findViewById(R.id.btn_start_async_task);
        btnStartAsyncTask.setOnClickListener(this);
        this.mProgressbar = (ProgressBar)this.findViewById(R.id.progressBar);
        this.mProgressbar.setProgress(0);

    }

    @Override
    public void onClick(View view) {
        mProgressbar.setProgress(0);
        if(view.getId()==R.id.btn_start_async_task){
            new MyAsyncTask(this).execute(1);
        }
        else if(view.getId()==R.id.btn_start_thread){
            new Thread(new Runnable(){

                @Override
                public void run() {
                    Log.i(MainActivity.TAG_MAIN_ACTIVITY,"Thread started");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext(), "Sleeping...", Toast.LENGTH_SHORT).show();
                        }
                    });
                    sleepForAWhile(10);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getApplicationContext() , FINISH_THREAD, Toast.LENGTH_SHORT).show();
                        }
                    });
                   //
                }

            }).start();
        }

    }

    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        private Context mContext;

        public MyAsyncTask(Context anyContext){
            this.mContext = anyContext;
        }
        @Override
        protected String doInBackground(Integer... params) {
            for(int idx=1;idx<=5;idx++){
                sleepForAWhile(params[0]);
                publishProgress(idx*20);
            }
            return "AsyncTask finished";
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            //((MainActivity)mContext).mProgressbar.setProgress(0);
            mProgressbar.setProgress(values[0]);
        }

        @Override
        protected void onPostExecute(String retString) {
            super.onPostExecute(retString);
            Toast.makeText(this.mContext, retString, Toast.LENGTH_SHORT).show();
        }
    }

    private void sleepForAWhile(int numSeconds){
        long endTime = System.currentTimeMillis() + (numSeconds *1000);

        while(System.currentTimeMillis()<endTime){
            synchronized (this){
                try{
                    Log.i(MainActivity.TAG_MAIN_ACTIVITY, "Sleeping...");
                    this.wait(endTime - System.currentTimeMillis());
                }catch(InterruptedException e){
                    e.printStackTrace();;
                }
            }
        }
    }
}



