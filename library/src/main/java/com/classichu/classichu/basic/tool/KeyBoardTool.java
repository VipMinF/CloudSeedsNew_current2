/**
 * @version 1.0  2015年1月27日
 */
package com.classichu.classichu.basic.tool;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 键盘操作工具类
 * @author louisgeek
 * 2016年9月22日13:21:20
 */
public class KeyBoardTool {

	//比较好
	public static void hideKeyboard(Activity activity){
		InputMethodManager imm =  (InputMethodManager)activity.getSystemService(Context.INPUT_METHOD_SERVICE);
		if(imm != null) {
			imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(),0);
		}
	}
	//可以试试
	public static void hideKeyboard2(Activity activity){
		InputMethodManager inputMethodManager = (InputMethodManager)activity
				.getSystemService(Context.INPUT_METHOD_SERVICE);
		View focus = activity.getCurrentFocus();
		if(focus != null)
			inputMethodManager.hideSoftInputFromWindow(focus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
	}
	//隐藏虚拟键盘
	public static void hideKeyboard(View v)
	{
		InputMethodManager imm = ( InputMethodManager ) v.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		if (imm.isActive()) {
			imm.hideSoftInputFromWindow( v.getApplicationWindowToken(),0);

		}
	}



	//显示虚拟键盘
	public static void showKeyboard(View v)
	{
		InputMethodManager imm = ( InputMethodManager ) v.getContext( ).getSystemService( Context.INPUT_METHOD_SERVICE );

		imm.showSoftInput(v,InputMethodManager.SHOW_FORCED);

	}
}
