package com.ran.mall.widget;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Configuration;
import android.os.Handler;
import android.text.InputType;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.ran.mall.R;


/**
 * 就是自定义的Dialog
 */
public class CustomDialog {
    private final static String TAG = CustomDialog.class.getSimpleName();
    public final static int SELECT_DIALOG = 1;
    public final static int RADIO_DIALOG = 2;
    public final static int UPDATE_DIALOG = 3;

    private TextView mConfirButton;

    private TextView mCancleButton;

    private android.app.Dialog mDialog;
    private static CustomDialog mInstance;

    public static CustomDialog bulider() {
        if (mInstance == null) {
            mInstance = new CustomDialog();
        }
        return mInstance;
    }


    /**
     * 创建�?个单选对话框
     *
     * @param context
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public android.app.Dialog showRadioDialog(Context context,
                                              String toast, final DialogClickListener dialogClickListener) {
        return ShowDialog(context,
                toast, "",
                dialogClickListener, RADIO_DIALOG);
    }


    public android.app.Dialog showRadioDialog1(Context context,
                                               String title, String toast, final DialogClickListener dialogClickListener) {
        return ShowDialog1(context,
                title, toast,
                dialogClickListener, RADIO_DIALOG);
    }

    /**
     * 创建一个选择对话框
     *
     * @param context
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public android.app.Dialog showSelectDialog(Context context,
                                               String toast, final DialogClickListener dialogClickListener) {

        return ShowDialog(context,
                toast, "",
                dialogClickListener, SELECT_DIALOG);
    }

    /**
     * 创建�?个�?�择对话�?
     *
     * @param context
     * @param title               提示标题
     * @param toast               提示消息
     * @param dialogClickListener 点击监听
     * @return
     */
    public android.app.Dialog showSelectDialog(Context context,
                                               String title, String toast,
                                               final DialogClickListener dialogClickListener) {
        return ShowDialog(context, title, toast, dialogClickListener,
                SELECT_DIALOG);
    }


