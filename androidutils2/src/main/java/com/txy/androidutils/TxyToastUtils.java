package com.txy.androidutils;

import android.content.Context;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Apple on 17/9/10.
 */

public class TxyToastUtils {

    private Context context;
    private List<Toast> toasts = new ArrayList<>();

    public TxyToastUtils(Context context) {
        this.context = context;
    }

    public void toastCenterStr(String str) {
        Toast toast = createToast(str);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }

    public void toastCenterResId(int resId) {
        toastCenterStr(context.getString(resId));
    }

    public void toast(String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }

    public void toast(int resId) {
        toast(context.getString(resId));
    }

    private Toast createToast(String str) {
        Toast toast = Toast.makeText(context, str, Toast.LENGTH_SHORT);
        toasts.add(toast);
        return toast;
    }

    public void toastNoNetwork() {
        Toast toast = new Toast(context);
        toast.setGravity(Gravity.CENTER, 0, 0);
        ImageView imageView = new ImageView(context);
        imageView.setImageResource(R.drawable.nowangluo);
        toast.setView(imageView);
        toast.setDuration(Toast.LENGTH_SHORT);
        toasts.add(toast);
        toast.show();
    }

    public void destroy() {
        if (toasts.size() == 0) return;
        for (Toast toast : toasts) {
            if (toast != null) toast.cancel();
        }
    }
}
