package net.vrgsoft.videcrop.cropview.window.handle;

import android.graphics.Rect;

import net.vrgsoft.videcrop.cropview.util.AspectRatioUtil1;
import net.vrgsoft.videcrop.cropview.window.edge.Edge1;

class VerticalHandleHelper extends HandleHelper {
    private Edge1 mEdge;

    VerticalHandleHelper(Edge1 edge) {
        super(null, edge);
        this.mEdge = edge;
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        this.mEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        float left = Edge1.LEFT.getCoordinate();
        float top = Edge1.TOP.getCoordinate();
        float right = Edge1.RIGHT.getCoordinate();
        float bottom = Edge1.BOTTOM.getCoordinate();
        float targetHeight = AspectRatioUtil1.calculateHeight(left, right, targetAspectRatio);
        float currentHeight = bottom - top;
        float difference = targetHeight - currentHeight;
        float halfDifference = difference / 2.0F;
        top -= halfDifference;
        bottom += halfDifference;
        Edge1.TOP.setCoordinate(top);
        Edge1.BOTTOM.setCoordinate(bottom);
        float offset;
        if (Edge1.TOP.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(Edge1.TOP, imageRect, targetAspectRatio)) {
            offset = Edge1.TOP.snapToRect(imageRect);
            Edge1.BOTTOM.offset(-offset);
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }

        if (Edge1.BOTTOM.isOutsideMargin(imageRect, snapRadius) && !this.mEdge.isNewRectangleOutOfBounds(Edge1.BOTTOM, imageRect, targetAspectRatio)) {
            offset = Edge1.BOTTOM.snapToRect(imageRect);
            Edge1.TOP.offset(-offset);
            this.mEdge.adjustCoordinate(targetAspectRatio);
        }

    }
}

