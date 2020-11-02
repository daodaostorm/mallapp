package com.ran.mall.system;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.ran.library.base.BaseActivity;
import com.ran.library.base.SystemBase;
import com.ran.library.base.SystemManager;
import com.ran.mall.base.BaseActivity_2;
import com.ran.mall.entity.bean.DeviceInfoBean;
import com.ran.mall.entity.bean.PairedBlueToothInfoBean;
import com.ran.mall.entity.bean.UserBean;
import com.ran.mall.https.HttpRequestClient;
import com.ran.mall.utils.ApplicationUtils;
import com.ran.mall.utils.LogUtils;
import com.ran.mall.utils.PreferenceUtils;
import com.ran.mall.widget.EndEvidenceDialog;
import com.ran.mall.widget.HintEvidenceDialog;
import com.ran.mall.widget.ShowIpControlDialog;
import com.ran.mall.widget.ToastDialog;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by DELL on 2017/4/18.
 */
public class SystemCommon extends SystemBase {
    private final String TAG = SystemCommon.class.getSimpleName();
    private CameraManager manager;// 声明CameraManager对象


    public boolean mLoginFirst = true;
    public String mMoblieModle;
    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.READ_EXTERNAL_STORAGE",
            "android.permission.WRITE_EXTERNAL_STORAGE"};

    public boolean mIsUpload = false;
    public Handler mUploadDeviceStatus = new Handler();

    public static String[][] mDamageType = {
            {"carDamage", "车损"},
            {"goodsDamage", "物损"},
            {"peopleDamage", "人伤"}
    };

    public static String[][] mSuveryPlace = {
            {"accident", "事故现场"},
            {"factory", "修理厂"}
    };

    public static String getPlaceKey(String strValue) {
        String returnKey = "";

        if (strValue.equals("") || strValue.equals("请选择")){
            return returnKey;
        }
        for (int index = 0; index< mSuveryPlace.length; index++){
            if (strValue.equals(SystemCommon.mSuveryPlace[index][1])){
                returnKey = SystemCommon.mSuveryPlace[index][0];
                break;
            }
        }

        return returnKey;
    }

    public static String getPlaceValue(String strKey) {
        String returnValue = "";

        if (strKey.equals("") || strKey.equals("请选择")){
            return returnValue;
        }
        for (int index = 0; index< mSuveryPlace.length; index++){
            if (strKey.equals(SystemCommon.mSuveryPlace[index][0])){
                returnValue = SystemCommon.mSuveryPlace[index][1];
                break;
            }
        }

        return returnValue;
    }

    public static String getDamageKey(String strValue) {
        String returnKey = "";

        if (strValue.equals("") || strValue.equals("请选择")){
            return returnKey;
        }
        for (int index = 0; index< mDamageType.length; index++){
            if (strValue.equals(mDamageType[index][1])){
                returnKey = mDamageType[index][0];
                break;
            }
        }

        return returnKey;
    }

    public static String getDamageValue(String strKey) {
        String returnValue = "";

        if (strKey.equals("") || strKey.equals("请选择")){
            return returnValue;
        }
        for (int index = 0; index< mDamageType.length; index++){
            if (strKey.equals(SystemCommon.mDamageType[index][0])){
                returnValue = SystemCommon.mDamageType[index][1];
                break;
            }
        }

        return returnValue;
    }




    public void verifyStoragePermissions(Activity activity) {
        try {
            //检测是否有写的权限
            int permission = ActivityCompat.checkSelfPermission(activity,
                    "android.permission.WRITE_EXTERNAL_STORAGE");
            if (permission != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE, REQUEST_EXTERNAL_STORAGE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isFADMoblie() {

        if (mMoblieModle == null && mMoblieModle.equals("")) {
            return false;
        }
        if (mMoblieModle.toUpperCase().equals("HONOR") || mMoblieModle.toUpperCase().equals("HUAWEI")) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void init() {
        manager = (CameraManager) mContext.getSystemService(Context.CAMERA_SERVICE);
        try {
            String[] camerList = manager.getCameraIdList();

            for (String str : camerList) {
                Log.d(TAG, "init: ");
            }
        } catch (CameraAccessException e) {
            Log.e("error", e.getMessage());
        }

    }

    public void lightSwitch(final boolean lightStatus) {
        if (lightStatus) { // 关闭手电筒
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", false);
                } catch (Exception e) {
                    Log.d(TAG, "lightSwitch: " + e);
                    e.printStackTrace();
                }
            } else {

            }
        } else { // 打开手电筒

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                try {
                    manager.setTorchMode("0", true);
                } catch (Exception e) {
                    Log.d(TAG, "lightSwitch: " + e);
                    e.printStackTrace();
                }
            } else {

            }
        }
    }


    /**
     * 判断Android系统版本是否 >= M(API23)
     */
    private boolean isM() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * dip转pix
     *
     * @param context
     * @param dp
     * @return
     */
    public static int dp2px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static Bitmap savePixels(int x, int y, int w, int h, GL10 gl) {
        int b[] = new int[w * (y + h)];
        int bt[] = new int[w * h];
        IntBuffer ib = IntBuffer.wrap(b);
        ib.position(0);
        gl.glReadPixels(x, 0, w, y + h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
        for (int i = 0, k = 0; i < h; i++, k++) {//remember, that OpenGL bitmap is incompatible with Android bitmap
            for (int j = 0; j < w; j++) {
                int pix = b[i * w + j];
                int pb = (pix >> 16) & 0xff;
                int pr = (pix << 16) & 0x00ff0000;
                int pix1 = (pix & 0xff00ff00) | pr | pb;
                bt[(h - k - 1) * w + j] = pix1;
            }
        }
        Bitmap bp = Bitmap.createBitmap(bt, w, h, Bitmap.Config.ARGB_8888);
        return bp;
    }

    public void screenShot(int width, int heigh, String filepath, ShotScreentCallBack callback) {
        EGL10 egl = (EGL10) EGLContext.getEGL();
        Log.d(TAG, "screenShot: egl" + egl.toString());
        GL10 gl = (GL10) egl.eglGetCurrentContext().getGL();
        Log.d(TAG, "screenShot: egl" + gl.toString());
        Bitmap bp = savePixels(0, 0, width, heigh, gl);
        savePic(bp, filepath, callback);
    }


    // 保存到sdcard
    public void savePic(Bitmap b, String strFileName, ShotScreentCallBack callback) {
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(strFileName);
            if (null != fos) {
                b.compress(Bitmap.CompressFormat.PNG, 90, fos);
                b.recycle();
                fos.flush();
                fos.close();
                if (callback != null) {
                    callback.shotscrren(strFileName);
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            if (callback != null) {
                callback.shotscrren(null);
            }
        } catch (IOException e) {
            e.printStackTrace();
            callback.shotscrren(null);
        }
    }

    public interface ShotScreentCallBack {
        public void shotscrren(String path);
    }


    //退出应用
    public void exitApp() {
        SystemManager.getInstance().destoryAllSystem();
//        android.os.Process.killProcess(android.os.Process.myPid());//获取PID
        System.exit(0);
    }

    public void logoutApp(){
        SystemManager.getInstance().destoryAllSystem();
        ApplicationUtils.finishActivity();
    }

    public boolean matchCarNumber(String carNumber) {
        String carRule = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领A-Z]{1}[A-Z]{1}[警京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼]{0,1}[A-Z0-9]{4}[A-Z0-9挂学警港澳]{1}$";
        String bigCarNumber = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-HJ-NP-Z]{1}[0-9]{5}[DF]{1}$";
        String smallCarnumber = "[京津沪渝冀豫云辽黑湘皖鲁新苏浙赣鄂桂甘晋蒙陕吉闽贵粤青藏川宁琼使领]{1}[A-HJ-NP-Z]{1}[DF]{1}[A-Z0-9]{1}[0-9]{4}$";
        String carRule1 = "([0-9A-Z]){17}$";

        if (TextUtils.isEmpty(carNumber)) {
            return false;
        }
        return carNumber.matches(carRule) || carNumber.matches(carRule1) || carNumber.matches(bigCarNumber) || carNumber.matches(smallCarnumber);
    }


    public String imageToBase64(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        InputStream is = null;
        byte[] data = null;
        String result = null;
        try {
            is = new FileInputStream(path);
            //创建一个字符流大小的数组。
            data = new byte[is.available()];
            //写入数组
            is.read(data);
            //用默认的编码格式进行编码
            result = Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != is) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return result;
    }

    public String byteToBase64(byte[] bytes) {

        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String result = new String(encode);
        return result;
    }

    public String imgToBase64(String imageptah) {
        Bitmap bitmap = BitmapFactory.decodeFile(imageptah);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        byte[] encode = Base64.encode(bytes, Base64.DEFAULT);
        String result = new String(encode);
        return result;
    }


    public int getScreenWidth(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int width = metrics.widthPixels;
        return width;
    }

    public int getScreenHigh(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getRealMetrics(metrics);
        int heigh = metrics.heightPixels;
        return heigh;
    }

    //
    private ToastDialog mDialog;

    public void showToastDialog(Context context, String msg, final ToastDialog.onDialogListenerCallBack callBack) {
        if (mDialog == null) {
            mDialog = new ToastDialog(context, msg);
            mDialog.setCanceledOnTouchOutside(true);
            mDialog.setOnDialohClickListener(new ToastDialog.onDialogListenerCallBack() {
                @Override
                public void onOkCliclck() {
                    if (callBack != null) {
                        callBack.onOkCliclck();
                    }
                    if (mDialog != null)
                        mDialog = null;
                }

                @Override
                public void onCancleClick() {
                    if (callBack != null) {
                        callBack.onCancleClick();
                    }
                    if (mDialog != null)
                        mDialog = null;
                }
            });
            mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialog) {
                    if (mDialog != null) {
                        mDialog = null;
                    }
                }
            });
            mDialog.show();
        }

    }


    public void dissToastDialog() {
        if (mDialog != null) {
            mDialog.dismiss();
            mDialog = null;
        }
    }


    public interface DialogCallBack {
        public void onConfrim(ArrayList<String> optionsItems);
    }


    public void ShowSuveyPlaceChoose(Context context, String defaultValue, DialogCallBack callBack) {

        //String []stringArray = {};
        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < mSuveryPlace.length; i++){
            list.add(mSuveryPlace[i][1]);
        }
        String []stringArray= list.toArray(new String[list.size()]);
        int checkedItem = 0;

        for (int i = 0; i < stringArray.length; i++){
            if (defaultValue.equals(stringArray[i])){
                checkedItem = i;
                break;
            }
        }

        AlertDialog.Builder builder=new AlertDialog.Builder(context);
        builder.setTitle("");
        ArrayList<String> resultArray = new ArrayList<String>();
        resultArray.add(stringArray[checkedItem]);
        builder.setSingleChoiceItems(stringArray, checkedItem, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resultArray.clear();
                resultArray.add(stringArray[which]);
                callBack.onConfrim(resultArray);
            }
        });

        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.onConfrim(resultArray);
            }
        });
        AlertDialog dialog= builder.create();

        WindowManager mg = (WindowManager)context.getSystemService(Context.WINDOW_SERVICE);


        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = mg.getDefaultDisplay().getWidth();
        window.setAttributes(lp);

        dialog.show();
    }

    public void ShowDamageNameChoose(Context context, DialogCallBack callBack) {

        //String []stringArray = {"车损","物损","人伤"};

        ArrayList<String> list = new ArrayList<String>();
        for (int i = 0; i < mDamageType.length; i++){
            list.add(mDamageType[i][1]);
        }
        String []stringArray= list.toArray(new String[list.size()]);

        ArrayList<String> resultArray = new ArrayList<String>();

        ArrayList<String> optionsItems = new ArrayList<>();
        for (int i = 0; i < stringArray.length; i++) {
            optionsItems.add(stringArray[i]);
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("");
        builder.setMultiChoiceItems(stringArray, null, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                Boolean isExist = false;
                if (isChecked) {
                    for (int i = 0; i < resultArray.size(); i++) {
                        if (stringArray[which].equals(resultArray.get(i))) {
                            isExist = true;
                            break;
                        }
                    }
                    if (!isExist) {
                        resultArray.add(stringArray[which]);
                    }
                } else {
                    for (int i = 0; i < resultArray.size(); i++) {
                        if (stringArray[which].equals(resultArray.get(i))) {
                            resultArray.remove(i);
                            break;
                        }
                    }
                }
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.onConfrim(resultArray);
            }
        });
        AlertDialog dialog = builder.create();

        WindowManager mg = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);


        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = mg.getDefaultDisplay().getWidth();
        window.setAttributes(lp);

        dialog.show();
    }



    //判断网络的连接状态
    public boolean isNetworkAvailable(final Context context) {
        boolean hasWifoCon = false;
        boolean hasMobileCon = false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfos = cm.getAllNetworkInfo();
        for (NetworkInfo net : netInfos) {
            String type = net.getTypeName();
            if (type.equalsIgnoreCase("WIFI")) {
                if (net.isConnected()) {
                    hasWifoCon = true;
                }
            }
            if (type.equalsIgnoreCase("MOBILE")) {
                if (net.isConnected()) {
                    hasMobileCon = true;
                }
            }
        }
        return hasWifoCon || hasMobileCon;
    }

    public void showIPDialog(Context context, final ShowIpControlDialog.onDialogListenerCallBack callBack) {
        ShowIpControlDialog dialog = new ShowIpControlDialog(context);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnDialohClickListener(new ShowIpControlDialog.onDialogListenerCallBack() {
            @Override
            public void onOkCliclck(String ip, String port) {
                if (callBack != null)
                    callBack.onOkCliclck(ip, port);
            }
        });
        dialog.show();
    }


    public boolean isOritation(Context context) {
        int flag = 0;
        try {
            int screenchange = Settings.System.getInt(context.getContentResolver(), Settings.System.ACCELEROMETER_ROTATION);
            flag = screenchange;
        } catch (Settings.SettingNotFoundException e) {
            e.printStackTrace();
        }
        return flag == 0 ? false : true;
    }


    /**
     * 显示dialog
     *
     * @param context
     * @param callBack
     */
    public void ShowEndEvidenceDialog(Context context, final EndEvidenceDialog.onCommDilogCallBack callBack) {
        EndEvidenceDialog dialog = new EndEvidenceDialog(context, callBack);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (callBack != null) {
                    callBack.onCancleDialog();
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (callBack != null) {
                    callBack.onCancleDialog();
                }
            }
        });
        dialog.show();
    }

    public void ShowHintEvidenceDialog(Context context, final HintEvidenceDialog.onCommDilogCallBack callBack) {
        HintEvidenceDialog dialog = new HintEvidenceDialog(context, callBack);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                if (callBack != null) {
                    callBack.onCancleDialog();
                }
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (callBack != null) {
                    callBack.onCancleDialog();
                }
            }
        });
        dialog.show();
    }

    private static Bitmap getBitmapFromGL(int w, int h, GL10 gl) {
        int b[] = new int[w * (h)];
        int bt[] = new int[w * h];
        IntBuffer ib = IntBuffer.wrap(b);
        ib.position(0);

        gl.glFlush();
        gl.glReadPixels(0, 0, w, h, GL10.GL_RGBA, GL10.GL_UNSIGNED_BYTE, ib);
        for (int i = 0, k = 0; i < h; i++, k++) {
            for (int j = 0; j < w; j++) {
                int pix = b[i * w + j];
                int pb = (pix >> 16) & 0xff;
                int pr = (pix << 16) & 0xffff0000;
                int pix1 = (pix & 0xff00ff00) | pr | pb;
                bt[(h - k - 1) * w + j] = pix1;
            }
        }
        return Bitmap.createBitmap(bt, w, h, Bitmap.Config.RGB_565);
    }

    public String saveBitmap(Bitmap bitmap) {
        if (isBlack(bitmap)) {
            return null;
        }
        String fileDir;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            fileDir = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "piccPhoto";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            fileDir = mContext.getFilesDir().getAbsolutePath()
                    + File.separator + "piccPhoto";
        }
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }
        File picFile = new File(file, "Assess_shot_" + System.currentTimeMillis() + ".jpg");
        Log.d(TAG, "saveBitmap: picFile" + picFile.getAbsolutePath());
        try {
            FileOutputStream out = new FileOutputStream(picFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.flush();
            out.close();
            FileInputStream inputStream = new FileInputStream(picFile);
            int size = inputStream.available() / 1024;
            Log.d(TAG, "saveBitmap: size" + size);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            picFile = null;
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            picFile = null;
        }
        if (picFile == null) {
            return null;
        }
        return picFile.getAbsolutePath();
    }


    public interface onRequestCallBack {
        public void onSuccess();

        public void onFail(String msg);
    }

    public boolean isBlack(Bitmap bitmap) {
        int width = bitmap.getWidth();
        int hight = bitmap.getHeight();
        for (int i = 2; i < 6; i++) {
            int color = bitmap.getPixel(width / i, hight / i);
            int r = Color.red(color);
            int g = Color.green(color);
            int b = Color.blue(color);
            Log.d(TAG, "isBlack: r" + r + ";g" + g + ";b" + b);
            if (r != 0 || g != 0 || b != 0) {
                return false;
            }
        }
        return true;
    }
    public byte saveBitmapByte(Bitmap bitmap)[] {
        if (isBlack(bitmap)) {
            return null;
        }
        String fileDir;
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {// 优先保存到SD卡中
            fileDir = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + File.separator + "piccPhoto";
        } else {// 如果SD卡不存在，就保存到本应用的目录下
            fileDir = mContext.getFilesDir().getAbsolutePath()
                    + File.separator + "piccPhoto";
        }
        File file = new File(fileDir);
        if (!file.exists()) {
            file.mkdirs();
        }

        File picFile = new File(file, "Assess_shot_" + System.currentTimeMillis() + ".jpg");

        Log.d(TAG, "saveBitmap: picFile" + picFile.getAbsolutePath());
        byte[] mBytes = null;
        try {


            FileOutputStream out = new FileOutputStream(picFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, out);
            out.flush();
            out.close();

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, baos);
            mBytes = baos.toByteArray();

            FileInputStream inputStream = new FileInputStream(picFile);
            int size = inputStream.available() / 1024;
            Log.d(TAG, "saveBitmap: size" + size);

        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            picFile = null;
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            picFile = null;
        }
        if (picFile == null) {
            return null;
        }
        return mBytes;
    }

}
