package com.swolf.libraryxml.xml;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import org.xmlpull.v1.XmlPullParserFactory;

/**
 * Pull解析XML
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public abstract class NYPullParse<T> {

    public XmlPullParserFactory factory;

    public NYPullParse() {
        try {
            factory = XmlPullParserFactory.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void save(OutputStream outputStream, List<T> list);

    public abstract List<T> parse(InputStream inputStream);

}
