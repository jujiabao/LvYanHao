package com.lvyanhao.dummy;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;


public class DummyContent {

    public static final List<DummyItem> ITEMS = new ArrayList<DummyItem>();

    public static final Map<String, DummyItem> ITEM_MAP = new HashMap<String, DummyItem>();

    /**
     * 默认共加载20条数据
     */
    private static final int COUNT = 20;

    static {
        // Add some sample items.
        for (int i = 0; i < COUNT; i++) {
            addItem(createDummyItem(i));
        }
    }

    private static void addItem(DummyItem item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.id, item);
    }

    /**
     * 添加listview页面的菜单
     * @param position
     * @return
     */
    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
        Log.d(">>>createDummyItem<<<", "已经入...."+position);
        String id = position+"";
        String filmId = UUID.randomUUID().toString();
        String content = "null";
        String filmNa = "大闹天竺" + position;
        String filmLv = "4.6";
        String filmInfo = "宝强取真经，争当搞笑King.....";
        String filmPlayTm = "2017-01-28 本周六上映";
        return new DummyItem(id, content, filmId, filmNa, filmLv, filmInfo, filmPlayTm, makeDetails(position));
    }

    /**
     * 点击进去的详细事件
     * @param position
     * @return
     */
    private static String makeDetails(int position) {
        return "这是第"+position+"个标签!";
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        //电影Id名
        public final String filmId;
        //保留
        public final String content;
        //电影名
        public final String filmNa;
        //电影评分
        public final String filmLv;
        //电影简介
        public final String filmInfo;
        //电影上映时间
        public final String filmPlayTm;
        //详细信息
        public final String details;

        public DummyItem(String id, String content, String filmId, String filmNa, String filmLv, String filmInfo, String filmPlayTm, String details) {
            this.id = id;
            this.content = content;
            this.filmId = filmId;
            this.filmNa = filmNa;
            this.filmLv = filmLv;
            this.filmInfo = filmInfo;
            this.filmPlayTm = filmPlayTm;
            this.details = details;
        }

        @Override
        public String toString() {
            return content;
        }
    }
}
