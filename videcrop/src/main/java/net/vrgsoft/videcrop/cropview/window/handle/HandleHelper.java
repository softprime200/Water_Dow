package net.vrgsoft.videcrop.cropview.window.handle;

import android.graphics.Rect;

import net.vrgsoft.videcrop.cropview.util.AspectRatioUtil1;
import net.vrgsoft.videcrop.cropview.window.edge.Edge1;
import net.vrgsoft.videcrop.cropview.window.edge.EdgePair1;

abstract class HandleHelper {
    private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1.0F;
    private Edge1 mHorizontalEdge;
    private Edge1 mVerticalEdge;
    private EdgePair1 mActiveEdges;

    HandleHelper(Edge1 horizontalEdge, Edge1 verticalEdge) {
        this.mHorizontalEdge = horizontalEdge;
        this.mVerticalEdge = verticalEdge;
        this.mActiveEdges = new EdgePair1(this.mHorizontalEdge, this.mVerticalEdge);
    }

    void updateCropWindow(float x, float y, Rect imageRect, float snapRadius) {
        EdgePair1 activeEdges = this.getActiveEdges();
        Edge1 primaryEdge = activeEdges.primary;
        Edge1 secondaryEdge = activeEdges.secondary;
        if (primaryEdge != null) {
            primaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, 1.0F);
        }

        if (secondaryEdge != null) {
            secondaryEdge.adjustCoordinate(x, y, imageRect, snapRadius, 1.0F);
        }

    }

    abstract void updateCropWindow(float var1, float var2, float var3, Rect var4, float var5);

    EdgePair1 getActiveEdges() {
        return this.mActiveEdges;
    }

    EdgePair1 getActiveEdges(float x, float y, float targetAspectRatio) {
        float potentialAspectRatio = this.getAspectRatio(x, y);
        if (potentialAspectRatio > targetAspectRatio) {
            this.mActiveEdges.primary = this.mVerticalEdge;
            this.mActiveEdges.secondary = this.mHorizontalEdge;
        } else {
            this.mActiveEdges.primary = this.mHorizontalEdge;
            this.mActiveEdges.secondary = this.mVerticalEdge;
        }

        return this.mActiveEdges;
    }

    private float getAspectRatio(float x, float y) {
        float left = this.mVerticalEdge == Edge1.LEFT ? x : Edge1.LEFT.getCoordinate();
        float top = this.mHorizontalEdge == Edge1.TOP ? y : Edge1.TOP.getCoordinate();
        float right = this.mVerticalEdge == Edge1.RIGHT ? x : Edge1.RIGHT.getCoordinate();
        float bottom = this.mHorizontalEdge == Edge1.BOTTOM ? y : Edge1.BOTTOM.getCoordinate();
        float aspectRatio = AspectRatioUtil1.calculateAspectRatio(left, top, right, bottom);
        return aspectRatio;
    }
}

