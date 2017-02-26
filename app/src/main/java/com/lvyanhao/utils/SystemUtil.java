package com.lvyanhao.utils;

import android.content.Context;
import android.telephony.TelephonyManager;

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
}
