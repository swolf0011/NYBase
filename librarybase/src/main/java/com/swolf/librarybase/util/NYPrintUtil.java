package com.swolf.librarybase.util;

import com.swolf.librarybase.BuildConfig;

public class NYPrintUtil {
    public static void println(String msg){
        if(BuildConfig.DEBUG){
            System.out.println(msg);
        }
    }
    public static void print(String msg){
        if(BuildConfig.DEBUG){
            System.out.print(msg);
        }
    }
}
