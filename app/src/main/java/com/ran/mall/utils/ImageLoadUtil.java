package com.ran.mall.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.ran.mall.R;

/**
 * Created by DELL on 2017/8/23.
 */

public class ImageLoadUtil {
    public static void loadImageUrL(Context context, int resid, ImageView view) {
        Glide.with(context).load(resid).placeholder(R.drawable.default_icon).error(R.drawable.default_icon).into(view);
    }

    public static void loadImageUrL(Context context, Uri url, ImageView view) {
        Glide.with(context).load(url).into(view);
    }

    public static void loadImageUrL(Context context, String url, ImageView view) {
        Log.d("wdy", "loadImageUrL: ");
        Glide.with(context).load(url).listener(new RequestListener<String, GlideDrawable>() {
            @Override
            public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                Log.d("wdy", "onException: " + e.toString() + "  model:" + model + " isFirstResource: " + isFirstResource);
                return false;
            }

            @Override
            public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                Log.e("wdy", "model:" + model + " isFirstResource: " + isFirstResource);
                return false;
            }
        }).into(view);
    }

    interface ImageUrlListener {
        void onException();

        void onResourceReady();

    }


    //加载圆形图片
    public static void loadCircleImageUrl(Context context, Uri uri, ImageView view) {
        Glide.with(context).load(uri).placeholder(R.drawable.default_icon).error(R.drawable.default_icon).transform(new CircleTransfrom(context)).into(view);
    }

    public static void loadCircleImageUrl(Context context, int resouid, ImageView view) {
        Glide.with(context).load(resouid).placeholder(R.drawable.default_icon).error(R.drawable.default_icon).transform(new CircleTransfrom(context)).into(view);
    }

    public static class CircleTransfrom extends BitmapTransformation {

        public CircleTransfrom(Context context) {
            super(context);
        }

        @Override
        protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
            return circleCrop(pool, toTransform);
        }

        private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
            if (source == null) return null;

            int size = Math.min(source.getWidth(), source.getHeight());
            int x = (source.getWidth() - size) / 2;
            int y = (source.getHeight() - size) / 2;

            // TODO this could be acquired from the pool too
            Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

            Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
            if (result == null) {
                result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
            }
            Canvas canvas = new Canvas(result);
            Paint paint = new Paint();
            paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
            paint.setAntiAlias(true);
            float r = size / 2f;
            canvas.drawCircle(r, r, r, paint);
            return result;
        }

        @Override
        public String getId() {
            return getClass().getName();
        }
    }


}
