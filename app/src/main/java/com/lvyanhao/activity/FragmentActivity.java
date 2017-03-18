package com.lvyanhao.activity;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;
import com.lvyanhao.R;
import com.lvyanhao.fragment.HotFragment;
import com.lvyanhao.fragment.MainFragment;
import com.lvyanhao.fragment.MineFragment;

/**
 * Created by lyh on 2017/3/5.
 */

public class FragmentActivity extends Activity implements View.OnClickListener {

    private MainFragment mainFragment;

    private HotFragment hotFragment;

    private MineFragment mineFragment;

    private View listLayout;

    private View hotLayout;

    private View mineLayout;

    private ImageView mainImage;

    private ImageView hotImage;

    private ImageView mineImage;

    private TextView mainText;

    private TextView hotText;

    private TextView mineText;

    private FragmentManager fragmentManager;

    private TextView tv_titlename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.activity_fragment);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.title_common);
        initViews();
        fragmentManager = getFragmentManager();
        setTabSelection(0);
    }

    private void initViews(){
        listLayout = findViewById(R.id.fragment_main);
        hotLayout = findViewById(R.id.fragment_hot);
        mineLayout = findViewById(R.id.fragment_mine);
        mainImage = (ImageView)findViewById(R.id.main_image);
        hotImage = (ImageView)findViewById(R.id.hot_image);
        mineImage = (ImageView)findViewById(R.id.mine_image);
        mainText = (TextView)findViewById(R.id.main_text);
        hotText = (TextView)findViewById(R.id.hot_text);
        mineText = (TextView)findViewById(R.id.mine_text);
        listLayout.setOnClickListener(this);
        hotLayout.setOnClickListener(this);
        mineLayout.setOnClickListener(this);
        tv_titlename = (TextView) findViewById(R.id.title_name);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fragment_main:
                setTabSelection(0);
                break;
            case R.id.fragment_hot:
                setTabSelection(1);
                break;
            case R.id.fragment_mine:
                setTabSelection(2);
                break;
        }
    }

    //����ѡ��״̬
    private void setTabSelection(int index) {
        //����ϴ�ѡ��״̬
        clearSelection();
        //����fragment����
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        hideFragments(transaction);

        switch (index) {
            case 0:
                // ���������ҳtabʱ���ı�ؼ���ͼƬ��������ɫ
                tv_titlename.setText("��ӳ/��ӳ");
                mainImage.setImageResource(R.drawable.fragmeng_main);
                mainText.setTextColor(Color.RED);
                if (mainFragment == null) {
                    // ���MessageFragmentΪ�գ��򴴽�һ������ӵ�������
                    mainFragment = new MainFragment();
                    transaction.add(R.id.content, mainFragment);
                } else {
                    // ���MessageFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
                    transaction.show(mainFragment);
                }
                break;
            case 1:
                // ��������ȵ�tabʱ���ı�ؼ���ͼƬ��������ɫ
                tv_titlename.setText("�㳡");
                hotImage.setImageResource(R.drawable.fragment_hot);
                hotText.setTextColor(Color.RED);
                if (hotFragment == null) {
                    // ���ContactsFragmentΪ�գ��򴴽�һ������ӵ�������
                    hotFragment = new HotFragment();
                    transaction.add(R.id.content, hotFragment);
                } else {
                    // ���ContactsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
                    transaction.show(hotFragment);
                }
                break;
            case 2:
                // ������˸���tabʱ���ı�ؼ���ͼƬ��������ɫ
                tv_titlename.setText("�ҵ�");
                mineImage.setImageResource(R.drawable.fragment_mine);
                mineText.setTextColor(Color.RED);
                if (mineFragment == null) {
                    // ���NewsFragmentΪ�գ��򴴽�һ������ӵ�������
                    mineFragment = new MineFragment();
                    transaction.add(R.id.content, mineFragment);
                } else {
                    // ���NewsFragment��Ϊ�գ���ֱ�ӽ�����ʾ����
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commit();
    }

    private void clearSelection() {
        mainImage.setImageResource(R.drawable.unfragment_main);
        mainText.setTextColor(Color.parseColor("#D0D0D0"));
        hotImage.setImageResource(R.drawable.unfragment_hot);
        hotText.setTextColor(Color.parseColor("#D0D0D0"));
        mineImage.setImageResource(R.drawable.unfragemnt_mine);
        mineText.setTextColor(Color.parseColor("#D0D0D0"));
    }
    private void hideFragments(FragmentTransaction transaction) {
       if (mainFragment != null) {
            transaction.hide(mainFragment);
        }
        if (hotFragment != null) {
            transaction.hide(hotFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }
}


