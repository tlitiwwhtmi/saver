package com.tlitiwwhtmi.filter;

import com.tlitiwwhtmi.column.ColumnList;

import java.util.List;

/**
 * Created by administrator on 16/6/8.
 */
public abstract class BaseFilter {

    public abstract List doFilter(List list, ColumnList columnList);

}
