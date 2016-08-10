package com.liompei.zlog;

import android.util.Log;

/**
 * Created by BLM on 2016/8/10.
 */
public class BaseLog {
    public static void toPrint(int type, String tag, String msg) {

        int index = 0;
        int maxLength = 4000;
        int countOfSub = msg.length() / maxLength;

        if (countOfSub > 0) {
            for (int i = 0; i < countOfSub; i++) {
                String sub = msg.substring(index, index + maxLength);
                printSub(type, tag, sub);
                index += maxLength;
            }
            printSub(type, tag, msg.substring(index, msg.length()));
        } else {
            printSub(type, tag, msg);
        }
    }

    private static void printSub(int type, String tag, String sub) {

        switch (type) {
            case Z.V:
                Log.v(tag, sub);
                break;
            case Z.D:
                Log.d(tag, sub);
                break;
            case Z.I:
                Log.i(tag, sub);
                break;
            case Z.W:
                Log.w(tag, sub);
                break;
            case Z.E:
                Log.e(tag, sub);
                break;
            case Z.WTF:
                Log.wtf(tag, sub);
                break;
        }
    }

}
