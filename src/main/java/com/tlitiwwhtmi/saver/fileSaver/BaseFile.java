package com.tlitiwwhtmi.saver.fileSaver;

import com.tlitiwwhtmi.column.ColumnList;
import com.tlitiwwhtmi.saver.fileSaver.parser.Parser;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by administrator on 16/6/1.
 */
public class BaseFile{

    private String fileName;

    private Parser parser;

    private String winFilePath = "D:/";

    private String linuxFilePath = "/data/mvp/";

    public BaseFile(String fileName,Parser parser) {
        this.fileName = fileName;
        this.parser = parser;
        this.createFile();
    }

    private String generatePath(){
        String osname = System.getProperty("os.name").toLowerCase();
        if(osname.startsWith("win")){
            return winFilePath+fileName;
        }else{
            File saveDir = new File(linuxFilePath);
            if(!saveDir.exists()){
                saveDir.mkdirs();
            }
            return linuxFilePath+fileName;
        }
    }

    private void createFile(){
        File file = new File(generatePath());
        if(file.exists()){
            return;
        }else{
            clearFile();
        }
    }

    public boolean clearFile(){
        try{
            FileOutputStream fos = new FileOutputStream(generatePath());
            fos.flush();
            fos.close();
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean writeToFile(Object object, ColumnList columnList){
        String data = this.parser.parseToString(object,columnList);
        try {

            String filePath = generatePath();
            String newLine = System.getProperty("line.separator");
            File savefile = new File(filePath);
            FileOutputStream fos =  new FileOutputStream(savefile, true);
            fos.write(data.getBytes());
            fos.write(newLine.getBytes());
            fos.close();

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean RewWriteToFile(List list,ColumnList columnList){

        if(clearFile()){
            for(Object obj : list){
                if(!writeToFile(obj,columnList)){
                    return false;
                }
            }
            return true;
        }

        return false;
    }

    public List readFromFile(ColumnList columnList,Class<?> clazz){
        List list = new ArrayList();

        File dataFile = new File(generatePath());
        try {
            BufferedReader reader = new BufferedReader(new FileReader(dataFile));
            String tempString = null;
            while ((tempString = reader.readLine()) != null){
                list.add(this.parser.parse(tempString,columnList,clazz));
            }
        } catch (Exception e) {
            e.printStackTrace();
            return list;
        }
        return list;
    }

    public String getFullFilePath(){
        return generatePath();
    }
}
