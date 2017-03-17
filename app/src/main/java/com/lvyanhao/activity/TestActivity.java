package com.lvyanhao.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.lvyanhao.R;
import com.lvyanhao.utils.NetUtil;

/**
 * Created by Hello.Ju on 17/3/13.
 */
public class TestActivity extends Activity {

    private ImageView img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        img = (ImageView) findViewById(R.id.comment_avator);
    }
}
