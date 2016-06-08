package com.tlitiwwhtmi.saver.fileSaver;

import com.tlitiwwhtmi.column.ColumnList;
import com.tlitiwwhtmi.column.PrimaryColumn;
import com.tlitiwwhtmi.filter.BaseFilter;
import com.tlitiwwhtmi.saver.BaseSaver;
import com.tlitiwwhtmi.saver.fileSaver.parser.Parser;

import java.util.Iterator;
import java.util.List;

/**
 * Created by saver on 16/5/31.
 */

public class BaseFileSaver extends BaseSaver {

    private final Class<?> clazz;

    private final BaseFile baseFile;

    private final ColumnList columnList;

    /**
     * @param filePath the path to save the data file
     * @param fileName the name of the data file
     * @param clazz the entity class
     * @param parser parser
     */
    public BaseFileSaver(String filePath,String fileName,Class<?> clazz,Parser parser){
        this.baseFile = new BaseFile(filePath,fileName,parser);
        this.clazz = clazz;
        this.columnList = new ColumnList(clazz);
    }

    @Override
    public boolean insert(Object obj) {

        if(!obj.getClass().equals(clazz)){
            return false;
        }

        PrimaryColumn primaryColumn = (PrimaryColumn) columnList.getPrimaryColumn();

        if(primaryColumn == null){
            return false;
        }
        String keyVal = primaryColumn.getColumnVal(obj);
        if(null == keyVal || "".equals(keyVal)){
            keyVal = primaryColumn.generateUUID();
            if(null == keyVal){
                return false;
            }
        }
        obj = primaryColumn.setColumnVal(keyVal,obj);
        if( obj == null){
            return false;
        }

        List list = list();
        for(Object fileObj : list){
            if(keyVal.equals(primaryColumn.getColumnVal(fileObj))){
                return false;
            }
        }

        return baseFile.writeToFile(obj,this.columnList);

    }

    @Override
    public boolean delete(Object obj) {
        List list = list();

        int beforeSize = list.size();

        PrimaryColumn primaryColumn = (PrimaryColumn) columnList.getPrimaryColumn();

        if(primaryColumn == null){
            return false;
        }
        String keyVal = primaryColumn.getColumnVal(obj);

        list = this.removeFromListById(keyVal,primaryColumn,list);

        if(beforeSize == list.size()){
            return false;
        }

        return baseFile.RewWriteToFile(list,this.columnList);
    }

    @Override
    public boolean update(Object obj) {
        List list = list();

        int beforeSize = list.size();

        PrimaryColumn primaryColumn = (PrimaryColumn) columnList.getPrimaryColumn();

        if(primaryColumn == null){
            return false;
        }
        String keyVal = primaryColumn.getColumnVal(obj);

        list = this.removeFromListById(keyVal,primaryColumn,list);

        if(beforeSize == list.size()){
            return false;

        }

        list.add(obj);
        return baseFile.RewWriteToFile(list,this.columnList);
    }

    @Override
    public Object getById(String key) {
        List list = list();

        PrimaryColumn primaryColumn = (PrimaryColumn) columnList.getPrimaryColumn();

        if(primaryColumn == null){
            return null;
        }

        for(Object tempObj : list){
            if(key.equals(primaryColumn.getColumnVal(tempObj))){
                return tempObj;
            }
        }
        return null;
    }

    @Override
    public List query(BaseFilter filter) {
        List list = list();
        return filter.doFilter(list,columnList);
    }

    @Override
    public List list() {

        return baseFile.readFromFile(this.columnList,this.clazz);
    }


    private List removeFromListById(String keyVal,PrimaryColumn column,List list){
        for(Iterator itr=list.iterator();itr.hasNext();){
            Object tempObj = itr.next();
            if(keyVal.equals(column.getColumnVal(tempObj))){
                itr.remove();
                break;
            }
        }
        return list;
    }

}
