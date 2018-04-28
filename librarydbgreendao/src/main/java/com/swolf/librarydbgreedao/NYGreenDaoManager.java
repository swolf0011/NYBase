package com.swolf.librarydbgreedao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.swolf.librarydbgreedao.entity.DaoMaster;
import com.swolf.librarydbgreedao.entity.DaoSession;

import org.greenrobot.greendao.database.Database;




/**
 * Created by LiuYi-15973602714
 */
public class NYGreenDaoManager {

    private DaoMaster daoMaster;
    private DaoSession daoSession;
    private SQLiteDatabase sqliteDatabase;
    private Database database;
    private static volatile NYGreenDaoManager mInstance = null;
    private String db_name = "greendao.db";

    private NYGreenDaoManager(Context context) {
        if (mInstance == null) {
            DaoMaster.DevOpenHelper devOpenHelper = new DaoMaster.DevOpenHelper(context, db_name);
            sqliteDatabase = devOpenHelper.getWritableDatabase();
            daoMaster = new DaoMaster(sqliteDatabase);
            daoSession = daoMaster.newSession();
            database = daoSession.getDatabase();
        }
    }

    public static NYGreenDaoManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (NYGreenDaoManager.class) {
                if (mInstance == null) {
                    mInstance = new NYGreenDaoManager(context);
                }
            }
        }
        return mInstance;
    }

    public DaoMaster getDaoMaster() {
        return daoMaster;
    }

    public DaoSession getDaoSession() {
        return daoSession;
    }

    public Database getDatabase() {
        return database;
    }

    public SQLiteDatabase getSqliteDatabase() {
        return sqliteDatabase;
    }
}
