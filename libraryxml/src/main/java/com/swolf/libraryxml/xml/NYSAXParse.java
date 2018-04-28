package com.swolf.libraryxml.xml;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.InputSource;
import org.xml.sax.helpers.DefaultHandler;

/**
 * sax解析XML
 * Created by LiuYi-15973602714 on 2017-01-01
 */
public class NYSAXParse {
    public static void parse(DefaultHandler handler, String handlerValue) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(new InputSource(new StringReader(handlerValue)), handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void parse(DefaultHandler handler, InputStream is) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();
            saxParser.parse(is, handler);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (is != null) {
                    is.close();
                    is = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parse(DefaultHandler handler, File file) {
        try {
            SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
            SAXParser saxParser = saxParserFactory.newSAXParser();

            saxParser.parse(file, handler);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
