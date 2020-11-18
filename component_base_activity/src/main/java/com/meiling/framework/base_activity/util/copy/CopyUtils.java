package com.meiling.framework.base_activity.util.copy;

import android.content.ClipboardManager;
import android.content.Context;


public class CopyUtils {
    /**
     * todo 将指定信息复制到剪贴板中
     *
     * @param context
     * @param text
     */
    public static void copyText(Context context, String text){
        ClipboardManager clipboardManager = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        clipboardManager.setText(text);
    }
}
