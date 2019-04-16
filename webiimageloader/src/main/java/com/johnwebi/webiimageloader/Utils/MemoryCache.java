package com.johnwebi.webiimageloader.Utils;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.support.v4.util.LruCache;

import java.io.Serializable;

public final class MemoryCache implements Serializable {
    private static volatile MemoryCache INSTANCE;

    private LruCache<String, Bitmap> memCache;

    private MemoryCache() {
        if (INSTANCE != null) {
            throw new RuntimeException("Use getInstance() method to get the single instance of this class.");
        }
    }

    public static MemoryCache getInstance() {
        if (INSTANCE == null) {
            synchronized (MemoryCache.class) {
                INSTANCE = new MemoryCache();
            }
        }
        return INSTANCE;
    }

    //Make singleton from serialize and deserialize operation.
    protected MemoryCache readResolve() {
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
        return memCache.get(key);
    }
}
