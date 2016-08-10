package com.liompei.zlog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.liompei.zlog.BaseLog;
import com.liompei.zlog.JsonLog;
import com.liompei.zlog.XmlLog;

/**
 * Created by BLM on 2016/8/10.
 *
 * @author liompei
 *         本库参考凯子哥(github: ZhaoKaiQiang)的KLog
 *         KLog:  github https://github.com/ZhaoKaiQiang/KLog
 *         2016/8/10 create
 *         去除Log信息保存功能
 */
public class Z {

    private static boolean IS_SHOW_LOG = true;  //是否打印日志,默认打印
    private static boolean IS_SHOW_TOAST = true;  //是否显示toast,默认显示
    private static String TAG;  //标签
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final int V = 0x1;  //verbose
    public static final int D = 0x2;  //debug
    public static final int I = 0x3;  //information
    public static final int W = 0x4;  //warning
    public static final int E = 0x5;  //error

    public static final int WTF = 0x6;  //What a Terrible Failure
    public static final int JSON = 0x7;
    public static final int XML = 0x8;

    public static final int JSON_INDENT = 4;

    public static final String DEFAULT_MESSAGE = "execute";  //执行,默认输出字符
    public static final String TAG_DEFAULT = "Z--log";  //默认tag
    public static final String NULL_TIPS = "Log`s object is null";


    private static final int STACK_TRACE_INDEX = 5;  //??
    private static final String SUFFIX = ".java";  //类后缀

    private static boolean mIsGlobalTagEmpty = true;  //TAG是否无值

    private static Context CONTEXT;

    public static void initLog(String tag,boolean isShowlog) {
        IS_SHOW_LOG = isShowlog;
        TAG = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(TAG);  //判断传入的值是否为空,为空返回true
    }

    public static void initToast(Context context,boolean isShowToast) {
        IS_SHOW_TOAST=isShowToast;
        CONTEXT = context;
    }

    /**
     * V verbose
     */
    public static void v() {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object msg) {
        printLog(V, null, msg);
    }

    public static void v(String tag, Object msg) {
        printLog(V, tag, msg);
    }

    /**
     * D debug
     */
    public static void d() {
        printLog(D, null, DEFAULT_MESSAGE);
    }

    public static void d(Object msg) {
        printLog(D, null, msg);
    }

    public static void d(String tag, Object msg) {
        printLog(D, tag, msg);
    }

    /**
     * I information
     */

    public static void i() {
        printLog(I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object msg) {
        printLog(I, null, msg);
    }

    public static void i(String tag, Object msg) {
        printLog(I, tag, msg);
    }

    /**
     * W warning
     */

    public static void w() {
        printLog(W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object msg) {
        printLog(W, null, msg);
    }

    public static void w(String tag, Object msg) {
        printLog(W, tag, msg);
    }

    /**
     * e error
     */

    public static void e() {
        printLog(E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object msg) {
        printLog(E, null, msg);
    }

    public static void e(String tag, Object msg) {
        printLog(E, tag, msg);
    }

    /**
     * A
     */
    public static void wtf() {
        printLog(WTF, null, DEFAULT_MESSAGE);
    }

    public static void wtf(Object wtf) {
        printLog(WTF, null, wtf);
    }

    public static void wtf(String tag, Object wtf) {
        printLog(WTF, tag, wtf);
    }

    /**
     * json数据
     *
     * @param jsonData
     */
    public static void json(String jsonData) {
        printLog(JSON, null, jsonData);
    }

    public static void json(String tag, String jsonData) {
        printLog(JSON, tag, jsonData);
    }

    /**
     * xml解析
     *
     * @param xml
     */

    public static void xml(String xml) {
        printLog(XML, null, xml);
    }

    public static void xml(String tag, String xml) {
        printLog(XML, tag, xml);
    }

    public static void show(String toast) {
        if (!IS_SHOW_TOAST){
            return;
        }

        try {
            Toast.makeText(CONTEXT, toast, Toast.LENGTH_SHORT).show();
        }catch (NullPointerException e){
            Log.e("Zlog--","---------------------------------------------");
            Log.e("Zlog--error",e.getMessage());
            Log.e("Zlog--suggest","please add Context with Z.initToast() ");
            Log.e("Zlog--","---------------------------------------------");
        }

    }

    private static void printLog(int type, String tagStr, Object object) {
        if (!IS_SHOW_LOG) {
            return;
        }

        String[] contents = wrapperContent(tagStr, object);
        String tag = contents[0];
        String msg = contents[1];
        String headString = contents[2];

        switch (type) {
            case V:
            case D:
            case I:
            case W:
            case E:
            case WTF:
                BaseLog.toPrint(type, tag, headString + msg);
                break;
            case JSON:
                JsonLog.printJson(tag, msg, headString);
                break;
            case XML:
                XmlLog.printXml(tag, msg, headString);
                break;
        }

    }


    private static String[] wrapperContent(String tagStr, Object object) {
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();
        StackTraceElement targetElement = stackTrace[STACK_TRACE_INDEX];
        String className = targetElement.getClassName();  //获得当前类名
        String[] classNameInfo = className.split("\\.");  //出现 . 的地方分隔成数组
        if (classNameInfo.length > 0) {
            //如果是内部类,取数组第一个类名
            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }
        if (className.contains("$")) {  //判断是否包含
            className = className.split("\\$")[0] + SUFFIX;  //取分组后的第一位作为类
        }

        String methodName = targetElement.getMethodName();  //所在的方法名
        int lineNumber = targetElement.getLineNumber();  //所在行号
        if (lineNumber < 0) {
            lineNumber = 0;
        }
        //方法首字母大写(隐藏)
//        String methodNameShort=methodName.substring(0,1).toUpperCase()+methodName.substring(1);

        //tagStr为空则显示方法名,不为空显示输入的tag
        String tag = (tagStr == null ? className : tagStr);

        //若没有初始化tag和输入的tag为空
        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tagStr)) {
            tag = TAG_DEFAULT;
        } else if (!TextUtils.isEmpty(tagStr)) {
            //若中途为TAG赋值
            tag =tagStr;
        } else if (!mIsGlobalTagEmpty && TextUtils.isEmpty(tagStr)) {
            tag = TAG;
        }
        //若无显示预设,若有则进行解析
        String msg = (object == null) ? NULL_TIPS : getObjectsString(object);

        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }


    private static String getObjectsString(Object object) {

        return object.toString();

    }

}
