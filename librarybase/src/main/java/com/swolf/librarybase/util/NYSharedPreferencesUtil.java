package com.swolf.librarybase.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by abc on 2018/4/27.
 */

public class NYSharedPreferencesUtil {

    private static SharedPreferences sp;

    private static String fileName = "sharePreferences_data";

    public static boolean remove(Context context, String... ks) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        if (ks != null) {
            SharedPreferences.Editor edit = sp.edit();
            for (int i = 0; i < ks.length; i++) {
                edit.remove(ks[i]);
            }
            return edit.commit();
        }
        return false;
    }

    public static boolean clear(Context context) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        return sp.edit().clear().commit();
    }


    /**
     * @param context
     * @param k
     * @param resultClass resultClass ：Integer|Boolean|Float|Long|String class
     * @return
     */
    public static Object get(Context context, String k, Class resultClass) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        if (resultClass == Integer.class) {
            return sp.getInt(k, 0);
        } else if (resultClass == Boolean.class) {
            return sp.getBoolean(k, false);
        } else if (resultClass == Float.class) {
            return sp.getFloat(k, 0f);
        } else if (resultClass == Long.class) {
            return sp.getLong(k, 0L);
        } else if (resultClass == String.class) {
            return sp.getString(k, "");
        }
        return null;
    }

    /**
     * @param context
     * @param k
     * @param o       o ：Integer|Boolean|Float|Long|String
     * @return
     */
    public static boolean put(Context context, String k, Object o) {
        if (sp == null) {
            sp = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        }
        if (o instanceof Integer) {
            int v = Integer.parseInt(o.toString());
            return sp.edit().putInt(k, v).commit();
        } else if (o instanceof Boolean) {
            Boolean v = Boolean.parseBoolean(o.toString());
            return sp.edit().putBoolean(k, v).commit();
        } else if (o instanceof Float) {
            Float v = Float.parseFloat(o.toString());
            return sp.edit().putFloat(k, v).commit();
        } else if (o instanceof Long) {
            Long v = Long.parseLong(o.toString());
            return sp.edit().putLong(k, v).commit();
        } else if (o instanceof String) {
            String v = String.valueOf(o);
            return sp.edit().putString(k, v).commit();
        }
        return false;
    }
}
