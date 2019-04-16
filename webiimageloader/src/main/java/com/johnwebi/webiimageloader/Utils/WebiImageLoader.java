package com.johnwebi.webiimageloader.Utils;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.support.annotation.DrawableRes;
import android.widget.ImageView;


import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class WebiImageLoader extends NetworkLoader {
    private ImageView imageView;
    private @DrawableRes
    int placeHolderDrawable;

    public static class Builder extends NetworkLoader.Builder<Builder> {
        private @DrawableRes
        int placeHolderDrawable;
        private ImageView imageView;

        public Builder() {
        }

        public Builder placeHolder(@DrawableRes int drawable) {
            placeHolderDrawable = drawable;
            return this;
        }

        public Builder into(final ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public WebiImageLoader build() {
            if (context == null) {
                throw new RuntimeException("Context is null");
            }
            final Bitmap bitmap = MemoryCache.getInstance().getItem(request.url().toString());
            if (bitmap != null) {
                if (context instanceof Activity) {
                    final Activity activity = ((Activity) context);
                    activity.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imageView.setImageBitmap(bitmap);
                        }
                    });
                } else {
                    throw new RuntimeException("Invalid context passed.");
                }
            } else {
                if (progressListener != null)
                    progressListener.onRequestStarted();
                okHttpClient.newCall(request)
                        .enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, final IOException e) {
                                e.printStackTrace();
                                if (context instanceof Activity) {
                                    final Activity activity = ((Activity) context);
                                    activity.runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            if (progressListener != null)
                                                progressListener.onError(e);
                                            imageView.setImageDrawable(activity.getResources().getDrawable(placeHolderDrawable));
                                        }
                                    });
                                } else {
                                    throw new RuntimeException("Invalid context passed.");
                                }
                            }

                            @Override
                            public void onResponse(Call call, Response response) {
                                InputStream inputStream;
                                if (response.body() != null) {
                                    inputStream = response.body().byteStream();
                                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                                    MemoryCache.getInstance().addItem(request.url().toString(), bitmap);
                                    if (context instanceof Activity) {
                                        final Activity activity = ((Activity) context);
                                        activity.runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                if (progressListener != null)
                                                    progressListener.onRequestComplete();
                                                final TransitionDrawable td =
                                                        new TransitionDrawable(new Drawable[]{
                                                                new ColorDrawable(Color.TRANSPARENT),
                                                                new BitmapDrawable(activity.getResources(), bitmap)
                                                        });
                                                imageView.setImageBitmap(bitmap);
                                                imageView.setImageDrawable(td);
                                                td.startTransition(500);
                                            }
                                        });
                                    } else {
                                        throw new RuntimeException("Invalid context passed.");
                                    }
                                }
                            }
                        });
            }
            return new WebiImageLoader(this);
        }
    }

    protected WebiImageLoader(Builder builder) {
        super(builder);
        placeHolderDrawable = builder.placeHolderDrawable;
        imageView = builder.imageView;
    }
}
