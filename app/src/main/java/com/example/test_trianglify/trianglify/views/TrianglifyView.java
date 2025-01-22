package com.example.test_trianglify.trianglify.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;

import com.example.test_trianglify.R;
import com.example.test_trianglify.trianglify.models.Palette;
import com.example.test_trianglify.trianglify.models.Triangulation;
import com.example.test_trianglify.trianglify.presenters.Presenter;

import org.jetbrains.annotations.NotNull;

import java.security.SecureRandom;

/**
 * @noinspection UnusedReturnValue, unused
 */
public class TrianglifyView extends View implements TrianglifyViewInterface {
    private final Presenter presenter;
    private final SecureRandom random = new SecureRandom();
    private int bleedX;
    private int bleedY;
    private int gridHeight;
    private int gridWidth;
    private int typeGrid;
    private int variance;
    private int cellSize;
    private int altCellSize;
    private boolean fillTriangle;
    private boolean drawStroke;
    private boolean randomColoring;
    private Palette palette;
    private Triangulation triangulation;
    private int bitmapQuality;
    private boolean fillViewCompletely;
    /*changes*/
    private float strokeSizeF = 0.5f;
    private Paint reusePaint;
    private Path reusePath;
    private boolean generatingSoup = false;
    private Presenter.TrianglifyGenerateListener listener;

    public static boolean ENABLE_GC = false;

