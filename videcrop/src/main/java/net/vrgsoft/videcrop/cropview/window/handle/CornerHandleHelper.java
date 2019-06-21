package net.vrgsoft.videcrop.cropview.window.handle;

import android.graphics.Rect;

import net.vrgsoft.videcrop.cropview.window.edge.Edge1;
import net.vrgsoft.videcrop.cropview.window.edge.EdgePair1;

class CornerHandleHelper extends HandleHelper {
    CornerHandleHelper(Edge1 horizontalEdge, Edge1 verticalEdge) {
        super(horizontalEdge, verticalEdge);
    }

    void updateCropWindow(float x, float y, float targetAspectRatio, Rect imageRect, float snapRadius) {
        EdgePair1 activeEdges = this.getActiveEdges(x, y, targetAspectRatio);
        Edge1 primaryEdge = activeEdges.primary;
        Edge1 secondaryEdge = activeEdges.secondary;
        primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, targetAspectRatio);
        secondaryEdge.adjustCoordinate(targetAspectRatio);
        if (secondaryEdge.isOutsideMargin(imageRect, snapRadius)) {
            secondaryEdge.snapToRect(imageRect);
            primaryEdge.adjustCoordinate(targetAspectRatio);
        }

    }
}