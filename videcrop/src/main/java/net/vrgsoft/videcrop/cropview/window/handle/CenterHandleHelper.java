package net.vrgsoft.videcrop.cropview.window.handle;

import android.graphics.Rect;

import net.vrgsoft.videcrop.cropview.window.edge.Edge1;

class CenterHandleHelper extends HandleHelper {
    CenterHandleHelper() {
        super(null, null);
    }

    void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        float left = Edge1.LEFT.getCoordinate();
        float top = Edge1.TOP.getCoordinate();
        float right = Edge1.RIGHT.getCoordinate();
        float bottom = Edge1.BOTTOM.getCoordinate();
        float currentCenterX = (left + right) / 2.0F;
        float currentCenterY = (top + bottom) / 2.0F;
        float offsetX = x - currentCenterX;
        float offsetY = y - currentCenterY;
        Edge1.LEFT.offset(offsetX);
        Edge1.TOP.offset(offsetY);
        Edge1.RIGHT.offset(offsetX);
        Edge1.BOTTOM.offset(offsetY);
        float offset;
        if (Edge1.LEFT.isOutsideMargin(imageRect, snapRadius)) {
            offset = Edge1.LEFT.snapToRect(imageRect);
            Edge1.RIGHT.offset(offset);
        } else if (Edge1.RIGHT.isOutsideMargin(imageRect, snapRadius)) {
            offset = Edge1.RIGHT.snapToRect(imageRect);
            Edge1.LEFT.offset(offset);
        }

        if (Edge1.TOP.isOutsideMargin(imageRect, snapRadius)) {
            offset = Edge1.TOP.snapToRect(imageRect);
            Edge1.BOTTOM.offset(offset);
        } else if (Edge1.BOTTOM.isOutsideMargin(imageRect, snapRadius)) {
            offset = Edge1.BOTTOM.snapToRect(imageRect);
            Edge1.TOP.offset(offset);
        }

    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.updateCropWindow(x, y, imageRect, snapRadius);
    }
}

