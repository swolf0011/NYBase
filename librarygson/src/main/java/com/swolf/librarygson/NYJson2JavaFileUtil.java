package com.swolf.librarygson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class NYJson2JavaFileUtil {
    private static List<String> listClassName = new ArrayList<>();

    public static class MyAttributeInfo {
        public String className;
        public String attributeName;
    }

    public static class MyClassInfo {
        public String className;
        public String packageName;
        public Set<String> imports = new HashSet<>();
        public List<MyAttributeInfo> attributes = new ArrayList<>();
        public List<MyClassInfo> myClassInfoList = new ArrayList<>();
    }

    public static void clearListClassName() {
        listClassName.clear();
    }
    public static void myClassInfoToString(List<String> strList, MyClassInfo myClassInfo, String saveDirPath) {


        StringBuffer sb = new StringBuffer();

        sb.append("package " + myClassInfo.packageName + ";\n");

        for (String item : myClassInfo.imports) {
            sb.append(item);
            sb.append("\n");
        }

        sb.append("public class " + myClassInfo.className + "{\n");


        for (MyAttributeInfo item : myClassInfo.attributes) {
            sb.append("\tpublic " + item.className + " " + item.attributeName + ";\n");
        }
        sb.append("\n");
        for (MyAttributeInfo item : myClassInfo.attributes) {
            String s0 = "";
            if (item.attributeName.length() > 0) {
                s0 = item.attributeName.substring(0, 1).toUpperCase();
            }
            String s1 = "";
            if (item.attributeName.length() > 1) {
                s1 = item.attributeName.substring(1);
            }
            String fName = s0 + s1;

            sb.append("\tpublic void set" + fName + "(" + item.className + " " + item.attributeName + "){");
            sb.append(" this." + item.attributeName + " = " + item.attributeName + "; }\n");

            sb.append("\tpublic " + item.className + " get" + fName + "(){");
            sb.append(" return " + item.attributeName + "; }\n");
        }


        sb.append("}\n");
        sb.append("\n");

        for (MyClassInfo item : myClassInfo.myClassInfoList) {
            myClassInfoToString(strList, item, saveDirPath);
        }

        if (listClassName.contains(myClassInfo.className)) {

        }else{
            listClassName.add(myClassInfo.className);
            strList.add(sb.toString());
        }
    }

    public static MyClassInfo jsonObjectToMyClassInfo(String packageName, String className, JSONObject obj0) {
        MyClassInfo myClassInfo = new MyClassInfo();
        myClassInfo.packageName = packageName;
        myClassInfo.className = className;
        Iterator<String> it = obj0.keys();
        while (it.hasNext()) {
            String key = it.next();
            String s0 = "";
            if (key.length() > 0) {
                s0 = key.substring(0, 1).toUpperCase();
            }
            String s1 = "";
            if (key.length() > 1) {
                s1 = key.substring(1);
            }
            String cn = s0 + s1;
            try {
                Object obj = obj0.get(key);
                if (obj instanceof Integer) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = "Integer";
                    myAttributeInfo.attributeName = key;
                    myClassInfo.attributes.add(myAttributeInfo);
                } else if (obj instanceof String) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = "String";
                    myAttributeInfo.attributeName = key;
                    myClassInfo.attributes.add(myAttributeInfo);
                } else if (obj instanceof Double) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = "Double";
                    myAttributeInfo.attributeName = key;
                    myClassInfo.attributes.add(myAttributeInfo);
                } else if (obj instanceof Boolean) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = "Boolean";
                    myAttributeInfo.attributeName = key;
                    myClassInfo.attributes.add(myAttributeInfo);
                } else if (obj instanceof Long) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = "Long";
                    myAttributeInfo.attributeName = key;
                    myClassInfo.attributes.add(myAttributeInfo);
                } else if (obj instanceof JSONObject) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = cn;
                    myAttributeInfo.attributeName = key;
                    myClassInfo.imports.add("import " + packageName + "." + cn + ";");
                    myClassInfo.attributes.add(myAttributeInfo);
                    JSONObject jsonObj = (JSONObject) obj;
                    myClassInfo.myClassInfoList.add(jsonObjectToMyClassInfo(packageName, cn, jsonObj));
                } else if (obj instanceof JSONArray) {
                    MyAttributeInfo myAttributeInfo = new MyAttributeInfo();
                    myAttributeInfo.className = "List<" + cn + ">";
                    myAttributeInfo.attributeName = key;
                    myClassInfo.imports.add("import java.util.List;");
                    myClassInfo.attributes.add(myAttributeInfo);
                    JSONArray jsonArray = (JSONArray) obj;
                    if (jsonArray.length() > 0) {
                        try {
                            boolean isOneJsonObject = true;
                            Object objTemp = null;
                            for (int i = 0; i < jsonArray.length() - 1; i++) {
                                Object objI = jsonArray.get(i);
                                if (objI instanceof JSONObject) {
                                    if (objTemp != null && !objI.getClass().equals(objTemp.getClass())) {
                                        isOneJsonObject = false;
                                        break;
                                    }
                                    objTemp = objI;
                                } else {
                                    isOneJsonObject = false;
                                    break;
                                }
                            }
                            if (isOneJsonObject) {
                                JSONObject objI = jsonArray.getJSONObject(0);
                                myClassInfo.myClassInfoList.add(jsonObjectToMyClassInfo(packageName, cn, objI));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return myClassInfo;
    }


}
