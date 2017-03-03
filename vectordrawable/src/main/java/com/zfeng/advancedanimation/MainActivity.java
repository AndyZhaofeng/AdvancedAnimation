package com.zfeng.advancedanimation;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatDelegate;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    static{
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }
    public void imageAnim(View view){
        ImageView imageView=(ImageView)view;
        Drawable drawableColor = imageView.getDrawable();
        if (drawableColor instanceof Animatable) {
            ((Animatable) drawableColor).start();
        }
    }
    public void imageLAnim(View view){
        Intent intent=new Intent(MainActivity.this,MainLActivity.class);
        startActivity(intent);
    }
}
