package com.txy.androidutils.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.txy.androidutils.R;

/**
 * Created by Apple on 17/9/10.
 */

class AlertHolder extends BaseHolder {
    TextView tvTitle;
    TextView tvMessage;
    TextView tvLeft;
    TextView tvRight;
    LinearLayout llDoubleButton;
    TextView tvOk;

    AlertHolder(Context context) {
        super(context);
        tvTitle = contentView.findViewById(R.id.tv_title);
        tvMessage = contentView.findViewById(R.id.tv_message);
        tvLeft = contentView.findViewById(R.id.tv_left);
        tvRight = contentView.findViewById(R.id.tv_right);
        tvOk = contentView.findViewById(R.id.tv_ok);
        llDoubleButton = contentView.findViewById(R.id.ll_double_button);
    }

    void show(String title, String message) {
        llDoubleButton.setVisibility(View.GONE);
        tvOk.setVisibility(View.VISIBLE);
        show(title,message,null,null);
    }

    void show(String title, String message, String left, String right) {
        tvMessage.setText(message);
        setText(tvLeft,left);
        setText(tvRight,right);
        setText(tvTitle, title);
    }

    public void setText(TextView tv, String str) {
        if (!TextUtils.isEmpty(str))
            tv.setText(str);
    }

    @Override
    public int layoutId() {
        return R.layout.alert_dialog;
    }
}
