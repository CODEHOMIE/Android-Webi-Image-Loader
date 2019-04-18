package com.johnwebi.webiimageloader.Utils.lrucache;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import java.io.Serializable;

public final class LruMemCache implements Serializable {
    private static volatile LruMemCache INSTANCE;

    private LruCache<String, Bitmap> memCache;

    private LruMemCache() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static LruMemCache getInstance() {
        if (INSTANCE == null) {
            synchronized (LruMemCache.class) {
                INSTANCE = new LruMemCache();
            }
        }
        return INSTANCE;
    }

    //Make singleton from serialize and deserialize operation.
    protected LruMemCache readResolve() {
        return getInstance();
    }

    public void initCache(int size) {
        memCache = new LruCache<String, Bitmap>(size) {
            @Override
            protected int sizeOf(@NonNull String key, @NonNull Bitmap value) {
                return value.getByteCount() / 1024;
            }
        };
    }

    public void addItem(String key, Bitmap value) {
        if (memCache == null) {
            initCache(4 * 1024 * 1024);
        }

        if (key == null || value == null) return;

        if (getItem(key) == null) {
            memCache.put(key, value);
        }
    }

    public Bitmap getItem(String key) {
        if (memCache == null)
            initCache(4 * 1024 * 1024);
        if (key == null )
            return null;

        return memCache.get(key);
    }
}
