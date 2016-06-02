package com.tlitiwwhtmi.saver.fileSaver.parser;

import com.tlitiwwhtmi.column.Column;
import com.tlitiwwhtmi.column.ColumnList;

/**
 * Created by administrator on 16/6/1.
 */
public class StringParser extends Parser {

    private static final String FILED_SEPERATOR = "|";

    @Override
    public String parseToString(Object object, ColumnList columnList) {
        StringBuffer objData = new StringBuffer();
        for(Column column : columnList.getColumnList()){
            if(column.isExcludeColumn()){
                continue;
            }else{
                objData.append(column.getColumnVal(object));
                objData.append(FILED_SEPERATOR);
            }
        }
        return objData.toString();
    }

    @Override
    public Object parse(String str,ColumnList columnList,Class<?> clazz) {
        try{
            Object obj = clazz.newInstance();

            String[] attrs = str.split("\\" + FILED_SEPERATOR);

            int j = 0;
            for(int i = 0; i < columnList.getColumnList().size(); i++){
                Column column = columnList.getColumnList().get(i);
                if(column.isExcludeColumn()){
                    continue;
                }else{
                    if(j < attrs.length){
                        obj = column.setColumnVal(attrs[j],obj);
                        j++ ;
                    }
                }
            }

            return obj;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
