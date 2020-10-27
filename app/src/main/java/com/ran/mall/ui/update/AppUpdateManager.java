package com.ran.mall.ui.update;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.app.NotificationCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RemoteViews;
import android.widget.TextView;

import com.ran.mall.BuildConfig;
import com.ran.mall.R;
import com.ran.mall.entity.bean.UpdateModel;
import com.ran.mall.utils.ApkUtils;
import com.ran.mall.utils.AppUtils;
import com.ran.mall.utils.FileUtils;
import com.ran.mall.utils.ToastUtils;
import com.ran.mall.widget.UpdateDialog;

import java.io.File;

/**
 * Created by owner on 2016/8/26.
 */
public class AppUpdateManager {

    private Context mContext;
    private Class mClass;
    // 版本号
    private String versionName = "12";
    // 下载地址
    private String downloadUrl = "http://shouji.360tpcdn.com/160608/573774d184321cb2319b7bc2834e7068/com.qihoo360.mobilesafe_243.apk";

    public static final int MSG_DOWNLOAD_START = 2;
    // 下载进度
    public static final int MSG_DOWNLOAD_PROGRESS = 0;
    // 下载结果
    public static final int MSG_DOWNLOAD_RESULT = 1;

    // 下载成功
    public static final int FLAG_DOWNLOAD_SUCCESS = 0;
    // 取消下载
    public static final int FLAG_CANCEL_UPDATE = -1;
    // 空间不足
    public static final int FLAG_NO_ENOUGH_SPACE = 1;
    // 下载失败或者写入失败
    public static final int FLAG_DOWNLOAD_ERROR = 2;
    // handler用到的key
    public static final String KEY_DOWNLOAD_RESULT = "downloadResult";
    public static final String KEY_FILENAME = "fileName";
    public static final String KEY_FILE_SIZE = "fileSize";
    public static final String KEY_DOWNLOAD_FILE_SIZE = "currentfileSize";
    public static final String KEY_PERCENT = "percent";


    private static AppUpdateManager instance;

    // 定义更新服务
    private AppUpdateService updateService = null;
    // 更新服务连接
    private ServiceConnection updateServiceConnection = null;

    // 进度对话框
    private AlertDialog progressDialog = null;


    // 是否已绑定服务
    private boolean isBinded = false;

    // 根据它来判断是否显示消息通知
    private boolean isForeground = false;

    private String mFileSize;

    // 通知
    private Notification notification = null;
    private NotificationManager notificationManager = null;
    private NotificationCompat.Builder builder = null;

    //VersionEntity entity;
    private boolean isDownLoadStart = false;

    public static AppUpdateManager getInstance(Context context, Class mClass) {
        if (instance == null) {
            synchronized (AppUpdateManager.class) {
                instance = new AppUpdateManager(context, mClass);
            }
        }
        return instance;
    }

    /**
     * 对象置空
     */
    public void clear() {
        //unBindService();
        instance = null;
    }

    /**
     * 判断是否有下载任务
     *
     * @return
     */
    public static boolean isDownStart() {

        if (instance == null) {
            return false;
        }
        if (instance.isDownLoadStart) {
            ToastUtils.shortShow("正在下载...");
        }

        return instance.isDownLoadStart;
    }

    public AppUpdateManager(Context context, Class mClass) {
        this.mContext = context;
        this.mClass = mClass;
        builder = new NotificationCompat.Builder(mContext);
        builder.setSmallIcon(R.drawable.icon_notification);
        builder.setTicker(mContext.getString(R.string.app_update));
        builder.setWhen(System.currentTimeMillis());
        builder.setAutoCancel(true);
        notification = builder.build();

        // 获取通知服务
        notificationManager = (NotificationManager) mContext
                .getSystemService(mContext.NOTIFICATION_SERVICE);

        initServiceConnection();
        initProgressDialog();

    }

    private ProgressBar mProgressBar;
    private TextView mTvPercent;
    private TextView mTvSize;

