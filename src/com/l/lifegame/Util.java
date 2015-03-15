package com.l.lifegame;

import android.content.Context;
import android.widget.Toast;


public class Util {

	/**
	 * �����ǰ�һ���ַ����л�Ϊһ��������������쳣����Ĭ��ֵ������
	 * 
	 * @param str
	 * @param defaultValue
	 * @return
	 */
	public static int StringToInt(String str, int defaultValue) {
		int value = defaultValue;// Ĭ��ֵ
		try {
			value = Integer.parseInt(str);
		} catch (Exception e) {
			value = defaultValue;
		}
//		Log.i(TAG, "StringToInt str=" + str + ",defaultValue=" + defaultValue + ",value=" + value);
		return value;
	}
	
	/**����Toast*/
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
