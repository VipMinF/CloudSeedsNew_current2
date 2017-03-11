package com.classichu.classichu.basic.helper;

import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

/**
 * Created by louisgeek on 2017/1/19.
 * 解决IllegalStateException: Can not perform this action after onSaveInstanceState
 */

public class DialogFragmentShowHelper {
    /**
     *
     * @param fragmentManager
     * @param dialogFragment
     * @param tag
     */
    public static void show(FragmentManager fragmentManager, DialogFragment dialogFragment, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(dialogFragment,tag);
        transaction.commitAllowingStateLoss();
    }
}
