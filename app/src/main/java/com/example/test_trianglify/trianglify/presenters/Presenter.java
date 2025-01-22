package com.example.test_trianglify.trianglify.presenters;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.example.test_trianglify.trianglify.models.Triangulation;
import com.example.test_trianglify.trianglify.utilities.colorizers.ColorInterface;
import com.example.test_trianglify.trianglify.utilities.colorizers.FixedPointsColorInterface;
import com.example.test_trianglify.trianglify.utilities.patterns.Circle;
import com.example.test_trianglify.trianglify.utilities.patterns.Patterns;
import com.example.test_trianglify.trianglify.utilities.patterns.Rectangle;
import com.example.test_trianglify.trianglify.views.TrianglifyViewInterface;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;


/**
 * <h1>Presenter.java</h1>
 * <b>Description :</b>
 * P of MVP implemented to present data generated using models
 * to a view.
 * <p>
 * ...
 */

public class Presenter {
    public static boolean ASKED_TO_ELEM = false;

    private final TrianglifyViewInterface view;
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();
    public ViewState viewState = ViewState.NULL_TRIANGULATION;
    private Triangulation triangulation;
    private Future<?> runningTask = null;
    private boolean generateOnlyColor;

    public Presenter(TrianglifyViewInterface view) {
        this.view = view;
    }

    public void setGenerateOnlyColor(boolean generateOnlyColor) {
        this.generateOnlyColor = generateOnlyColor;
    }

    public void updateView(TrianglifyGenerateListener listener) {
        if (listener == null) return;
        listener.onTriangulationGenerationStarted();
        viewState = view.getViewState();
        if (viewState == ViewState.PAINT_STYLE_CHANGED || viewState == ViewState.UNCHANGED_TRIANGULATION) {
            view.invalidateView(triangulation);
            listener.onTriangulationGenerated();
        } else if (viewState == ViewState.COLOR_SCHEME_CHANGED) {
            generateNewColoredSoupAndInvalidate(listener);
        } else if (viewState == ViewState.GRID_PARAMETERS_CHANGED || viewState == ViewState.NULL_TRIANGULATION) {
            generateOnlyColor = false;
            generateSoupAndInvalidateView(listener, "P63");
        }
        Log.v("Presenter", "updateView");
    }

    private void generateNewColoredSoupAndInvalidate(TrianglifyGenerateListener listener) {
        if (listener == null) return;
        setGenerateOnlyColor(true);
        generateSoupAndInvalidateView(listener, "P71");
        Log.v("Presenter", "generateNewColoredSoupAndInvalidate");
    }

    private List<com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D> generateGrid() {
        int gridType = view.getTypeGrid();
        Patterns patterns;

        float possiblePPC = view.getCellSize() * 0.25F;
        if (possiblePPC < 16) possiblePPC = 16;
        else if (possiblePPC > 96) possiblePPC = 96;

        Log.v("Presenter", "possiblePPC: " + possiblePPC);

        if (gridType == TrianglifyViewInterface.GRID_CIRCLE)
            patterns = new Circle(view.getBleedX(), view.getBleedY(), Math.round(possiblePPC),
                    view.getGridHeight(), view.getGridWidth(), view.getCellSize(), view.getVariance());
        else if (gridType == TrianglifyViewInterface.GRID_TRIANGLE)
            patterns = new com.example.test_trianglify.trianglify.utilities.patterns.Triangle(2 * view.getBleedX(), 2 * view.getBleedY(), view.getGridHeight(),
                    view.getGridWidth(), view.getCellSize(), view.getVariance());
        else
            patterns = new Rectangle(view.getBleedX(), view.getBleedY(), view.getGridHeight(),
                    view.getGridWidth(), view.getCellSize(), view.getVariance());

        Log.v("Presenter", "generateGrid");
        try {
            return patterns.generate();
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Triangulation getSoup(Presenter.TrianglifyGenerateListener listener) throws com.example.test_trianglify.trianglify.utilities.triangulator.NotEnoughPointsException {
        if (!generateOnlyColor) triangulation = generateTriangulation(generateGrid(), listener);
        if (triangulation == null) triangulation = generateTriangulation(generateGrid(), listener);
        triangulation = generateColoredSoup(triangulation, listener);
        if (ASKED_TO_ELEM) return null;
        return triangulation;
    }

    private Triangulation generateTriangulation(List<com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D> inputGrid, Presenter.TrianglifyGenerateListener listener) throws com.example.test_trianglify.trianglify.utilities.triangulator.NotEnoughPointsException {
        com.example.test_trianglify.trianglify.utilities.triangulator.DTIterativeLegalize triangulation = new com.example.test_trianglify.trianglify.utilities.triangulator.DTIterativeLegalize(inputGrid, listener);
        triangulation.triangulate();
        return new Triangulation(triangulation.getTriangles());
    }

    private Triangulation generateColoredSoup(Triangulation inputTriangulation, Presenter.TrianglifyGenerateListener listener) {
        ColorInterface colorInterface = new FixedPointsColorInterface(inputTriangulation, view.getPalette(), view.getGridHeight() + 2 * view.getBleedY(), view.getGridWidth() + 2 * view.getBleedX(), view.isRandomColoringEnabled());
        if (ASKED_TO_ELEM) return null;
        return colorInterface.getColororedTriangulation(listener);
    }

    public void clearSoup() {
        triangulation = null;
        viewState = ViewState.NULL_TRIANGULATION;
    }

    public void generateSoupAndInvalidateView(TrianglifyGenerateListener listener, String fromWhere) {
        Log.v("Presenter", "generateSoupAndInvalidateView");
        Log.v("Check Loc", fromWhere);
        if (runningTask != null && !runningTask.isDone()) {
            ASKED_TO_ELEM = true;
            runningTask.cancel(true); //not working that's why ASKED_TO_ELEM is used
        }

        new Handler().postDelayed(() -> runningTask = executorService.submit(() -> {
            try {
                Triangulation triangulation = getSoup(listener);
                if (ASKED_TO_ELEM) {
                    ASKED_TO_ELEM = false;
                    return;
                }
                new Handler(Looper.getMainLooper()).post(() -> {
                    try {
                        listener.onTriangulationGenerated();
                        if (triangulation != null) view.invalidateView(triangulation);

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }), ASKED_TO_ELEM ? 1000 : 0); //delay to let it safely eliminate..
    }

    public enum ViewState {
        NULL_TRIANGULATION, UNCHANGED_TRIANGULATION, PAINT_STYLE_CHANGED, COLOR_SCHEME_CHANGED, GRID_PARAMETERS_CHANGED
    }

    public interface TrianglifyGenerateListener {
        void onTriangulationGenerationStarted();

        void onTriangulationGenerationInProgress(int total, int current, String message);

        void onTriangulationGenerated();

        void onGenerationFailed();
    }
}
