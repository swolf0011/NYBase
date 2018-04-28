package com.swolf.librarydbgreedao;

import android.content.Context;
import android.database.Cursor;

import com.swolf.librarydbgreedao.entity.DaoSession;
import com.swolf.librarydbgreedao.entity.NYEntityBase;
import com.swolf.librarydbgreedao.entity.ParentDao;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.database.Database;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Created by LiuYi-15973602714
 */
public class NYGreenDaoUtil<T extends NYEntityBase> {
    Database database = null;
    AbstractDao dao = null;

    public NYGreenDaoUtil(Context context, String entityName) {
        DaoSession daoSession = NYGreenDaoManager.getInstance(context).getDaoSession();
        database = daoSession.getDatabase();
        dao = getDao(daoSession.getClass(),entityName);
    }


    /**
     * 当指定主键在表中存在时会发生异常
     *
     * @param t
     * @return
     */
    public long insert(T t) {
        return dao.insert(t);
    }

    /**
     * 当指定主键在表中存在时会覆盖数据
     *
     * @param t
     * @return
     */
    public long insertOrReplace(T t) {
        return dao.insertOrReplace(t);
    }

    /**
     * 批量插入数据
     *
     * @param entities
     */
    public void insertOrReplace(Iterable<T> entities) {
        dao.insertInTx(entities);
    }

    /**
     * 删除
     *
     * @param t
     */
    public void delete(T t) {
        dao.delete(t);
    }

    /**
     * id删除
     *
     * @param id
     */
    public void deleteByKey(Long id) {
        dao.deleteByKey(id);
    }

    /**
     * 批量删除
     *
     * @param entities
     */
    public void deleteInTx(Iterable<T> entities) {
        dao.deleteInTx(entities);
    }

    /**
     * 批量id删除
     *
     * @param ids
     */
    public void deleteByKeyInTx(Iterable<Long> ids) {
        dao.deleteByKeyInTx(ids);
    }

    /**
     * 全部删除
     */
    public void deleteAll() {
        dao.deleteAll();
    }

    /**
     * 修改
     *
     * @param t
     */
    public void update(T t) {
        dao.update(t);
    }

    /**
     * 批量修改
     *
     * @param entities
     */
    public void updateInTx(Iterable<T> entities) {
        dao.updateInTx(entities);
    }

    /**
     * 根据id查找数据
     *
     * @param id
     * @return
     */
    public T load(Long id) {
        return (T) dao.load(id);
    }

    /**
     * 根据行号查找数据
     *
     * @param rowId
     * @return
     */
    public T loadByRowId(Long rowId) {
        return (T) dao.loadByRowId(rowId);
    }

    /**
     * 查找全部数据
     *
     * @return
     */
    public List<T> loadAll() {
        return dao.loadAll();
    }

    /**
     * 条件查找
     *
     * @param where
     * @param selectionArg
     * @return
     */
    public List<T> queryRaw(String where, String... selectionArg) {
        return dao.queryRaw(where, selectionArg);
    }

    /**
     * 执行非查询SQL
     *
     * @param sql
     */
    public void execSQL(String sql) {
        database.execSQL(sql);
    }

    /**
     * 执行查询SQL
     *
     * @param sql
     * @param var2
     * @return
     */
    public Cursor rawQuery(String sql, String[] var2) {
        return database.rawQuery(sql, var2);
    }

    public Database getDatabase() {
        return database;
    }


    private AbstractDao getDao(Class clazz,String entityName) {
        try {
            Method method = clazz.getMethod("get" + entityName + "Dao");
            dao = (AbstractDao) method.invoke(clazz.newInstance());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        }
        return dao;
    }

}
