package com.liompei.zlog;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Z.initLog("Aa",true);
        Z.d();
        Z.d("This is debug");
        Z.d("MyTag","This is debug");
    }
}
