package com.zfeng.advancedanimation;

import android.annotation.TargetApi;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by zhaofeng on 2017/2/10.
 */

public class MainLActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_l);
    }

    public void imageLAnim(View view){
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP){
            ImageView imageView=(ImageView)view;
            AnimatedVectorDrawable drawable=(AnimatedVectorDrawable)getDrawable(R.drawable.bright_anim);
            imageView.setImageDrawable(drawable);
            drawable.start();
        }

    }
}
