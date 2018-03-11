package com.wizardev.handleranalysis;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button mStartTask, mTimeCycle,mStopCycle;
    private Runnable mRunnable;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                Toast.makeText(MainActivity.this, "刷新UI、", Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        mStartTask = findViewById(R.id.btn_start_task);
        mStartTask.setOnClickListener(this);
        mTimeCycle = findViewById(R.id.btn_time_cycle);
        mTimeCycle.setOnClickListener(this);
        mStopCycle = findViewById(R.id.btn_stop_cycle);
        mStopCycle.setOnClickListener(this);


        mRunnable = new Runnable() {
            @Override
            public void run() {
                Toast.makeText(MainActivity.this, "正在循环！！！", Toast.LENGTH_SHORT).show();
                mHandler.postDelayed(mRunnable, 1000);
            }
        };
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_task:
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(1000);
                            mHandler.sendEmptyMessage(1);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
                break;
            case R.id.btn_time_cycle:
                mHandler.post(mRunnable);
                break;
            case R.id.btn_stop_cycle:
                mHandler.removeCallbacks(mRunnable);
                break;
        }
    }
}
