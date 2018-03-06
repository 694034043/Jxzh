package com.bocop.zyt.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Gallery;

/**
 * Created by Administrator on 2017/9/18 0018.
 */

public class GalleryView extends Gallery {

    public GalleryView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GalleryView(Context context) {
        this(context, null);
    }

    public GalleryView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

}
