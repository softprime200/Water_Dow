package net.vrgsoft.videcrop.cropview.window.handle;

import android.graphics.Rect;

import net.vrgsoft.videcrop.cropview.window.edge.Edge1;

public enum Handle {
    TOP_LEFT(new CornerHandleHelper(Edge1.TOP, Edge1.LEFT)),
    TOP_RIGHT(new CornerHandleHelper(Edge1.TOP, Edge1.RIGHT)),
    BOTTOM_LEFT(new CornerHandleHelper(Edge1.BOTTOM, Edge1.LEFT)),
    BOTTOM_RIGHT(new CornerHandleHelper(Edge1.BOTTOM, Edge1.RIGHT)),
    LEFT(new VerticalHandleHelper(Edge1.LEFT)),
    TOP(new HorizontalHandleHelper(Edge1.TOP)),
    RIGHT(new VerticalHandleHelper(Edge1.RIGHT)),
    BOTTOM(new HorizontalHandleHelper(Edge1.BOTTOM)),
    CENTER(new CenterHandleHelper());

    private HandleHelper mHelper;

    private Handle(HandleHelper helper) {
        this.mHelper = helper;
    }

    public void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        this.mHelper.updateCropWindow(x, y, imageRect, snapRadius);
    }

    public void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mHelper.updateCropWindow(x, y, targetAspectRatio, imageRect, snapRadius);
    }
}
