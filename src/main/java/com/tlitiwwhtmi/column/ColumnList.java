package com.tlitiwwhtmi.column;

import com.tlitiwwhtmi.annotation.PrimayKey;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/6/1.
 */
public class ColumnList {

    private List<Column> columnList;

    public ColumnList(Class<?> clazz) {

        List<Column> columns = new ArrayList();
        for (Field field : clazz.getDeclaredFields()){
            if(field.isAnnotationPresent(PrimayKey.class)){
                columns.add(new PrimaryColumn(field));
            }else{
                columns.add(new Column(field));
            }
        }
        this.columnList = columns;
    }

    public List<Column> getColumnList() {
        return columnList;
    }

    public void setColumnList(List<Column> columnList) {
        this.columnList = columnList;
    }

    public Column getPrimaryColumn(){
        for (Column column : columnList){
            if(column.isPrimaryColumn()){
                return column;
            }
        }
        return null;
    }
}
