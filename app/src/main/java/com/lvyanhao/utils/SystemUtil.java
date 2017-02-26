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
     * ��ȡIMEI����
     * @return
     */
    public String getIMEI(){
        return tm.getDeviceId();
    }

    /**
     * ��ȡ����
     * @return
     */
    public String getMobile(){
        return tm.getLine1Number();
    }

    /**
     * ��ȡ������Ϣ
     * @return
     */
    public String getCounty(){
        return tm.getSimCountryIso();
    }
}
