package com.sangebaba.rxjava.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sangebaba.rxjava.application.AppApplication;

import butterknife.ButterKnife;


/**
 * @author zanyang
 *         created at 2016/12/16 10:45
 */

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        ButterKnife.bind(this);
    }

    protected abstract int getLayoutId();

}
