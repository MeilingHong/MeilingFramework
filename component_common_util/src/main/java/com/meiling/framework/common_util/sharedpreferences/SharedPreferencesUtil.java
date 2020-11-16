package com.meiling.framework.common_util.sharedpreferences;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.StreamCorruptedException;

/**
 * 使用系统的SharedPreferences进行持久化存储相关信息
 *
 * todo PS：尽量使用基本数据类型进行存储，并且不要存储过多的信息，该方式本身不适合存储过多的信息
 */
public class SharedPreferencesUtil {

    /**
     * 将对象进行BASE64编码存储
     *
     * @param context
     * @param spFile
     * @param spKey
     * @param object
     * @return
     */
    public static boolean saveObject(Context context, String spFile, String spKey, Object object) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(object);
            String base64Str = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
            SharedPreferences shared = context.getApplicationContext()
                    .getSharedPreferences(spFile, Context.MODE_PRIVATE);
            shared.edit().putString(spKey, base64Str).apply();//
            oos.close();
            baos.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 将存储的对象进行BASE64解码，转换成对应的对象
     *
     * @param context
     * @param spFile
     * @param spKey
     * @return
     */
    public static Object readObject(Context context, String spFile, String spKey) {
        if (context == null || TextUtils.isEmpty(spFile) || TextUtils.isEmpty(spKey)) {
            return null;
        }
        try {
            SharedPreferences shared = context.getApplicationContext()
                    .getSharedPreferences(spFile, Context.MODE_PRIVATE);
            String base64Str = shared.getString(spKey, null);
            if (TextUtils.isEmpty(base64Str)) {
                return null;
            }
            byte[] objData = Base64.decode(base64Str, Base64.DEFAULT);
            ByteArrayInputStream bais = new ByteArrayInputStream(objData);
            ObjectInputStream ois = new ObjectInputStream(bais);
            Object obj = ois.readObject();
            bais.close();
            ois.close();
            objData = null;
            return obj;
        } catch (StreamCorruptedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 存储字符串类型的数据
     *
     * @param context
     * @param spFile
     * @param spKey
     * @param saving
     * @return
     */
    public static boolean saveString(Context context, String spFile, String spKey, String saving) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        shared.edit().putString(spKey, saving).apply();//
        return false;
    }

    /**
     * 读取字符串类型的数据
     *
     * @param context
     * @param spFile
     * @param spKey
     * @return
     */
    public static String readString(Context context, String spFile, String spKey) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return shared.getString(spKey, null);
    }

    /**
     * 存储整形的数据
     *
     * @param context
     * @param spFile
     * @param spKey
     * @param saving
     * @return
     */
    public static boolean saveInt(Context context, String spFile, String spKey, int saving) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        shared.edit().putInt(spKey, saving).apply();//
        return false;
    }

    /**
     * 获取整形类型的数据【默认值固定为0】
     *
     * @param context
     * @param spFile
     * @param spKey
     * @return
     */
    public static int readInt(Context context, String spFile, String spKey) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return shared.getInt(spKey, 0);
    }

    /**
     * 获取整形类型的数据【默认值自己进行指定】
     *
     * @param context
     * @param spFile
     * @param spKey
     * @param defaultValue
     * @return
     */
    public static int readInt(Context context, String spFile, String spKey, int defaultValue) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return shared.getInt(spKey, defaultValue);
    }

    /**
     * 存储长整形的数据
     *
     * @param context
     * @param spFile
     * @param spKey
     * @param saving
     * @return
     */
    public static boolean saveLong(Context context, String spFile, String spKey, long saving) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        shared.edit().putLong(spKey, saving).apply();//
        return false;
    }

    /**
     * 读取长整形的数据
     *
     * @param context
     * @param spFile
     * @param spKey
     * @return
     */
    public static long readLong(Context context, String spFile, String spKey) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return shared.getLong(spKey, 0);
    }

    public static boolean saveFloat(Context context, String spFile, String spKey, float saving) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        shared.edit().putFloat(spKey, saving).apply();//
        return false;
    }

    public static float readFloat(Context context, String spFile, String spKey) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return shared.getFloat(spKey, 0);
    }

    public static boolean saveBoolean(Context context, String spFile, String spKey, boolean saving) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        shared.edit().putBoolean(spKey, saving).apply();//
        return false;
    }

    public static boolean readBoolean(Context context, String spFile, String spKey) {
        SharedPreferences shared = context.getApplicationContext()
                .getSharedPreferences(spFile, Context.MODE_PRIVATE);
        return shared.getBoolean(spKey, false);
    }


}
