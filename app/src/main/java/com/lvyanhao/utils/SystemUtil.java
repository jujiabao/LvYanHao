package com.lvyanhao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.telephony.TelephonyManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.MODE_PRIVATE;
import static android.content.Context.TELEPHONY_SERVICE;

/**
 * Created by lyh on 2017/2/26.
 */

public class SystemUtil {

    private Context mContext;

    private TelephonyManager tm;

    public SystemUtil(Context context) {
        this.mContext = context;
        tm = (TelephonyManager) mContext.getSystemService(TELEPHONY_SERVICE);
    }

    /**
     * 获取IMEI号码
     * @return
     */
    public String getIMEI(){
        return tm.getDeviceId();
    }

    /**
     * 获取号码
     * @return
     */
    public String getMobile(){
        return tm.getLine1Number();
    }

    /**
     * 获取国家信息
     * @return
     */
    public String getCounty(){
        return tm.getSimCountryIso();
    }

    public static void setListViewHeightBasedOnChildren(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            // pre-condition
            return;
        }

        int totalHeight = 0;
        for (int i = 0; i < listAdapter.getCount(); i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }

        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    public static String getTokenValueFromSP(Context context){
        SharedPreferences settings= context.getSharedPreferences("TOKEN", MODE_PRIVATE);
        return settings.getString("token","");
    }

    public static Map<String,Object> getIpAndPortFromSP(Context mContext) {
        //拿出设置的数据
        SharedPreferences settings= mContext.getSharedPreferences("setting_net", 0);
        String ip = settings.getString("ip","");
        int port = settings.getInt("port", 0);
        Map<String, Object> map = new HashMap<>();
        map.put("ip", ip);
        map.put("port",port);
        return map;
    }
}
