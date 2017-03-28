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

    public static final String SERVER_NAME = "LvYanHaoServer";

    private static String REQUEST_URL = PROTOCOL + IP + ":" + PORT + "/" + SERVER_NAME;

    private static final String CONTENT_TYPE = "application/json";

    private static final String TOKEN_NAME = "token";

    private static final String REQUEST_FAILED_JSON = "{\"status\":\"99999\",\"msg\":\"��������ʧ�ܣ�\",\"data\":null}";

    private static final String JSESSION_NAME="JSESSIONID";

    private static String JSESSION_VALUE = null;

    public static String TOKEN_VALUE = null;

    public static boolean flag = false;

    /**
     * ��ָ��URL����POST����������
     *
     * @param transCode
     *            ���������URL
     * @param params
     *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
     * @return URL������Զ����Դ����Ӧ
     */
    public static String post(Context context, String transCode, String params, String tokenValue) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        //�ó����õ�����
        SharedPreferences settings= context.getSharedPreferences("setting_net", 0);
        String ip = settings.getString("ip","");
        int port = settings.getInt("port", 0);
        String jsession_value = settings.getString(JSESSION_NAME, null);
        if (ip != "" || !"".equals(ip)) {
            IP = ip;
        }
        if (port != 0) {
            PORT = port;
        }
        String temp = null;
        temp = JSESSION_VALUE;
        REQUEST_URL = PROTOCOL + IP + ":" + PORT + "/" + SERVER_NAME;
        Log.d("lvyanhao", "@ ��SharedPreferencesȡ�����ݣ�ip="+ip+",port="+port);
        try {
            URL realUrl = new URL(NetUtil.REQUEST_URL + transCode);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            System.out.println("conn=" + conn);
            // ����ͨ�õ���������
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", NetUtil.CONTENT_TYPE);
            if (JSESSION_VALUE != null) {
                conn.setRequestProperty("cookie", JSESSION_NAME+"="+JSESSION_VALUE);
            }
            if (JSESSION_VALUE == null && flag && jsession_value != null) {
                conn.setRequestProperty("cookie", JSESSION_NAME+"="+jsession_value);
            }
            conn.setRequestProperty(NetUtil.TOKEN_NAME, tokenValue);
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(params);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
            //��ȡ��Ӧͷ��Ϣ
            Map<String, List<String>> map = conn.getHeaderFields();
            if (map.containsKey("Set-Cookie")) {
                List<String> values = map.get("Set-Cookie");
                if (values != null && !values.isEmpty()) {
                    for (String s:values) {
                        if (s.startsWith("JSESSIONID=")) {
                            JSESSION_VALUE = s.replaceAll("JSESSIONID=","");
                        } else {
                            int start = s.indexOf("JSESSIONID=");
                            if (start >= 0) {
                                s = s.substring(start + 11);
                                int end = s.indexOf(";");
                                if (end >=0) {
                                    JSESSION_VALUE = s.substring(0, end);
                                }
                            }
                        }
                    }
                }
                Log.d("jujiabao", "JSESSION_VALUE="+JSESSION_VALUE);
            }
            if (map.containsKey(TOKEN_NAME)) {
                TOKEN_VALUE = map.get(TOKEN_NAME).get(0);
            }
            Log.d("jujiabao", "��Ӧͷ��"+map.toString());
            if (JSESSION_VALUE != null) {
                if (!JSESSION_VALUE.equals(temp)) {
                    SharedPreferences.Editor editor = settings.edit();
                    editor.putString(JSESSION_NAME, JSESSION_VALUE);
                    editor.commit();
                    Log.d("jujiabao", JSESSION_NAME+"�����ã�"+JSESSION_VALUE);
                }
            }
        } catch (Exception e) {
            System.out.println("����POST��������쳣��" + e);
            e.printStackTrace();
            return REQUEST_FAILED_JSON;
        }
        // ʹ��finally�����ر��������������
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
     * ��ָ��URL����GET����������
     *
     * @param transCode ���������URL
     * @param params ����������������Ӧ����name1=value1&name2=value2����ʽ��
     * @return URL������Զ����Դ����Ӧ
     */
    public static String get(Context context, String transCode, String params) {
        String result = "";
        BufferedReader in = null;
        //�ó����õ�����
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
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            // ����ͨ�õ���������
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent",
                    "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            // ����ʵ�ʵ�����
            conn.connect();
            /*// ��ȡ������Ӧͷ�ֶ�
            Map<String, List<String>> map = conn.getHeaderFields();
            // �������е���Ӧͷ�ֶ�
            for (String key : map.keySet()) {
                System.out.println(key + "--->" + map.get(key));
            }*/
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
            //��ȡ��Ӧͷ��Ϣ
            Map<String, List<String>> map = conn.getHeaderFields();
            if (map.containsKey("Set-Cookie")) {
                List<String> values = map.get("Set-Cookie");
                if (values != null && !values.isEmpty()) {
                    for (String s:values) {
                        if (s.startsWith("JSESSIONID=")) {
                            JSESSION_VALUE = s.replaceAll("JSESSIONID=","");
                        } else {
                            int start = s.indexOf("JSESSIONID=");
                            if (start >= 0) {
                                s = s.substring(start + 11);
                                int end = s.indexOf(";");
                                if (end >=0) {
                                    JSESSION_VALUE = s.substring(0, end);
                                }
                            }
                        }
                    }
                }
                Log.d("jujiabao", "JSESSION_VALUE="+JSESSION_VALUE);
            }
            if (map.containsKey(TOKEN_NAME)) {
                TOKEN_VALUE = map.get(TOKEN_NAME).get(0);
            }
            Log.d("jujiabao", "��Ӧͷ��"+map.toString());
        } catch (Exception e) {
            System.out.println("����GET��������쳣��" + e);
            result = "����GET��������쳣��";
            e.printStackTrace();
            return REQUEST_FAILED_JSON;
        }
        // ʹ��finally�����ر�������
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

    /**
     * ��ָ��URL����POST����������
     *
     * @param transCode
     *            ���������URL
     * @param params
     *            ����������������Ӧ����name1=value1&name2=value2����ʽ��
     * @return URL������Զ����Դ����Ӧ
     */
    public static String postForCheck(Context context, String transCode, String params, String tokenValue) {
        PrintWriter out = null;
        BufferedReader in = null;
        String result = "";
        //�ó����õ�����
        SharedPreferences settings= context.getSharedPreferences("setting_net", 0);
        String ip = settings.getString("ip","");
        int port = settings.getInt("port", 0);
        String jsession_value = settings.getString(JSESSION_NAME, null);
        if (ip != "" || !"".equals(ip)) {
            IP = ip;
        }
        if (port != 0) {
            PORT = port;
        }
        REQUEST_URL = PROTOCOL + IP + ":" + PORT + "/" + SERVER_NAME;
        Log.d("lvyanhao", "@ ��SharedPreferencesȡ�����ݣ�ip="+ip+",port="+port);
        try {
            URL realUrl = new URL(NetUtil.REQUEST_URL + transCode);
            // �򿪺�URL֮�������
            URLConnection conn = realUrl.openConnection();
            System.out.println("conn=" + conn);
            // ����ͨ�õ���������
            conn.setConnectTimeout(10000);
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent","Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type", NetUtil.CONTENT_TYPE);
            if (jsession_value != null) {
                conn.setRequestProperty("cookie", JSESSION_NAME+"="+jsession_value);
            }
            conn.setRequestProperty(NetUtil.TOKEN_NAME, tokenValue);
            // ����POST�������������������
            conn.setDoOutput(true);
            conn.setDoInput(true);
            // ��ȡURLConnection�����Ӧ�������
            out = new PrintWriter(conn.getOutputStream());
            // �����������
            out.print(params);
            // flush������Ļ���
            out.flush();
            // ����BufferedReader����������ȡURL����Ӧ
            in = new BufferedReader(
                    new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result += "\n" + line;
            }
            //��ȡ��Ӧͷ��Ϣ
            Map<String, List<String>> map = conn.getHeaderFields();
            if (map.containsKey("Set-Cookie")) {
                List<String> values = map.get("Set-Cookie");
                if (values != null && !values.isEmpty()) {
                    for (String s:values) {
                        if (s.startsWith("JSESSIONID=")) {
                            JSESSION_VALUE = s.replaceAll("JSESSIONID=","");
                        } else {
                            int start = s.indexOf("JSESSIONID=");
                            if (start >= 0) {
                                s = s.substring(start + 11);
                                int end = s.indexOf(";");
                                if (end >=0) {
                                    JSESSION_VALUE = s.substring(0, end);
                                }
                            }
                        }
                    }
                }
                Log.d("jujiabao", "JSESSION_VALUE="+JSESSION_VALUE);
            }
            if (map.containsKey(TOKEN_NAME)) {
                TOKEN_VALUE = map.get(TOKEN_NAME).get(0);
            }
            Log.d("jujiabao", "��Ӧͷ��"+map.toString());
        } catch (Exception e) {
            System.out.println("����POST��������쳣��" + e);
            e.printStackTrace();
            return REQUEST_FAILED_JSON;
        }
        // ʹ��finally�����ر��������������
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
}
