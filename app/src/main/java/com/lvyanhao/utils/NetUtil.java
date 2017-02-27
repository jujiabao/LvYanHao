package com.lvyanhao.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

/**
 * Created by lyh on 2017/2/26.
 */

public class NetUtil {
    private static final String PROTOCOL = "http://";

    private static String IP = "192.168.2.106";

    private static int PORT = 8888;

    private static final String SERVER_NAME = "LvYanHaoServer";

    private static String REQUEST_URL = PROTOCOL + IP + ":" + PORT + "/" + SERVER_NAME;

    private static final String CONTENT_TYPE = "application/json";

    private static final String TOKEN_NAME = "token";

    private static final String REQUEST_FAILED_JSON = "{\"status\":\"99999\",\"msg\":\"请求网络失败！\",\"data\":null}";

    /**
     * 向指定URL发送POST方法的请求
     *
     * @param transCode
     *            发送请求的URL
     * @param params
     *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String post(Context context, String transCode, String params, String tokenValue) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        //拿出设置的数据
        SharedPreferences settings= context.getSharedPreferences("setting_net", 0);
        String ip = settings.getString("ip","");
        int port = settings.getInt("port", 0);
        if (ip != "" || !"".equals(ip)) {
            IP = ip;
        }
        if (port != 0) {
            PORT = port;
        }
        REQUEST_URL = PROTOCOL + IP + ":" + PORT + "/" + SERVER_NAME;
        Log.d("lvyanhao", "@ 在SharedPreferences取得数据：ip="+ip+",port="+port);
        try {
            URL realUrl = new URL(NetUtil.REQUEST_URL + transCode);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            System.out.println("conn=" + conn);
            // 设置通用的请求属性
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", NetUtil.CONTENT_TYPE);
            conn.setRequestProperty(NetUtil.TOKEN_NAME, tokenValue);
            // 发送POST请求必须设置如下两行
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 获取URLConnection对象对应的输出流
            out = new PrintWriter(conn.getOutputStream());
            // 发送请求参数
            out.print(params);
            // flush输出流的缓冲
            out.flush();
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送POST请求出现异常！" + e);
            e.printStackTrace();
            return REQUEST_FAILED_JSON;
        }
        // 使用finally块来关闭输出流、输入流
        finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 向指定URL发送GET方法的请求
     *
     * @param transCode 发送请求的URL
     * @param params 请求参数，请求参数应该是name1=value1&name2=value2的形式。
     * @return URL所代表远程资源的响应
     */
    public static String get(Context context, String transCode, String params) {
        String result = "";
        BufferedReader in = null;
        //拿出设置的数据
        SharedPreferences settings= context.getSharedPreferences("setting_net", 0);
        String ip = settings.getString("ip","");
        int port = settings.getInt("port", 0);
        if (ip != "" || "".equals(ip)) {
            IP = ip;
        }
        if (port != 0) {
            PORT = port;
        }
        REQUEST_URL = PROTOCOL + IP + ":" + PORT + "/" + SERVER_NAME;
        try {
            String urlName = NetUtil.REQUEST_URL + transCode + "?" + params;
            URL realUrl = new URL(urlName);
            // 打开和URL之间的连接
            URLConnection conn = realUrl.openConnection();
            // 设置通用的请求属性
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // 建立实际的连接
            conn.connect();
            // 获取所有响应头字段
            Map<String, List<String>> map = conn.getHeaderFields();
            // 遍历所有的响应头字段
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }
            // 定义BufferedReader输入流来读取URL的响应
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
        } catch (Exception e) {
            System.out.println("发送GET请求出现异常！" + e);
            result = "发送GET请求出现异常！";
            e.printStackTrace();
            return REQUEST_FAILED_JSON;
        }
        // 使用finally块来关闭输入流
        finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }
}
