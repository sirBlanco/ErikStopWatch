package com.hfad.erikstopwatch;

import android.app.Activity;
import android.os.Bundle;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import java.util.Locale;

public class StopwatchActivity extends Activity {

    private int seconds = 0;
    private boolean running;
  /*  wasRunning, to record whether
    the stopwatch was running before the onStop() method
    was called so that we know whether to set it running
    again when the activity becomes visible again.*/
    private boolean wasRunning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stopwatch);
        if(savedInstanceState != null ){
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            //restore the state of the wasRunning variable if the activity is recreated.
            wasRunning =savedInstanceState.getBoolean("wasRunning");
        }
        runTimer();
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running",running);
        savedInstanceState.putBoolean("wasRunning",wasRunning);
    }

    @Override
    protected void onPause(){
        super.onPause();
        //Record whether the stopwatch was running when the onStop() method was called.
        wasRunning = running;
        running = false;
    }
    @Override
    protected void onResume(){
        super.onResume();
        if(wasRunning)
            running = true;
    }

    //Start the stopwatch when start button is clicked
    public void onClickStart(View view){

        running = true;
    }

    //Stop the stopwatch when the stop button is clicked
    public void onClickStop(View view){

        running = false;
    }

    // Reset the stopwatch when the Reset button is clicked.
    public void onClickReset(View view){
        running = false;
        seconds = 0;
    }

    private void runTimer(){
        final TextView timeView = (TextView) findViewById(R.id.time_view);
        final Handler handler = new Handler();

        //Call the post() method, passing in a new Runnable. The post()
        //method processes code without a delay, so the code in the
        //Runnable will run almost immediately.
        handler.post(new Runnable() {
            @Override
            public void run() {
                int hours = seconds/3600;
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),
                        "%d:%02d:%02d", hours,minutes,secs);
                timeView.setText(time);
                if(running){
                    seconds++;
                }
                handler.postDelayed(this, 1000);
            }
        });

    }

}
