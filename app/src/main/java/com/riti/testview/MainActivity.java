package com.riti.testview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private WareView mWareView;
    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        MyView myView=findViewById(R.id.my_view);
//        myView.setDate(270,90);
//        mWareView = findViewById(R.id.wv);
//
//        new Thread() {
//            @Override
//            public void run() {
//                super.run();
//                try {
//                    for (int i = 0; i < 100; i++) {
//                        Log.i(TAG, "run: "+i);
//                        MainActivity.this.i = i;
//                        runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                mWareView.setProcess(MainActivity.this.i);
//                            }
//                        });
//                        sleep(1000);
//                    }
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        }.start();

    }
}