    private android.app.Dialog ShowDialog(Context context, String title,
                                          String toast, final DialogClickListener dialogClickListener,

                                          int DialogType) {
        dissMissDilog();
        mDialog = new android.app.Dialog(context,
                R.style.DialogStyle);
        mDialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        mDialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        ((TextView) view.findViewById(R.id.toast)).setText(toast);
        if (DialogType == RADIO_DIALOG) {
        } else {
            view.findViewById(R.id.leftContent).setVisibility(View.GONE);
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.confirm).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mDialog.dismiss();
                        mDialog = null;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.confirm();
                            }
                        }, 200);
                    }

                });
        view.findViewById(R.id.rightContent).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mDialog.dismiss();
                        mDialog = null;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.cancel();
                            }
                        }, 200);
                    }
                });
        view.findViewById(R.id.leftContent).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDialog.dismiss();
                mDialog = null;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        Window mWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        mDialog.show();
        return mDialog;
    }

    private android.app.Dialog ShowDialog1(Context context, String title,
                                           String toast, final DialogClickListener dialogClickListener,

                                           int DialogType) {
        dissMissDilog();
        mDialog = new android.app.Dialog(context,
                R.style.DialogStyle);
        mDialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog, null);
        mDialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        ((TextView) view.findViewById(R.id.toast)).setText(toast);
        if (DialogType == RADIO_DIALOG) {
        } else {
            view.findViewById(R.id.leftContent).setVisibility(View.GONE);
            view.findViewById(R.id.divider).setVisibility(View.VISIBLE);
        }
        view.findViewById(R.id.confirm).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mDialog.dismiss();
                        mDialog = null;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.confirm();
                            }
                        }, 200);
                    }

                });
        view.findViewById(R.id.rightContent).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        mDialog.dismiss();
                        mDialog = null;
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.cancel();
                            }
                        }, 200);
                    }
                });
        view.findViewById(R.id.leftContent).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                mDialog.dismiss();
                mDialog = null;
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        dialogClickListener.confirm();
                    }
                }, 200);
            }
        });
        Window mWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        mDialog.show();
        return mDialog;
    }


    public static android.app.Dialog showSelctDialog(Context context, String title, String leftTxt, String rightTxt, int leftColor, int rightcolor, final DialogClickListener dialogClickListener
    ) {
        final Dialog dialog = new android.app.Dialog(context,
                R.style.DialogStyle);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_select, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        TextView confim = (TextView) view.findViewById(R.id.confirm);
        if (!TextUtils.isEmpty(rightTxt)) {
            confim.setText(rightTxt);
        }
        if (rightcolor != -1) {
            confim.setTextColor(rightcolor);
        }
        confim.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.confirm();
                            }
                        }, 200);
                    }

                });
        TextView cancle = (TextView) view.findViewById(R.id.rightContent);
        if (!TextUtils.isEmpty(leftTxt)) {
            cancle.setText(leftTxt);
        }
        if (leftColor != -1) {
            cancle.setTextColor(leftColor);
        }
        cancle.findViewById(R.id.rightContent).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.cancel();
                            }
                        }, 200);
                    }
                });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public static android.app.Dialog showPassWordDialog(Context context, String title, String toast, String leftTxt, String rightTxt, int leftColor, int rightcolor, final DialogPasswordClickListener dialogClickListener
    ) {
        final Dialog dialog = new android.app.Dialog(context,
                R.style.DialogStyle);
        dialog.setCancelable(false);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_set_password, null);
        dialog.setContentView(view);
        ((TextView) view.findViewById(R.id.point)).setText(title);
        ((TextView) view.findViewById(R.id.toast)).setText(toast);
        TextView confim = (TextView) view.findViewById(R.id.confirm);
        EditText et_text = (EditText) view.findViewById(R.id.password_et);
        et_text.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        if (!TextUtils.isEmpty(rightTxt)) {
            confim.setText(rightTxt);
        }
        if (rightcolor != -1) {
            confim.setTextColor(rightcolor);
        }
        confim.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.confirm(et_text.getText().toString().trim());
                            }
                        }, 200);
                    }

                });
        TextView cancle = (TextView) view.findViewById(R.id.leftContent);
        if (!TextUtils.isEmpty(leftTxt)) {
            cancle.setText(leftTxt);
        }
        if (leftColor != -1) {
            cancle.setTextColor(leftColor);
        }
        cancle.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                dialogClickListener.cancel();
                            }
                        }, 200);
                    }
                });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();
        return dialog;
    }

    public Dialog showRadiosDialog(Context context, String title, String content, String leftContent, String rightContent, int leftContentcolorId,int rightContentcolorId, final RadioDialogClickListener listener) {
        final Dialog dialog = new android.app.Dialog(context,
                R.style.DialogStyle);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_radio, null);
        dialog.setContentView(view);
        TextView mTitle = (TextView) view.findViewById(R.id.title);
        TextView mpoint = (TextView) view.findViewById(R.id.point);
        TextView mLeftContent = (TextView) view.findViewById(R.id.leftContent);
        TextView mRightContent = (TextView) view.findViewById(R.id.rightContent);
        dialog.setCancelable(false);

        if (!TextUtils.isEmpty(title)) {
            mTitle.setText(title);
        } else {
            mTitle.setVisibility(View.GONE);
            mpoint.setTextSize(19f);
        }

        if (!TextUtils.isEmpty(content)) {
            mpoint.setText(content);
        } else {
            mpoint.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(leftContent)) {
            mLeftContent.setText(leftContent);
        }
        if (leftContentcolorId != -1) {
            mLeftContent.setTextColor(leftContentcolorId);
        }
        if (!TextUtils.isEmpty(rightContent)) {
            mRightContent.setText(rightContent);
        }
        if (leftContentcolorId != -1) {
            mRightContent.setTextColor(rightContentcolorId);
        }


        view.findViewById(R.id.leftContent).setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                listener.confir();
                            }
                        }, 200);
                    }

                });
        view.findViewById(R.id.rightContent).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        listener.cancel();
                    }
                }, 200);
            }
        });
        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();
        return mDialog;
    }

    public android.app.Dialog showTextDialog(Context context,final DialogItemClickListener dialogClickListener) {

        final Dialog dialog = new android.app.Dialog(context,
                R.style.DialogStyle);
        dialog.setCancelable(true);
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_delete, null);
        dialog.setContentView(view);
        final TextView numberEdit = (TextView) view.findViewById(R.id.content);

        numberEdit.setOnClickListener(
                new OnClickListener() {
                    @Override
                    public void onClick(View arg0) {
                        dialog.dismiss();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                dialogClickListener.confirm();



                            }
                        }, 200);
                    }

                });

        Window mWindow = dialog.getWindow();
        WindowManager.LayoutParams lp = mWindow.getAttributes();
        if (context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {// 横屏
            lp.width = getScreenHeight(context) / 10 * 8;
        } else {
            lp.width = getScreenWidth(context) / 10 * 8;
        }
        mWindow.setAttributes(lp);
        dialog.show();
        return mDialog;

    }



    //隐藏显示Dialog
    public void dissMissDilog() {

        try {
            if (mDialog != null) {
                if (mDialog.isShowing()) {
                    Context context = ((ContextWrapper) mDialog.getContext()).getBaseContext();
                    if (context instanceof Activity) {
                        if (!((Activity) context).isFinishing() && !((Activity) context).isDestroyed()) {
                            mDialog.dismiss();
                        }

                    } else {
                        mDialog.dismiss();
                    }


                }


            }
        } catch (Exception e) {
            e.printStackTrace();
            mDialog = null;
        } finally {
            mDialog = null;
        }

    }

    /**
     * 获取屏幕分辨率宽
     */
    public static int getScreenWidth(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);
        return dm.widthPixels;
    }


    /**
     * 获取屏幕分辨率高
     */
    public static int getScreenHeight(Context context) {
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay()
                .getMetrics(dm);


        return dm.heightPixels;
    }

    public interface DialogClickListener {
        void confirm();

        void cancel();
    }

    public interface DialogPasswordClickListener {
        void confirm(String strText);

        void cancel();
    }

    public interface RadioDialogClickListener {
        void confir();
        void cancel();
    }

    public interface AddDialogClickListener {
        void cancel();

        void onErr(String msg);

        void confirm(String carNumber);
    }

    public interface DialogItemClickListener {
        void confirm();
    }
}