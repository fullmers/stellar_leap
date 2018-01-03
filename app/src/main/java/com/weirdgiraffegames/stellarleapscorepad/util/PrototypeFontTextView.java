package com.weirdgiraffegames.stellarleapscorepad.util;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;

/**
 * Created by sarah on 03/01/2018.
 */

public class PrototypeFontTextView extends android.support.v7.widget.AppCompatTextView {

    public PrototypeFontTextView(Context context) {
        super(context);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Prototype.ttf");
        this.setTypeface(face);
    }

    public PrototypeFontTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Prototype.ttf");
        this.setTypeface(face);
    }

    public PrototypeFontTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "Prototype.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}
