package com.johnwebi.webiimageloader.Utils;

public interface ProgressListener {
    void onRequestStarted();

    void onRequestComplete();

    void onError(Throwable t);
}
