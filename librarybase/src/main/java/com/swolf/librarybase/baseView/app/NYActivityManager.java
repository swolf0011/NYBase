package com.swolf.librarybase.baseView.app;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.text.TextUtils;

import java.util.Enumeration;
import java.util.List;
import java.util.Stack;

public class NYActivityManager {
    /**
     * 保存所有Activity
     */
    private volatile Stack<Activity> activityStack = new Stack<Activity>();

    private static volatile NYActivityManager instance;

    private NYActivityManager() {
    }

    /**
     * 创建单例类，提供静态方法调用
     *
     * @return ActivityManager
     */
    public static NYActivityManager getInstance() {
        if (instance == null) {
            instance = new NYActivityManager();
        }
        return instance;
    }

    /**
     * 退出Activity
     *
     * @param activity Activity
     */
    public void popActivity(Activity activity) {
        if (activity != null) {
            activityStack.remove(activity);
        }
    }

    /**
     * 获得当前栈顶的Activity
     *
     * @return Activity Activity
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (!activityStack.empty()) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 将当前Activity推入栈中
     *
     * @param activity Activity
     */
    public void pushActivity(Activity activity) {
        activityStack.add(activity);
    }

    /**
     * 退出栈中其他所有Activity
     *
     * @param cls Class 类名
     */
    @SuppressWarnings("rawtypes")
    public void popOtherActivity(Class cls) {
        if (null == cls) {
            return;
        }

        for (Activity activity : activityStack) {
            if (null == activity || activity.getClass().equals(cls)) {
                continue;
            }

            activity.finish();
        }
    }

    /**
     * 退出栈中所有Activity
     */
    public void popAllActivity() {
        while (true) {
            Activity activity = currentActivity();
            if (activity == null) {
                break;
            }
            activity.finish();
            popActivity(activity);
        }
    }

//    public void startNextActivity(Class<?> activity)
//    {
//        Activity curActivity = currentActivity();
//        Intent intent = new Intent(curActivity, activity);
//        curActivity.startActivity(intent);
//        curActivity.overridePendingTransition(R.anim.push_right_in, R.anim.push_left_out);
//    }

    public boolean containsActivity(Activity activity) {
        return activityStack.contains(activity);
    }

    public boolean containsActivity(String name) {
        if (activityStack.empty()) {
        } else {
            //得到Stack中的枚举对象
            Enumeration items = activityStack.elements();
            //显示枚举(stack)中的所有元素
            while (items.hasMoreElements()) {
                Activity activity = ((Activity) items.nextElement());
                if (activity.getComponentName().getClassName().equals(name)) {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {

//        synchronized(this) {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
            }
        }
//        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivityLastOne(Class<?> cls) {

//        synchronized(this) {
        int size = (activityStack == null ? 0 : activityStack.size() - 1);
        for (int i = size; i >= 0; i--) {
            if (activityStack.get(i).getClass().equals(cls)) {
                finishActivity(activityStack.get(i));
                return;
            }
        }
//        }
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {

//            activityStack.remove(activity);
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = activityStack.size(); i > 0; i--) {
            if (null != activityStack.get(i - 1)) {
                activityStack.get(i - 1).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 结束除当前activity外的所有activity
     */
    public void finishOtherActivity() {
        for (int i = 0, size = activityStack.size() - 1; i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
    }

    /**
     * 退出应用程序
     */
    @SuppressWarnings("deprecation")
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            //android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            //activityManager.restartPackage(context.getPackageName());
            //System.exit(0);
            //android.os.Process.killProcess(android.os.Process.myPid());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 判断某个界面是否在前台
     *
     * @param activity 要判断的Activity
     * @return 是否在前台显示
     */
    public static boolean isForeground(Activity activity) {
        return isForeground(activity, activity.getClass().getName());
    }

    /**
     * 判断某个界面是否在前台
     *
     * @param context   Context
     * @param className 界面的类名
     * @return 是否在前台显示
     */
    public static boolean isForeground(Context context, String className) {
        if (context == null || TextUtils.isEmpty(className))
            return false;
        ActivityManager am = (ActivityManager) context.getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
        if (list != null && list.size() > 0) {
            ComponentName cpn = list.get(0).topActivity;
            if (className.equals(cpn.getClassName()))
                return true;
        }
        return false;
    }
}

