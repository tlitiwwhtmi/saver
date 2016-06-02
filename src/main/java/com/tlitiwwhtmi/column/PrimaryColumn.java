package com.tlitiwwhtmi.column;

import com.tlitiwwhtmi.annotation.PrimayKey;
import com.tlitiwwhtmi.uuid.UUIDGenerator;

import java.lang.reflect.Field;

/**
 * Created by administrator on 16/6/1.
 */
public class PrimaryColumn extends Column{

    private String uuidGeneratorClass;

    public PrimaryColumn(Field field) {
        super(field);
        this.uuidGeneratorClass = field.getAnnotation(PrimayKey.class).idGenerator();
    }

    public String getUuidGeneratorClass() {
        return uuidGeneratorClass;
    }

    public void setUuidGeneratorClass(String uuidGeneratorClass) {
        this.uuidGeneratorClass = uuidGeneratorClass;
    }

    public String generateUUID(){
        try{
            UUIDGenerator idGenerator = (UUIDGenerator) Class.forName(this.uuidGeneratorClass).newInstance();
            return idGenerator.generateUUID();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
