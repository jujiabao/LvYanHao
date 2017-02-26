package com.lvyanhao.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class JSONUtil {

    /**
     * ������ͨ���ݣ�һ����
     * @param jsonStr
     * @param key
     * @return
     * @throws JSONException
     */
    public static String parseJson(String jsonStr, String key) throws JSONException {
        try {
            JSONObject jsonObj = new JSONObject(jsonStr);
            String value = jsonObj.getString(key);
            return value;
        } catch (JSONException e) {
            System.out.println("JSON����ʧ�ܣ�"+e.getCause());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     * ֱ�ӷ���JSONObject�������Զ������
     * @param jsonStr
     * @param key
     * @return
     * @throws JSONException
     */
    public static JSONObject jsonObject(String jsonStr, String key) throws JSONException {
        try {
            JSONObject jsonObj = new JSONObject(jsonStr).getJSONObject(key);
            return jsonObj;
        } catch (JSONException e) {
            System.out.println("JSON����ʧ�ܣ�"+e.getCause());
            e.printStackTrace();
            throw e;
        }
    }

    /**
     *
     * @param jsonStr
     * @param key
     * @return
     * @throws JSONException
     */
    public static List<List<Map<String, String>>> parseJsonMulti(String jsonStr, String key) throws JSONException {
        Map<String, String> map;
        List<Map<String, String>> listMap;
        List<List<Map<String, String>>> list = new ArrayList<List<Map<String,String>>>();
        try {
            JSONArray jsonObjs = new JSONObject(jsonStr)
                    .getJSONArray(key);
            for (int i = 0; i < jsonObjs.length(); i++) {
                JSONObject jsonObj = (JSONObject) jsonObjs.get(i);
                Iterator<?> keys = jsonObj.keys();
                map = new HashMap<String, String>();
                listMap = new ArrayList<Map<String,String>>();
                while (keys.hasNext()) {
                    String keyMap = keys.next().toString();
                    Object valueMap = jsonObj.get(keyMap)+"";//ȫ��תΪ�ַ���
                    map.put(keyMap, (String) valueMap);
                    listMap.add(map);
                }
                list.add(listMap);
            }
            return list;
        } catch (JSONException e) {
            System.out.println("JSON����ʧ�ܣ�"+e.getCause());
            e.printStackTrace();
            throw e;
        }
    }
}
