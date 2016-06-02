package com.tlitiwwhtmi.column;

import com.tlitiwwhtmi.annotation.Exclude;
import com.tlitiwwhtmi.annotation.PrimayKey;

import java.beans.PropertyDescriptor;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/6/1.
 */
public class Column {

    private Class<?> columnType;

    private List<Class<?>> annotationClasses;

    private Field field;

    public Column(Field field) {

        this.columnType = field.getType();

        Annotation[] annotations = field.getAnnotations();
        List<Class<?>> classes = new ArrayList<Class<?>> ();
        if(annotations.length > 0){
            for(Annotation annotation : annotations){
                classes.add(annotation.annotationType());
            }
        }
        this.annotationClasses = classes;
        this.field = field;

    }

    public List<Class<?>> getAnnotationClasses() {
        return annotationClasses;
    }

    public void setAnnotationClasses(List<Class<?>> annotationClasses) {
        this.annotationClasses = annotationClasses;
    }

    public Class<?> getColumnType() {
        return columnType;
    }

    public void setColumnType(Class<?> columnType) {
        this.columnType = columnType;
    }

    public Field getField() {
        return field;
    }

    public void setField(Field field) {
        this.field = field;
    }

    public String getColumnVal(Object object){
        try{
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),object.getClass());
            Method getMethod = pd.getReadMethod();
            return null != getMethod.invoke(object) ? getMethod.invoke(object).toString() : null;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Object setColumnVal(String val,Object object){
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(),object.getClass());
            Method setMethod = pd.getWriteMethod();
            setMethod.invoke(object,this.parseVal(val));
            return object;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Object parseVal(String val){
        if(int.class.equals(this.columnType)){
            return Integer.parseInt(val);
        }
        if(String.class.equals(this.columnType)){
            return val;
        }
        return null;
    }

    public boolean isPrimaryColumn(){
        for(Class<?> clazz : annotationClasses){
            if(PrimayKey.class.equals(clazz)){
                return true;
            }
        }
        return false;
    }

    public boolean isExcludeColumn(){
        for(Class<?> clazz : annotationClasses){
            if(Exclude.class.equals(clazz)){
                return true;
            }
        }
        return false;
    }


}