    /**
     * 初始化ProgressDialog
     */
    private void initProgressDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        builder.setView(view);
        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setMax(100);
        mTvPercent = (TextView) view.findViewById(R.id.tvPercent);
        mTvSize = (TextView) view.findViewById(R.id.tvSize);
        builder.setTitle("正在更新");
        builder.setCancelable(true);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                ToastUtils.shortShow("后台更新");
//                unBindService();
            }
        });
        progressDialog = builder.create();
    }

    /**
     * 取消绑定服务
     */
    private void unBindService() {
        Intent intent = new Intent(mContext, AppUpdateService.class);
        if (isBinded) {
            Log.d("service", "unbind service");
            mContext.unbindService(updateServiceConnection);
            mContext.stopService(intent);
            updateService = null;
            isBinded = false;
        } else {
            Log.d("service", "not binded");
        }
    }

    /**
     * 初始化ServiceConnection
     */
    private void initServiceConnection() {
        updateServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                Log.d("ServiceConnection", "service connected");
                AppUpdateService.AppUpdateBinder aBinder = (AppUpdateService.AppUpdateBinder) service;
                updateService = aBinder.getService();
                isBinded = true;
                // 为service设置handler
                updateService.setHandler(handler);
                // TODO: 执行下载任务
                updateService.downloadStart(downloadUrl, versionName);
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                Log.d("ServiceConnection", "service disconnected");
                updateService = null;
                if (progressDialog != null && progressDialog.isShowing()) {
                    try {
                        progressDialog.dismiss();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isBinded = false;
            }
        };
    }

    // 定义检查更新的方法
    public void checkUpdate(UpdateModel model) {
        if (!isDownLoadStart) {//判断是否正在下载
            showUpdateDialog(model);
        } else {
            ToastUtils.shortShow("正在下载");
        }
    }

    /**
     * 混合调用
     * 先以startService方式启动服务
     * 再以bindService方式绑定服务
     */
    private void bindUpdateService() {
        Intent intent = new Intent(mContext, AppUpdateService.class);
        mContext.startService(intent);
        mContext.bindService(intent, updateServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void downLoadApkStart(String url) {
        downloadUrl=url;
        isDownLoadStart = true;
        progressDialog.show();
        bindUpdateService();
    }


    /**
     * 显示更新对话框
     */
    private void showUpdateDialog(final UpdateModel model) {

        String tip="发现新版本了"+model.getVersionName()+",请更新";
         UpdateDialog dialog=new UpdateDialog((Activity) mContext, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (v.getId()){
                    case R.id.btn_ok:
                        downLoadApkStart(model.getDownloadLink());
                        break;
                }

            }
        });
        dialog.setTitle(tip);
        dialog.setContent(model.getDes());
        dialog.show(model.isForce());
        }


    // 用来显示ui的Handler
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MSG_DOWNLOAD_START:
                    mFileSize = byteToMB(msg.getData().getInt(KEY_FILE_SIZE));
                    mTvSize.setText(mContext.getString(R.string.update_file_size, 0+"", mFileSize));
                    mTvPercent.setText("0%");
                    break;
                case MSG_DOWNLOAD_PROGRESS: // 更新进度
                    updateProgress(msg);
                    break;
                case MSG_DOWNLOAD_RESULT: // 下载结果
                    handleDownloadResult(msg);
                    break;
            }
        }
    };

    private String byteToMB(int size) {
        double fileSize = size / 1024.0 / 1024.0;
        java.text.DecimalFormat df = new java.text.DecimalFormat("#.00");
        String mbSize = df.format(fileSize);
        if (mbSize.indexOf(".") == 0) {
            mbSize = "0" + mbSize;
        }
        return mbSize;
    }

    /**
     * 更新下载进度
     *
     * @param msg
     */
    private void updateProgress(Message msg) {
        if (updateService != null) {
            Notification sNotification = updateService.getNotification();
            NotificationManager sNotificationManager = updateService.getNotificationManager();

            // 得到下载进度百分比
            int percentage = msg.getData().getInt(AppUpdateManager.KEY_PERCENT);
            mProgressBar.setProgress(percentage);
            mTvPercent.setText(percentage + "%");
            String downloadSize = byteToMB(msg.getData().getInt(KEY_DOWNLOAD_FILE_SIZE));
            mTvSize.setText(mContext.getString(R.string.update_file_size, downloadSize, mFileSize));
            //在后台时在消息通知栏显示下载进度
            if (!isForeground) {
                if (sNotification != null) {
                    RemoteViews remoteViews = sNotification.contentView;
                    if (remoteViews != null) {
                        remoteViews.setTextViewText(R.id.notification_update_progress_text, percentage + "%");
                        remoteViews.setProgressBar(R.id.notification_update_progress_bar, 100, percentage, false);
                        sNotificationManager.notify(AppUpdateService.NOTIFICATION_ID, sNotification);
                    } else {
                        Log.d("update", "remoteview null");
                    }
                }
            }
        }
    }


    public void handleDownloadResult(Message msg) {
        //拿到下载结果
        String fileName = msg.getData().getString(KEY_FILENAME);
        int downloadResult = msg.getData().getInt(KEY_DOWNLOAD_RESULT);

        switch (downloadResult) {
            case FLAG_CANCEL_UPDATE: //取消下载
                showNotification("取消下载", "取消下载", new Intent(mContext, mClass));
                break;
            case FLAG_DOWNLOAD_ERROR: //下载错误
                showNotification("下载错误", "重试", new Intent(mContext, mClass));
                break;
            case FLAG_NO_ENOUGH_SPACE: //空间不足
                showNotification("下载错误", "空间不足", new Intent(mContext, mClass));
                break;
            case FLAG_DOWNLOAD_SUCCESS: // 下载成功
                if (AppUtils.isAppOnForeground(mContext)) {
                    //如果在前台，则安装apk
                    ApkUtils.installApk(mContext, new File(FileUtils.getCacheApkPath(fileName, mContext)));
                }
                showInstallNotification("下载完成", "点击安装");
                break;
        }
        if (progressDialog != null && progressDialog.isShowing()) {
            try {
                progressDialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (updateService != null) {
            unBindService();
        }
        isDownLoadStart = false;
        instance = null;
    }

    /**
     * 在前台，消除notification
     */
    public void appForeground() {
        isForeground = true;
        if (updateService != null) {
            updateService.cancelNotification();
        }
    }

    /**
     * 在后台，显示notification
     */
    public void appBackground() {
        isForeground = false;
        if (updateService != null) {
            updateService.notifyMessage();
        }

    }

    /**
     * 显示通知
     *
     * @param nTitle
     * @param nMessage
     * @param intent
     */
    public void showNotification(String nTitle, String nMessage, Intent intent) {
        PendingIntent contentIntent = PendingIntent.getActivity(
                mContext, AppUpdateService.NOTIFICATION_ID, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        setNotification(nTitle, nTitle, nMessage, contentIntent);
        notificationManager.notify(AppUpdateService.NOTIFICATION_ID, notification);
    }

    /**
     * 设置消息通知内容
     *
     * @param ticker
     * @param title
     * @param text
     * @param intent
     */
    private void setNotification(String ticker, String title, String text, PendingIntent intent) {
        if (notification != null) {
            // change notification and repo
            if (builder != null) {
                builder.setTicker(ticker);
                builder.setContentTitle(title);
                builder.setContentText(text);
                builder.setAutoCancel(true);
                builder.setContent(null);
                builder.setContentIntent(intent);
                notification = builder.build();
            }
        }
    }

    /**
     * 显示安装的通知
     * @param nTitle
     * @param nMessage
     */
    public void showInstallNotification(String nTitle, String nMessage) {
        //判断是否是AndroidN以及更高的版本
        Intent intent = new Intent(Intent.ACTION_VIEW);
        File apkFile=new File(FileUtils.getCacheApkPath(FileUtils.getApkFileName(versionName), mContext));
        Log.d("wdy", "showInstallNotification: "+apkFile);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Log.d("wdy", "showInstallNotification: 1");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(mContext, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            Log.d("wdy", "showInstallNotification: 2");
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        showNotification(nTitle, nMessage, intent);
    }
}
