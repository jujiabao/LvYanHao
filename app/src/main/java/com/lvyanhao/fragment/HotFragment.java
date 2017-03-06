package com.lvyanhao.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lvyanhao.R;

/**
 * Created by lyh on 2017/3/5.
 */

public class HotFragment extends Fragment{
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View hotLayout = inflater.inflate(R.layout.fragment_hot,container,false);
        return hotLayout;
    }
}