    public TrianglifyView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.TrianglifyView, 0, 0);
        attributeSetter(a);
        this.presenter = new Presenter(this);

        reusePath = new Path();
        reusePath.setFillType(Path.FillType.EVEN_ODD);

        reusePaint = new Paint(Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);
        setLayerType(LAYER_TYPE_HARDWARE, reusePaint);

        updatePaint();
    }

    /**
     * it gives better control.
     * shader, miters and other settings can be easily fine tuned!
     * <p>
     */
    public TrianglifyView setPaintOverride(@NonNull Paint paint, @NonNull Path path) {
        this.reusePaint = paint;
        this.reusePath = path;
        return this;
    }

    public void setGenerateListener(@NotNull Presenter.TrianglifyGenerateListener listener) {
        this.listener = listener;
    }

    @Override
    protected void onSizeChanged(int w, int h, int old_w, int old_h) {
        super.onSizeChanged(w, h, old_w, old_h);
        setGridWidth(w);
        setGridHeight(h);
        smartUpdate();
    }

    private void attributeSetter(TypedArray typedArray) {
        //   bleedX = (int) typedArray.getDimension(R.styleable.TrianglifyView_bleedX, 100);
        //    bleedY = (int) typedArray.getDimension(R.styleable.TrianglifyView_bleedY, 100);
        variance = (int) typedArray.getDimension(R.styleable.TrianglifyView_variance, 0);
        cellSize = (int) typedArray.getDimension(R.styleable.TrianglifyView_cellSize, 32);
        typeGrid = typedArray.getInt(R.styleable.TrianglifyView_gridType, 0);
        fillTriangle = typedArray.getBoolean(R.styleable.TrianglifyView_fillTriangle, true);
        drawStroke = typedArray.getBoolean(R.styleable.TrianglifyView_fillStrokes, false);
        strokeSizeF = typedArray.getFloat(R.styleable.TrianglifyView_strokeSize, strokeSizeF);
        palette = Palette.getPalette(typedArray.getInt(R.styleable.TrianglifyView_palette, 0));
        randomColoring = typedArray.getBoolean(R.styleable.TrianglifyView_randomColoring, false);
        fillViewCompletely = typedArray.getBoolean(R.styleable.TrianglifyView_fillViewCompletely, false);

        bleedX = 70;
        bleedY = 70;
        altCellSize = cellSize;
        cellSize = cellSize; //todo !!!!

        typedArray.recycle();
    }


    @Override
    public int getBleedX() {
        return bleedX;
    }

    public TrianglifyView setBleedX(int bleedX) {
        this.bleedX = bleedX;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getBleedY() {
        return bleedY;
    }

    public TrianglifyView setBleedY(int bleedY) {
        this.bleedY = bleedY;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getGridHeight() {
        return gridHeight;
    }

    public TrianglifyView setGridHeight(int gridHeight) {
        this.gridHeight = gridHeight;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getGridWidth() {
        return gridWidth;
    }

    public TrianglifyView setGridWidth(int gridWidth) {
        this.gridWidth = gridWidth;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Deprecated
    @Override
    public int getBitmapQuality() {
        return 0;
    }

    @Deprecated
    public void setBitmapQuality(int bitmapQuality) {

    }

    @Override
    public int getTypeGrid() {
        return typeGrid;
    }

    public TrianglifyView setTypeGrid(int typeGrid) {
        this.typeGrid = typeGrid;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        return this;
    }

    @Override
    public int getVariance() {
        return variance;
    }

    public TrianglifyView setVariance(int variance) {
        this.variance = variance;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        smartUpdate();
        return this;
    }

    @Override
    public int getCellSize() {
        return cellSize;
    }

    public TrianglifyView setCellSize(int cellSize) {
        altCellSize = cellSize;
        this.cellSize = cellSize; //todo!!!!!!!!
        bleedX = 64;
        bleedY = 64;
        presenter.viewState = Presenter.ViewState.GRID_PARAMETERS_CHANGED;
        smartUpdate();
        return this;
    }

    @Override
    public boolean isFillViewCompletely() {
        return fillViewCompletely;
    }

    public TrianglifyView setFillViewCompletely(boolean fillViewCompletely) {
        this.fillViewCompletely = fillViewCompletely;
        smartUpdate();
        return this;
    }

    @Override
    public boolean isFillTriangle() {
        return fillTriangle;
    }

    public TrianglifyView setFillTriangle(boolean fillTriangle) {
        this.fillTriangle = fillTriangle;
        if (presenter.viewState != Presenter.ViewState.GRID_PARAMETERS_CHANGED && presenter.viewState != Presenter.ViewState.COLOR_SCHEME_CHANGED)
            presenter.viewState = Presenter.ViewState.PAINT_STYLE_CHANGED;
        return this;
    }

    @Override
    public boolean isDrawStrokeEnabled() {
        return drawStroke;
    }

    public TrianglifyView setDrawStrokeEnabled(boolean drawStroke) {
        this.drawStroke = drawStroke;
        if (presenter.viewState != Presenter.ViewState.GRID_PARAMETERS_CHANGED && presenter.viewState != Presenter.ViewState.COLOR_SCHEME_CHANGED)
            presenter.viewState = Presenter.ViewState.PAINT_STYLE_CHANGED;
        this.smartUpdate();
        return this;
    }

    @Override
    public boolean isRandomColoringEnabled() {
        return randomColoring;
    }

    public TrianglifyView setRandomColoring(boolean randomColoring) {
        this.randomColoring = randomColoring;
        if (presenter.viewState != Presenter.ViewState.GRID_PARAMETERS_CHANGED)
            presenter.viewState = Presenter.ViewState.COLOR_SCHEME_CHANGED;
        smartUpdate();
        return this;
    }

    @Override
    public Palette getPalette() {
        return palette;
    }

    public TrianglifyView setPalette(Palette palette) {
        this.palette = palette;
        if (presenter.viewState != Presenter.ViewState.GRID_PARAMETERS_CHANGED)
            presenter.viewState = Presenter.ViewState.COLOR_SCHEME_CHANGED;
        return this;
    }

    @Override
    public Presenter.ViewState getViewState() {
        return presenter.viewState;
    }

    public TrianglifyView setTriangulation(Triangulation triangulation) {
        this.triangulation = triangulation;
        return this;
    }

    public void clearView() {
        presenter.clearSoup();
    }

    @Override
    public void invalidateView(Triangulation triangulation) {
        this.setTriangulation(triangulation);
        invalidate();
        presenter.viewState = Presenter.ViewState.UNCHANGED_TRIANGULATION;
        Log.v("TrianglifyView", "invalidateView");
    }

    private void drawTriangle(Paint paint, Canvas canvas, com.example.test_trianglify.trianglify.utilities.triangulator.Triangle2D triangle2D) {
        reusePath.moveTo(triangle2D.a.x - bleedX, triangle2D.a.y - bleedY);
        reusePath.lineTo(triangle2D.b.x - bleedX, triangle2D.b.y - bleedY);
        reusePath.lineTo(triangle2D.c.x - bleedX, triangle2D.c.y - bleedY);
//        reusePath.moveTo(triangle2D.a.x, triangle2D.a.y);
//        reusePath.lineTo(triangle2D.b.x, triangle2D.b.y);
//        reusePath.lineTo(triangle2D.c.x, triangle2D.c.y);
        reusePath.close();

        canvas.drawPath(reusePath, paint);
        reusePath.reset();
    }

    private Paint.Style getPaintStyle() {
        if (isFillTriangle() && isDrawStrokeEnabled()) return Paint.Style.FILL_AND_STROKE;
        else if (isFillTriangle()) return Paint.Style.FILL;
        else return Paint.Style.STROKE;
    }


    private void updatePaint() {
        reusePaint.setStrokeWidth(strokeSizeF);
        reusePaint.setStyle(getPaintStyle());
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);
        if (triangulation != null && triangulation.triangleList() != null) {
            updatePaint();
            for (com.example.test_trianglify.trianglify.utilities.triangulator.Triangle2D triangle : triangulation.triangleList()) {
                reusePaint.setColor(triangle.getColor() /*+ 0xff000000 stupid*/);
                drawTriangle(reusePaint, canvas, triangle);
            }
        }
    }

    public void smartUpdate() {
        generatingSoup = true;
        presenter.updateView(new Presenter.TrianglifyGenerateListener() {
            @Override
            public void onTriangulationGenerationStarted() {
                listener.onTriangulationGenerationStarted();
            }

            @Override
            public void onTriangulationGenerationInProgress(int total, int current, String message) {
                listener.onTriangulationGenerationInProgress(total, current, message);
            }

            @Override
            public void onTriangulationGenerated() {
                listener.onTriangulationGenerated();
                generatingSoup = false;
                if (TrianglifyView.ENABLE_GC) {
                    System.gc();
                }
            }

            @Override
            public void onGenerationFailed() {
                presenter.updateView(this);
            }
        });
        Log.v("TrianglifyView", "smartUpdate");
    }

    public void generateAndInvalidate() {
        presenter.setGenerateOnlyColor(false);
        presenter.generateSoupAndInvalidateView(new Presenter.TrianglifyGenerateListener() {
            @Override
            public void onTriangulationGenerationStarted() {
                listener.onTriangulationGenerationStarted();
            }

            @Override
            public void onTriangulationGenerationInProgress(int total, int current, String message) {
                listener.onTriangulationGenerationInProgress(total, current, message);
            }

            @Override
            public void onTriangulationGenerated() {
                listener.onTriangulationGenerated();
                generatingSoup = false;
            }

            @Override
            public void onGenerationFailed() {
                presenter.updateView(this);
            }
        }, "Tv394");
        Log.v("TrianglifyView", "generateAndInvalidate");
    }

    public Bitmap getBitmap(int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(gridWidth, gridHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(0xFFFFFFFF);
        draw(canvas);
        int x = (gridWidth - width) / 2;
        int y = (gridHeight - height) / 2;
        return Bitmap.createBitmap(bitmap, x, y, width, height);
    }

}
