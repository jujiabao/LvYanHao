package com.lvyanhao.utils;

import java.util.Date;
import java.util.regex.Pattern;

/**
 * Created by Hello.Ju on 17/2/23.
 */
public class RegExUtil {

    //邮箱正则
    private static final String REG_EMAIL = "\\w[-\\w.+]*@([A-Za-z0-9][-A-Za-z0-9]+\\.)+[A-Za-z]{2,14}";

    //手机号码正则
    private static final String REG_MOBILE = "0?(13|14|15|18)[0-9]{9}";

    //网址正则
    private static final String REG_URL = "^((https|http|ftp|rtsp|mms)?:\\/\\/)[^\\s]+";

    //用户名正则
    private static final String REG_USERNAME = "^[A-Za-z0-9_\\-]{6,15}$";

    //昵称
    private static final String REG_USERNICK = "^[A-Za-z0-9_\\-\\u4e00-\\u9fa5]{1,15}$";

    public static boolean regEmail(String str){
        Pattern pattern = Pattern.compile(REG_EMAIL);
        return pattern.matcher(str).matches();
    }

    public static boolean regMobile(String str){
        Pattern pattern = Pattern.compile(REG_MOBILE);
        return pattern.matcher(str).matches();
    }

    public static boolean regUrl(String str){
        Pattern pattern = Pattern.compile(REG_URL);
        return pattern.matcher(str).matches();
    }

    public static boolean regUsername(String str){
        Pattern pattern = Pattern.compile(REG_USERNAME);
        return pattern.matcher(str).matches();
    }

    public static boolean regUsernick(String str){
        Pattern pattern = Pattern.compile(REG_USERNICK);
        return pattern.matcher(str).matches();
    }

    public static void main(String[] args) {
        System.out.println(regEmail("694455391@qq.com"));
        System.out.println(regMobile("18651444434"));
        System.out.println(regUrl("http://www.baidu.com"));
        System.out.println(regUsername("694455391"));
        System.out.println(new Date().getTime());
    }

}
