package com.lvyanhao;

import com.lvyanhao.utils.JSONUtil;
import com.lvyanhao.vo.FilmListInfoVo;
import com.lvyanhao.vo.PageVo;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * Created by Hello.Ju on 17/2/22.
 */
public class TestCase {
    @Test
    public void test1(){
        PageVo pageVo = new PageVo(3);
        System.out.println(pageVo.getStartPgNum()+":"+pageVo.getEndPgNum());
    }

    @Test
    public void test2() {
        FilmListInfoVo filmListInfoVo = new FilmListInfoVo(1);
        Field[] fields = filmListInfoVo.getClass().getDeclaredFields();
        //Ç¿±©Ëü
        Field.setAccessible(fields, true);
        for (Field field : fields) {
            System.out.println(field.getName());
        }
    }

    @Test
    public void test3() {

    }
}
