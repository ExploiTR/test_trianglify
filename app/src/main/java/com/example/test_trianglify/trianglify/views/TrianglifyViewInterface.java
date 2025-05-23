package com.example.test_trianglify.trianglify.views;

/*
  Created by suyash on 18/3/17.
 */

import com.example.test_trianglify.trianglify.models.Palette;
import com.example.test_trianglify.trianglify.models.Triangulation;
import com.example.test_trianglify.trianglify.presenters.Presenter;

public interface TrianglifyViewInterface {
    int GRID_RECTANGLE = 0;
    int GRID_CIRCLE = 1;

    //honestly idk what shape it is creating but ok
    int GRID_TRIANGLE = 2;

    int getBleedX();

    int getBleedY();

    int getTypeGrid();

    int getGridWidth();

    int getGridHeight();

    int getVariance();

    int getCellSize();

    int getBitmapQuality();

    Presenter.ViewState getViewState();

    boolean isFillViewCompletely();

    boolean isFillTriangle();

    boolean isDrawStrokeEnabled();

    boolean isRandomColoringEnabled();

    Palette getPalette();

    void invalidateView(Triangulation triangulation);
}
