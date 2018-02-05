package com.swolf.librarydbormlite;


/**
 * Created by LiuYi-15973602714
 */
public class NYOrmLiteInfo {

    public String databasename = "OrmLite.db";
    public int version = 1;
    //onCreate
    public Class[] createTableClass;
    public Class[] delTableClass;

    public NYOrmLiteInfo(int version, Class[] createTableClass, Class[] delTableClass) {
        this.version = version;
        this.createTableClass = createTableClass;
        this.delTableClass = delTableClass;
    }

    public NYOrmLiteInfo(String databasename, int version, Class[] createTableClass, Class[] delTableClass) {
        this.databasename = databasename;
        this.version = version;
        this.createTableClass = createTableClass;
        this.delTableClass = delTableClass;
    }
}