package com.otaliastudios.cameraview;

import android.content.Context;
import android.hardware.SensorManager;
import android.support.annotation.NonNull;
import android.util.SparseIntArray;
import android.view.Display;
import android.view.OrientationEventListener;
import android.view.Surface;
import android.view.WindowManager;

class OrientationHelper {

    final OrientationEventListener mListener;

    private final Callback mCallback;
    private int mLastKnownDisplayOrientation = 0;
    private int mLastKnownDeviceOrientation = 0;
    private Display mDisplay = null;

    static final SparseIntArray DISPLAY_ORIENTATIONS = new SparseIntArray();

    static {
        DISPLAY_ORIENTATIONS.put(Surface.ROTATION_0, 0);
        DISPLAY_ORIENTATIONS.put(Surface.ROTATION_90, 90);
        DISPLAY_ORIENTATIONS.put(Surface.ROTATION_180, 180);
        DISPLAY_ORIENTATIONS.put(Surface.ROTATION_270, 270);
    }

    interface Callback {
        void onDeviceOrientationChanged(int deviceOrientation);
    }

    OrientationHelper(Context context, @NonNull Callback callback) {
        mCallback = callback;
        mListener = new OrientationEventListener(context, SensorManager.SENSOR_DELAY_NORMAL) {

            private int mLastKnownDisplayRotation = -1;

            @Override
            public void onOrientationChanged(int orientation) {

                if (orientation == OrientationEventListener.ORIENTATION_UNKNOWN || mDisplay == null) {
                    return;
                }

                boolean displayOrDeviceOrientationChanged = false;

                final int displayRotation = mDisplay.getRotation();
                if (mLastKnownDisplayRotation != displayRotation) {
                    mLastKnownDisplayRotation = displayRotation;
                    displayOrDeviceOrientationChanged = true;
                }

                int deviceOrientation;
                if (orientation >= 60 && orientation <= 140) {
                    // the mDisplay.getRotation stuff is flipped for 90 & 270 vs. deviceOrientation here. This keeps it consistent.
                    deviceOrientation = 90;
                } else if (orientation >= 140 && orientation <= 220) {
                    deviceOrientation = 180;
                } else if (orientation >= 220 && orientation <= 300) {
                    // the mDisplay.getRotation stuff is flipped for 90 & 270 vs. deviceOrientation here. This keeps it consistent.
                    deviceOrientation = 270;
                } else {
                    deviceOrientation = 0;
                }

                if (deviceOrientation != mLastKnownDeviceOrientation) {
                    mLastKnownDeviceOrientation = deviceOrientation;
                    displayOrDeviceOrientationChanged = true;
                }

                if (displayOrDeviceOrientationChanged)
                    mCallback.onDeviceOrientationChanged(mLastKnownDeviceOrientation);

            }
        };
    }

    void enable(Context context) {
        if (mDisplay == null) {
            Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
            mDisplay = display;
            mLastKnownDisplayOrientation = DISPLAY_ORIENTATIONS.get(display.getRotation());
        }
        mListener.enable();
    }

    void disable() {
        mListener.disable();
        mLastKnownDisplayOrientation = -1;
        mLastKnownDeviceOrientation = -1;
        mDisplay = null;
    }

    int getDeviceOrientation() {
        return mLastKnownDeviceOrientation;
    }

    int getDisplayOffset() {
        return mLastKnownDisplayOrientation;
    }
}