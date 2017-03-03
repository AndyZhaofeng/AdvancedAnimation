package com.zfeng.pathmeasure;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void onDrawLine(View view){
        startActivity(new Intent(MainActivity.this,PathMeasure1Activity.class));
    }
    public void onPathDash(View view){
        startActivity(new Intent(MainActivity.this,PathDashActivity.class));
    }
    public void onPathTran(View view){
        startActivity(new Intent(MainActivity.this,PathTranActivity.class));
    }
}
