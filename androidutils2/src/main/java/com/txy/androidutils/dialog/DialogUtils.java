package com.txy.androidutils.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.view.View;

import com.txy.androidutils.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apple on 17/9/10.
 */

public class DialogUtils {

    private Context context;
    private List<Dialog> dialogs = new ArrayList<>();

    public DialogUtils(Context context) {
        this.context = context;
    }

    public void showPermissionDialog(String message) {
        OnClickListener rightListener = new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                intent.setData(Uri.parse("package:"+context.getPackageName()));
                context.startActivity(intent);
            }
        };
        AlertBean alertBean = new AlertBean(null, message, "取消", "去开启", null, rightListener);
        Dialog dialog = createAlertDialog(alertBean);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
    }

    public void showAlertDialog(AlertBean alertBean) {
        createAlertDialog(alertBean).show();
    }

    /**
     * 只有确定按钮的对话框
     */
    public void showOkDialog(String title, String message, final OnClickListener listener){
        AlertHolder holder = new AlertHolder(context);
        final Dialog dialog = createDialog(holder);
        dialog.setCanceledOnTouchOutside(false);
        holder.show(title,message);
        holder.tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(listener != null) listener.onClick(view);
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    private Dialog createAlertDialog(final AlertBean alertBean) {
        AlertHolder holder = new AlertHolder(context);
        final Dialog dialog = createDialog(holder);
        holder.show(alertBean.title, alertBean.message, alertBean.leftStr, alertBean.rightStr);
        holder.tvLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertBean.leftListener != null)
                    alertBean.leftListener.onClick(view);
                dialog.dismiss();
            }
        });
        holder.tvRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (alertBean.rightListener != null)
                    alertBean.rightListener.onClick(view);
                dialog.dismiss();
            }
        });
        return dialog;
    }

    private Dialog createDialog(BaseHolder holder) {
        final Dialog dialog = new Dialog(context, R.style.mydialogstyle);
        dialog.setContentView(holder.contentView);
        dialogs.add(dialog);
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogs.remove(dialog);
            }
        });
        return dialog;
    }

    public void destroy() {
        if (dialogs.size() == 0) return;
        for (Dialog dialog : dialogs) {
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }
}
