package com.ran.mall.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import com.ran.mall.BuildConfig;

import java.io.File;

public class ApkUtils {


    /**
     * 判断对象是否为空
     *
     * @return boolean
     */
    private static boolean isNull(Object object) {
        if (object == null || object.equals("")) {
            return true;
        }
        return false;
    }

    public static String getDeviceModel() {
        return Build.MODEL;
    }


    /**
     * 根据包名判断是否安装
     *
     * @param context
     *            {@link Context}
     * @param packageName
     *            包名
     * @return boolean
     */
    public static boolean isInstalledApk(Context context, String packageName) {
        if (context == null || isNull(packageName)) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                return true;
            }
        } catch (NameNotFoundException e) {
            return false;
        }
        return false;
    }

    /**
     * 根据包名判断是否安装
     *
     * @param context
     *            {@link Context}
     * @param packageName
     *            包名
     * @return int versioncode
     */
    public static int getInstalledApk(Context context, String packageName) {
        if (context == null || isNull(packageName)) {
            return -1;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                return packageInfo.versionCode;
            }
        } catch (NameNotFoundException e) {
            return -1;
        }
        return -1;
    }

    /**
     * 根据包名,版本号判断是否已安装
     *
     * @param context
     *            {@link Context}
     * @param packageName
     * @param versionCode
     * @return boolean
     */
    public static boolean isInstalledApk(Context context, String packageName,
                                         String versionCode) {
        if (context == null || isNull(packageName) || isNull(versionCode)) {
            return false;
        }
        try {
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(packageName, 0);
            if (packageInfo != null) {
                String v = String.valueOf(packageInfo.versionCode);
                if (versionCode.equals(v))
                    return true;
            }
        } catch (NameNotFoundException e) {
            return false;
        }
        return false;
    }

    /**
     * 根据包名获取版本名
     *
     * @param context
     *            {@link Context}
     * @param packageName
     * @return String
     */
    public static String getAppVersionName(Context context, String packageName) {
        String versionName = "0.0.0";
        if (context == null) {
            return versionName;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            if (packageName == null) {
                packageName = context.getPackageName();
            }
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            versionName = packageInfo.versionName;
        } catch (NameNotFoundException e) {

        }

        return versionName;
    }

    /**
     * 根据包名获取版本号
     *
     * @param context
     *            {@link Context}
     * @param packageName
     *            包名 传入null 则取当前包名\
     * @return int
     */
    public static int getAppVersionCode(Context context, String packageName) {
        int versionCode = 0;
        if (context == null) {
            return versionCode;
        }
        if (TextUtils.isEmpty(packageName)) {
            return versionCode;
        }
        PackageManager packageManager = context.getPackageManager();
        try {
            PackageInfo packageInfo = packageManager.getPackageInfo(
                    packageName, 0);
            versionCode = packageInfo.versionCode;
        } catch (NameNotFoundException e) {
            return 0;
        }

        return versionCode;
    }

//	/***
//	 * 安装apk
//	 * @param context
//	 * @param appDownloader
//	 */
//	public static void installApk(Context context, ApkDownloader appDownloader) {
//		if (appDownloader == null || Utils.isFastDoubleClick()) {
//			return;
//		}
//		if (appDownloader.appType == Constants.DPK_GAME) {
//			String fileName = appDownloader.packageName + "_" + appDownloader.versionCode + ".apk";
//			File apkFile = ApkUtils.getApkFile(context, fileName);
//			if (apkFile.exists()) {
//				MobclickAgent.onEvent(context, Constants.EVENT_ID_LARGE_GAME_BEGIN_INSTALL);
//				ApkUtils.installPackage(context, apkFile.getPath(), appDownloader);
//			}
//		} else {
//			ApkUtils.installPackage(context, appDownloader.filePath, appDownloader);
//		}
    /**
     * 安装应用
     * @param context 上下文对象
     * @param apkFile  apk文件
     */
    public static boolean installApk(Context context, File apkFile){
        if (apkFile == null || !apkFile.exists() || !apkFile.isFile() || apkFile.length() <= 0) {
            return false;
        }

        Intent intent = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, BuildConfig.APPLICATION_ID + ".fileProvider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        }
        context.startActivity(intent);
        return true;
    }

//	}

    /**
     * 打开已安装的包
     */
//	public static void openInstalledPackage(Context context, String packageName) {
//		if (context == null || isNull(packageName)) {
//			return;
//		}
//        MobclickAgent.onEvent(context, Constants.EVENT_ID_STATUS_OPEN_CLICK);
//		try {
//			final PackageManager packageManager = context.getPackageManager();
//			Intent queryIntent = new Intent(Intent.ACTION_MAIN);
//			queryIntent.addCategory(Intent.CATEGORY_LAUNCHER);
//			List<ResolveInfo> resolveInfos = packageManager
//					.queryIntentActivities(queryIntent, 0);
//			ActivityInfo activityInfo = null;
//			String mainActivityClass = "";
//			for (ResolveInfo resolveInfo : resolveInfos) {
//				activityInfo = resolveInfo.activityInfo;
//				if (activityInfo.packageName.equals(packageName)) {
//					mainActivityClass = activityInfo.name;
//					break;
//				}
//			}
//			if (!"".equals(mainActivityClass)) {
//				Intent opentIntent = context.getPackageManager()
//						.getLaunchIntentForPackage(packageName);
//				if (null == opentIntent) {
//					opentIntent = new Intent();
//					opentIntent.setComponent(new ComponentName(packageName,
//							mainActivityClass));
//					opentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//					context.startActivity(opentIntent);
//					return;
//				}
//				opentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(opentIntent);
//			} else {
//				Intent opentIntent = context.getPackageManager()
//						.getLaunchIntentForPackage(packageName);
//				opentIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//				context.startActivity(opentIntent);
//			}
//		} catch (Exception e) {
//			Toast.makeText(context, context.getResources().getString(R.string.not_keep_in_local), Toast.LENGTH_SHORT).show();
//		}
//	}

    /**
     * 卸载应用程序
     *
     * @param ctx
     * @param packageName
     */
    public static void unInstallPackage(Context ctx, String packageName) {
        Uri packageUri = Uri.fromParts("package", packageName, null);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
        // 注意:卸载这里不能使用FLAG_ACTIVITY_NEW_TASK 标签，不然广播不能被接收
        // uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(uninstallIntent);
    }

    /**
     * 卸载应用程序
     *
     * @param activity
     * @param packageName
     */
    public static void unInstallPackageForResult(Activity activity, String packageName,
                                                 int requestCode) {
        Uri packageUri = Uri.fromParts("package", packageName, null);
        Intent uninstallIntent = new Intent(Intent.ACTION_DELETE, packageUri);
        // 注意:卸载这里不能使用FLAG_ACTIVITY_NEW_TASK 标签，不然广播不能被接收
        // uninstallIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivityForResult(uninstallIntent, requestCode);
    }
}
