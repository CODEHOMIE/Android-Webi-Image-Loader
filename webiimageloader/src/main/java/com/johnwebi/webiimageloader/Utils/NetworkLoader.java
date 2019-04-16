package com.johnwebi.webiimageloader.Utils;

import android.content.Context;
import android.util.Log;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;

import java.util.Objects;

public abstract class NetworkLoader {

    private OkHttpClient okHttpClient;
    private Request request;
    private Context context;
    private ProgressListener progressListener;

    public static class Builder<T extends Builder<T>> {
        Request request;
        Context context;
        OkHttpClient okHttpClient;
        ProgressListener progressListener;

        public Builder() {
            okHttpClient = new OkHttpClient();
        }

        public T from(Context context) {
            this.context = context;
            return (T) this;
        }

        public T load(String url) {
            if (url == null || url.equals("") || !url.startsWith("http") || !url.startsWith("https")) {
                throw new IllegalArgumentException("Invalid image url");
            }
            request = new Request.Builder().tag(url).url(url).build();
            return (T) this;
        }

        public T setProgressListener(ProgressListener progressListener) {
            this.progressListener = progressListener;
            return (T) this;
        }
    }

    public NetworkLoader(Builder<?> builder) {
        okHttpClient = builder.okHttpClient;
        context = builder.context;
        request = builder.request;
        progressListener = builder.progressListener;
    }

    public void cancelRequest(Request request) {
        /*** Remove Queued calls ***/
        for (Call call : okHttpClient.dispatcher().queuedCalls()) {
            if (Objects.equals(call.request().tag(), request.tag()))
                call.cancel();
        }
        for (Call call : okHttpClient.dispatcher().runningCalls()) {
            if (Objects.equals(call.request().tag(), request.tag()))
                call.cancel();
        }
    }

    public Request getRequest() {
        return request;
    }

}
