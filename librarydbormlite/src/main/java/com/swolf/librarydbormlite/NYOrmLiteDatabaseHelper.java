package com.swolf.librarydbormlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;


/**
 * Dao帮助类
 * Created by LiuYi-15973602714
 */
public class NYOrmLiteDatabaseHelper extends OrmLiteSqliteOpenHelper {
    private Map<String, Dao> daos = new HashMap<String, Dao>();
    private NYOrmLiteInfo ormLiteInfo;

    public NYOrmLiteDatabaseHelper(Context context, NYOrmLiteInfo ormInfo){
        super(context, ormInfo.databasename, null, ormInfo.version);
        ormLiteInfo = ormInfo;
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            for (Class clazz : ormLiteInfo.createTableClass) {
                TableUtils.createTable(connectionSource, clazz);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            for (Class clazz : ormLiteInfo.delTableClass) {
                TableUtils.dropTable(connectionSource, clazz, true);
            }
            onCreate( database,  connectionSource);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    /**
     * 获取 Dao
     */
    public Dao getDao(Class clazz){
        Dao dao = null;
        String className = clazz.getSimpleName();
        if (daos.containsKey(className)) {
            dao = daos.get(className);
        }
        if (dao == null) {
            try {
                dao = super.getDao(clazz);
                daos.put(className, dao);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return dao;
    }

    /**
     * 释放资源
     */
    @Override
    public void close() {
        super.close();
        for (String key : daos.keySet()) {
            Dao dao = daos.get(key);
            dao = null;
        }
    }

}
