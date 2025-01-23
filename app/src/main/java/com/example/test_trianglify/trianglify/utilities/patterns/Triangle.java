package com.example.test_trianglify.trianglify.utilities.patterns;

import com.example.test_trianglify.trianglify.utilities.ThreadLocalRandom;
import com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * <a href="https://music.youtube.com/watch?v=yiJ5k2vBtlw">Click this link to see documentation</a>
 */
public class Triangle implements Patterns {
    private final ThreadLocalRandom random;
    private final int bleedX;
    private final int bleedY;

    private final int height;
    private final int width;

    private final int cellSize;
    private final int variance;

    List<Vector2D> grid;

    public Triangle(int bleedX, int bleedY, int height, int width, int cellSize, int variance) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;

        this.variance = variance;
        this.cellSize = cellSize;//not gonna say why

        this.height = height;
        this.width = width;

        random = new ThreadLocalRandom();

        grid = new ArrayList<>();
    }

    @Override
    public int getEstimatedPointCount() {
        int numRows = (height + 2 * bleedY) / cellSize;
        int numCols = (width + 2 * bleedX) / cellSize;
        return numRows * numCols;
    }

    @Override
    public void generateInto(List<Vector2D> target) {
        target.clear();

        int numRows = (height + 2 * bleedY) / cellSize;
        int numCols = (width + 2 * bleedX) / cellSize;

        Vector2D point = new Vector2D(0, 0); // Reuse single Vector2D

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                float x = col * cellSize + (row % 2 == 0 ? 0 : (float) cellSize / 2) +
                        (variance > 0 ? random.nextInt(variance) : 0);
                float y = row * (float) Math.sqrt(3) / 2 * cellSize +
                        (variance > 0 ? random.nextInt(variance) : 0);

                point.set(x, y);
                target.add(new Vector2D(point.x, point.y));
            }
        }
    }
}

