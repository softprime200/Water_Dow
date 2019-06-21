package net.vrgsoft.videcrop.cropview.window.handle;

import android.graphics.Rect;

import net.vrgsoft.videcrop.cropview.util.AspectRatioUtil1;
import net.vrgsoft.videcrop.cropview.window.edge.Edge1;

class HorizontalHandleHelper extends HandleHelper {
    private Edge1 mEdge;

    HorizontalHandleHelper(Edge1 edge) {
        super(edge, null);
        this.mEdge = edge;
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = Edge1.LEFT.getCoordinate();
        float top = Edge1.TOP.getCoordinate();
        float right = Edge1.RIGHT.getCoordinate();
        float bottom = Edge1.BOTTOM.getCoordinate();
        float targetWidth = AspectRatioUtil1.calculateWidth(top, bottom, targetAspectRatio);
        float currentWidth = right - left;
        float difference = targetWidth - currentWidth;
        float halfDifference = difference / 2.0F;
        left -= halfDifference;
        right += halfDifference;
        Edge1.LEFT.setCoordinate(left);
        Edge1.RIGHT.setCoordinate(right);
        float offset;
        if (Edge1.LEFT.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(Edge1.LEFT, imageRect, targetAspectRatio)) {
            offset = Edge1.LEFT.snapToRect(imageRect);
            Edge1.RIGHT.offset(-offset);
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }

        if (Edge1.RIGHT.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(Edge1.RIGHT, imageRect, targetAspectRatio)) {
            offset = Edge1.RIGHT.snapToRect(imageRect);
            Edge1.LEFT.offset(-offset);
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }

    }
}

