package com.example.a5alumno.ejercicio9_backgroundmanager;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG_MAIN_ACTIVITY = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnStartThread = (Button)this.findViewById(R.id.btn_start_thread);
        btnStartThread.setOnClickListener(this);
        Button btnStartAsyncTask = (Button)this.findViewById(R.id.btn_start_async_task);
        btnStartThread.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

    }

    public class MyAsyncTask extends AsyncTask<Integer, Integer, String> {
        private Context mContext;
        @Override
        protected String doInBackground(Integer... params) {
            for(int idx=1;idx<=5;idx++){
                sleepForAWhile(params[0]);
            }
            return "AsyncTask finished";
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



