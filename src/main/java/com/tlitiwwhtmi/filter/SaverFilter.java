package com.tlitiwwhtmi.filter;

import com.tlitiwwhtmi.column.Column;
import com.tlitiwwhtmi.column.ColumnList;
import com.tlitiwwhtmi.saver.fileSaver.BaseFile;

import java.util.*;

/**
 * Created by administrator on 16/6/2.
 */
public class SaverFilter extends BaseFilter{

    private Map<String,Object> equalMap;

    private Map<String,Object> notEqualMap;

    public SaverFilter equal(String key,Object value){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(key,value);
        this.equalMap = map;
        return this;
    }

    public SaverFilter notEqual(String key,Object value){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put(key,value);
        this.notEqualMap = map;
        return this;
    }

    public Map<String, Object> getEqualMap() {
        return equalMap;
    }

    public void setEqualMap(Map<String, Object> equalMap) {
        this.equalMap = equalMap;
    }

    @Override
    public List doFilter(List list, ColumnList columnList){
        List afterEqual = doEqualFilter(list,columnList);
        List afterNotEqual = doNotEqualFilter(afterEqual,columnList);
        return afterNotEqual;
    }

    public List doEqualFilter(List list, ColumnList columnList){
        if(null == this.equalMap || this.equalMap.size() == 0){
            return list;
        }

        Set<String> keys = equalMap.keySet();
        for(String key : keys){
            Column column = columnList.getColumn(key);
            if(null == column){
                continue;
            }

            for(Iterator itr = list.iterator();itr.hasNext();){
                Object object = itr.next();
                if(!column.getUnCastColumnVal(object).equals(equalMap.get(key))){
                    itr.remove();
                }
            }
        }

        return list;
    }

    public List doNotEqualFilter(List list, ColumnList columnList){
        if(null == this.notEqualMap || this.notEqualMap.size() == 0){
            return list;
        }

        Set<String> keys = notEqualMap.keySet();
        for(String key : keys){
            Column column = columnList.getColumn(key);
            if(null == column){
                continue;
            }

            for(Iterator itr = list.iterator();itr.hasNext();){
                Object object = itr.next();
                if(column.getUnCastColumnVal(object).equals(notEqualMap.get(key))){
                    itr.remove();
                }
            }
        }

        return list;

    }

}
