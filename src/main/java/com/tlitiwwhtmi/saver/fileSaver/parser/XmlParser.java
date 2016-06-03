package com.tlitiwwhtmi.saver.fileSaver.parser;

import com.tlitiwwhtmi.column.Column;
import com.tlitiwwhtmi.column.ColumnList;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Result;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by administrator on 16/6/3.
 */
public class XmlParser extends Parser{

    private DocumentBuilder getDocumentBuilder(){
        try{
            DocumentBuilderFactory factory = DocumentBuilderFactory
                    .newInstance();
            return factory.newDocumentBuilder();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private Document initDocument(){
        DocumentBuilder builder = getDocumentBuilder();
        if(null == builder){
            return null;
        }
        return builder.newDocument();
    }

    private Document parseDocument(String str){
        try {
            DocumentBuilder builder = getDocumentBuilder();
            if(null == builder){
                return null;
            }
            StringReader stringReader = new StringReader(str);
            InputSource inputSource = new InputSource(stringReader);
            return builder.parse(inputSource);
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    private String transform2String(Element element){
        try {
            DOMSource domSource = new DOMSource(element);
            StringWriter stringWriter = new StringWriter();
            Result result = new StreamResult(stringWriter);
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.transform(domSource,result);
            return stringWriter.getBuffer().toString();
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public String parseToString(Object object, ColumnList columnList) {
        Document document = initDocument();
        if(null == document){
            return null;
        }
        Element root = document.createElement(object.getClass().getName());
        for(Column column : columnList.getColumnList()){
            if(column.isExcludeColumn()){
                continue;
            }
            Element item = document.createElement(column.getField().getName());
            item.appendChild(document.createTextNode(column.getColumnVal(object)));
            root.appendChild(item);
        }

        return transform2String(root);
    }

    @Override
    public Object parse(String str, ColumnList columnList, Class<?> clazz) {
        try{
            Object object = clazz.newInstance();

            DocumentBuilder builder = getDocumentBuilder();
            if(null == builder) {
                return null;
            }
            Document document = parseDocument(str);
            if(null == document){
                return null;
            }
            NodeList nodeList = document.getElementsByTagName(clazz.getName());
            if(nodeList.getLength() == 0){
                return null;
            }
            Node node = nodeList.item(0);
            NodeList items = node.getChildNodes();
            for(int i = 0; i<items.getLength(); i++){
                Node item = items.item(i);
                for(Column column : columnList.getColumnList()){
                    if(item.getNodeName().equals(column.getField().getName())){
                        column.setColumnVal(item.getFirstChild().getTextContent(),object);
                    }
                }
            }
            return object;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }
}
