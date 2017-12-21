package com.txy.androidutils.dialog;

import android.content.Context;
import android.view.View;

/**
 * Created by Apple on 17/9/10.
 */

public abstract class BaseHolder {
    View contentView;

   public BaseHolder(Context context) {
        contentView = View.inflate(context, layoutId(), null);
    }

    public abstract int layoutId();
}
