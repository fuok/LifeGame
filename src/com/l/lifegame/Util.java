package com.l.lifegame;

import android.content.Context;
import android.widget.Toast;


public class Util {

	/**
	 * 作用是把一个字符串切换为一个整形数，如果异常则以默认值来返回
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int StringToInt(String str, int defaultValue) {
		int value = defaultValue;// 默认值
		try {
			value = Integer.parseInt(str);
		} catch (Exception e) {
			value = defaultValue;
		}
//		Log.i(TAG, "StringToInt str=" + str + ",defaultValue=" + defaultValue + ",value=" + value);
		return value;
	}
	
	/**快速Toast*/
    public static Toast mToast;

    public static void showToast(Context context, String msg, int duration) {
            // if (mToast != null) {
            // mToast.cancel();
            // }
            // mToast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
            if (mToast == null) {
                    mToast = Toast.makeText(context, msg, duration);
            } else {
                    mToast.setText(msg);
            }
            mToast.show();
    }

}
