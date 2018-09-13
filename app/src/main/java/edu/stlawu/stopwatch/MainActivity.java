package edu.stlawu.stopwatch;


import android.graphics.Color;
import android.os.Build;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    // Define variables for our views
    private TextView timer_text = null;
    private Button bt_start, bt_stop, bt_reset;
    private Handler handler;
    private long millisec_time, start_time, time_buff, update_time = 0L;
    private int milliseconds, seconds, minutes;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initialize views
        this.timer_text = findViewById(R.id.timer_text);
        this.bt_start = findViewById(R.id.bt_start);
        this.bt_stop = findViewById(R.id.bt_stop);
        this.bt_reset = findViewById(R.id.bt_reset);

        // create a handler
        handler = new Handler();

        // behaviors for start button
        this.bt_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                start_time = SystemClock.uptimeMillis();
                handler.postDelayed(runnable, 0);

                // Disable "Start" Button
                bt_start.setEnabled(false);
                bt_start.setBackgroundColor(Color.GRAY);

                // Enable "Stop" Button
                bt_stop.setEnabled(true);

            }
        });

        // behaviors for stop button
        this.bt_stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_buff = time_buff + millisec_time;
                handler.removeCallbacks(runnable);

                // Enable "Start" Buttons
                bt_start.setEnabled(true);
                bt_start.setText("Resume");
                bt_start.setBackgroundColor(0xff669900);

            }
        });

        // behaviors for reset button
        this.bt_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                time_buff = time_buff + millisec_time;
                handler.removeCallbacks(runnable);

                // Initiate everything to 0
                milliseconds = 0;
                seconds = 0;
                minutes = 0;
                millisec_time = 0L;
                start_time = 0L;
                time_buff = 0L;
                update_time = 0L;

                // Enable "Start" Buttons
                bt_start.setEnabled(true);
                bt_start.setText("Start");
                bt_start.setBackgroundColor(0xff669900);

                // Disable "Stop" Button
                bt_stop.setEnabled(false);

                // Initialize timer text to "00:00.0"
                timer_text.setText("00:00.0");

            }
        });


    }


    public Runnable runnable = new Runnable() {
        public void run() {
            millisec_time = SystemClock.uptimeMillis() - start_time;
            update_time = time_buff + millisec_time;
            seconds = (int) (update_time / 1000);
            minutes = seconds / 60;
            seconds = seconds % 60;
            milliseconds = (int) (update_time % 1000);


            timer_text.setText("" + minutes + ":"
                    + String.format("%02d", seconds) + "."
                    + String.format("%d", milliseconds));

            handler.postDelayed(this, 50);

        }
    };

}
