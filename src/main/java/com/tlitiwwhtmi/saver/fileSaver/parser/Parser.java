package com.tlitiwwhtmi.saver.fileSaver.parser;

import com.tlitiwwhtmi.column.ColumnList;

/**
 * Created by administrator on 16/6/1.
 */
public abstract class Parser {

    public abstract String parseToString(Object object, ColumnList columnList);

    public abstract Object parse(String str,ColumnList columnList,Class<?> clazz);
}
