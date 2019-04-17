package com.johnwebi.webiimageloaderexample.ImageView;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

public class WebiImageView extends android.support.v7.widget.AppCompatImageView
{
    public WebiImageView(Context context) {
        super(context);
    }

    public WebiImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WebiImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Drawable d = getDrawable();
        if (d != null) {
            int w = MeasureSpec.getSize(widthMeasureSpec);
            int h = w * d.getIntrinsicHeight() / d.getIntrinsicWidth();
            setMeasuredDimension(w, h);
        }
        else super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
