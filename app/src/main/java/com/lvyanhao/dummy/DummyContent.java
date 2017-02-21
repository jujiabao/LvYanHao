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
     * Ĭ�Ϲ�����20������
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
     * ���listviewҳ��Ĳ˵�
     * @param position
     * @return
     */
    private static DummyItem createDummyItem(int position) {
//        return new DummyItem(String.valueOf(position), "Item " + position, makeDetails(position));
        Log.d(">>>createDummyItem<<<", "�Ѿ���...."+position);
        String id = position+"";
        String filmId = UUID.randomUUID().toString();
        String content = "null";
        String filmNa = "��������" + position;
        String filmLv = "4.6";
        String filmInfo = "��ǿȡ�澭��������ЦKing.....";
        String filmPlayTm = "2017-01-28 ��������ӳ";
        return new DummyItem(id, content, filmId, filmNa, filmLv, filmInfo, filmPlayTm, makeDetails(position));
    }

    /**
     * �����ȥ����ϸ�¼�
     * @param position
     * @return
     */
    private static String makeDetails(int position) {
        return "���ǵ�"+position+"����ǩ!";
    }

    /**
     * A dummy item representing a piece of content.
     */
    public static class DummyItem {
        public final String id;
        //��ӰId��
        public final String filmId;
        //����
        public final String content;
        //��Ӱ��
        public final String filmNa;
        //��Ӱ����
        public final String filmLv;
        //��Ӱ���
        public final String filmInfo;
        //��Ӱ��ӳʱ��
        public final String filmPlayTm;
        //��ϸ��Ϣ
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
