package com.txy.androidutils.dialog;

/**
 * Created by Apple on 17/9/10.
 */

public class AlertBean {
    public String title;
    public String message;
    public String leftStr;
    public String rightStr;
    public OnClickListener leftListener;
    public OnClickListener rightListener;

    public AlertBean(String title, String message, String leftStr, String rightStr,
                     OnClickListener leftListener, OnClickListener rightListener) {
        this.title = title;
        this.message = message;
        this.leftStr = leftStr;
        this.rightStr = rightStr;
        this.leftListener = leftListener;
        this.rightListener = rightListener;
    }
}
