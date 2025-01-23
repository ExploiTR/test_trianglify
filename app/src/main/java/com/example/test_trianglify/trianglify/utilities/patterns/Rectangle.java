package com.example.test_trianglify.trianglify.utilities.patterns;

import com.example.test_trianglify.trianglify.utilities.ThreadLocalRandom;
import com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyash on 12/3/17.
 */

public class Rectangle implements Patterns {
    private final ThreadLocalRandom random;
    private final int bleedX;
    private final int bleedY;

    private final int height;
    private final int width;

    private final int cellSize;
    private final int variance;

    List<Vector2D> grid;

    public Rectangle(int bleedX, int bleedY, int height, int width, int cellSize, int variance) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;

        this.variance = variance;
        this.cellSize = cellSize;

        this.height = height;
        this.width = width;

        random = new ThreadLocalRandom();

        grid = new ArrayList<>();
    }

    @Override
    public int getEstimatedPointCount() {
        int cols = (width + 2 * bleedX) / cellSize + 1;
        int rows = (height + 2 * bleedY) / cellSize + 1;
        return cols * rows;
    }

    @Override
    public void generateInto(List<Vector2D> target) {
        target.clear();

        int x, y;
        Vector2D point = new Vector2D(0, 0); // Reuse single Vector2D

        for (int j = 0; j < height + 2 * bleedY; j += cellSize) {
            for (int i = 0; i < width + 2 * bleedX; i += cellSize) {
                x = i + (variance > 0 ? random.nextInt(variance) : 0);
                y = j + (variance > 0 ? random.nextInt(variance) : 0);

                // Update point values instead of creating new object
                point.x = x;
                point.y = y;

                // Add copy of point to list
                target.add(new Vector2D(point.x, point.y));
            }
        }
    }
}

