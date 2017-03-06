package com.lvyanhao.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lvyanhao.R;

/**
 * Created by lyh on 2017/3/5.
 */

public class MineFragment extends Fragment {
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View MineLayout = inflater.inflate(R.layout.fragment_mine,container,false);
        return MineLayout;
    }
}
