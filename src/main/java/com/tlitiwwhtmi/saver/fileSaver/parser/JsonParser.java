package com.tlitiwwhtmi.saver.fileSaver.parser;

import com.google.gson.Gson;
import com.tlitiwwhtmi.column.ColumnList;

/**
 * Created by administrator on 16/6/2.
 */
public class JsonParser extends Parser {
    @Override
    public String parseToString(Object object, ColumnList columnList) {
        Gson gson = new Gson();
        return gson.toJson(object);
    }

    @Override
    public Object parse(String str, ColumnList columnList, Class<?> clazz) {
        try{
            Gson gson = new Gson();
            return gson.fromJson(str,clazz);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
