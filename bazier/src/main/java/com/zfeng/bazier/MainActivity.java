package com.zfeng.bazier;

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
    public void onBazierView(View view){
        startActivity(new Intent(MainActivity.this,BazierViewActivity.class));
    }
    public void onBazierAnimator(View view){
        startActivity(new Intent(MainActivity.this,BazierAnimatorActivity.class));
    }
    public void onWaverAnimator(View view){
        startActivity(new Intent(MainActivity.this,WaveActivity.class));
    }
    public void moveOnBezier(View vew){
        startActivity(new Intent(MainActivity.this,MoveOnBezierActivity.class));
    }
}
