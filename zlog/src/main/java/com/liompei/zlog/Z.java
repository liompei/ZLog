package com.liompei.zlog;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by BLM on 2016/8/10.
 *
 * @author liompei
 *
 *         KLog:  github https://github.com/ZhaoKaiQiang/KLog
 *         2016/8/10 create
 *         去除Log信息保存功能,优化了初始化操作
 *
 */
public class Z {

    private static boolean IS_SHOW_LOG = true;
    private static boolean IS_SHOW_TOAST = true;
    private static String TAG;
    public static final String LINE_SEPARATOR = System.getProperty("line.separator");

    public static final int V = 0x1;
    public static final int D = 0x2;
    public static final int I = 0x3;
    public static final int W = 0x4;
    public static final int E = 0x5;

    public static final int WTF = 0x6;
    public static final int JSON = 0x7;
    public static final int XML = 0x8;

    public static final int JSON_INDENT = 4;

    public static final String DEFAULT_MESSAGE = "execute";
    public static final String TAG_DEFAULT = "Z--log";
    public static final String NULL_TIPS = "Log`s object is null";


    private static final int STACK_TRACE_INDEX = 5;
    private static final String SUFFIX = ".java";

    private static boolean mIsGlobalTagEmpty = true;

    private static Context CONTEXT;

    public static void initLog(String tag, boolean isShowlog) {
        IS_SHOW_LOG = isShowlog;
        TAG = tag;
        mIsGlobalTagEmpty = TextUtils.isEmpty(TAG);
    }

    public static void initToast(Context context, boolean isShowToast) {
        IS_SHOW_TOAST = isShowToast;
        CONTEXT = context;
    }

    /**
     * V verbose
     */
    public static void v() {
        printLog(V, null, DEFAULT_MESSAGE);
    }

    public static void v(Object verbose) {
        printLog(V, null, verbose);
    }

    public static void v(String tag, Object verbose) {
        printLog(V, tag, verbose);
    }

    /**
     * D debug
     */
    public static void d() {
        printLog(D, null, DEFAULT_MESSAGE);
    }

    public static void d(Object debug) {
        printLog(D, null, debug);
    }

    public static void d(String tag, Object debug) {
        printLog(D, tag, debug);
    }

    /**
     * I information
     */

    public static void i() {
        printLog(I, null, DEFAULT_MESSAGE);
    }

    public static void i(Object information) {
        printLog(I, null, information);
    }

    public static void i(String tag, Object information) {
        printLog(I, tag, information);
    }

    /**
     * W warning
     */

    public static void w() {
        printLog(W, null, DEFAULT_MESSAGE);
    }

    public static void w(Object warning) {
        printLog(W, null, warning);
    }

    public static void w(String tag, Object warning) {
        printLog(W, tag, warning);
    }

    /**
     * e error
     */

    public static void e() {
        printLog(E, null, DEFAULT_MESSAGE);
    }

    public static void e(Object error) {
        printLog(E, null, error);
    }

    public static void e(String tag, Object error) {
        printLog(E, tag, error);
    }

    /**
     * wtf
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
     * json
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
     * xml
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
        if (!IS_SHOW_TOAST) {
            return;
        }

        try {
            Toast.makeText(CONTEXT, toast, Toast.LENGTH_SHORT).show();
        } catch (NullPointerException e) {
            Log.e("Zlog--", "---------------------------------------------");
            Log.e("Zlog--error", e.getMessage());
            Log.e("Zlog--suggest", "please add Context with Z.initToast() ");
            Log.e("Zlog--", "---------------------------------------------");
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
        String className = targetElement.getClassName();
        String[] classNameInfo = className.split("\\.");
        if (classNameInfo.length > 0) {

            className = classNameInfo[classNameInfo.length - 1] + SUFFIX;
        }
        if (className.contains("$")) {
            className = className.split("\\$")[0] + SUFFIX;
        }

        String methodName = targetElement.getMethodName();
        int lineNumber = targetElement.getLineNumber();
        if (lineNumber < 0) {
            lineNumber = 0;
        }



        String tag = (tagStr == null ? className : tagStr);

        if (mIsGlobalTagEmpty && TextUtils.isEmpty(tagStr)) {
            tag = TAG_DEFAULT;
        } else if (!TextUtils.isEmpty(tagStr)) {

            tag = tagStr;
        } else if (!mIsGlobalTagEmpty && TextUtils.isEmpty(tagStr)) {
            tag = TAG;
        }

        String msg = (object == null) ? NULL_TIPS : getObjectsString(object);

        String headString = "[ (" + className + ":" + lineNumber + ")#" + methodName + " ] ";

        return new String[]{tag, msg, headString};
    }


    private static String getObjectsString(Object object) {

        return object.toString();

    }

}
