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
    TextView tvOneOk;

    AlertHolder(Context context) {
        super(context);
        tvTitle = (TextView) contentView.findViewById(R.id.tv_title);
        tvMessage = (TextView) contentView.findViewById(R.id.tv_message);
        tvLeft = (TextView) contentView.findViewById(R.id.tv_left);
        tvRight = (TextView) contentView.findViewById(R.id.tv_right);
        tvOneOk = (TextView) contentView.findViewById(R.id.tv_one_ok);
        llDoubleButton = (LinearLayout) contentView.findViewById(R.id.ll_double_button);
    }

    void show(String title, String message) {
        llDoubleButton.setVisibility(View.GONE);
        tvOneOk.setVisibility(View.VISIBLE);
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
        return R.layout.layout1_alert_dialog;
    }
}
