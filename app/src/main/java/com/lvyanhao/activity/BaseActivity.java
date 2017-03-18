package com.lvyanhao.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.KeyEvent;

import com.lvyanhao.R;
import com.lvyanhao.layout.SwipeBackLayout;

/**
 * Created by Hello.Ju on 17/3/18.
 */
public class BaseActivity extends Activity {

    /**
     * 按键监控
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            finish();
            overridePendingTransition(R.anim.in_from_left, R.anim.out_to_right);
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 侧滑时，可以关闭当前activity
     */
    @Override
    protected void onStart() {
        super.onStart();
        SwipeBackLayout swipeBackLayoutTwo = (SwipeBackLayout) findViewById(R.id.swipe_layout_two);
        swipeBackLayoutTwo.setCallBack(new SwipeBackLayout.CallBack() {
            @Override
            public void onFinish() {
                finish();
            }
        });
    }
}
