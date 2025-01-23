package com.example.test_trianglify.trianglify.utilities.patterns;

import com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by suyash on 12/3/17.
 */

public class Circle implements Patterns {
    private final Random random = new Random();
    public List<Vector2D> grid;
    private int bleedX = 0;
    private int bleedY = 0;
    private int pointsPerCircle = 8;
    private int height = 0;
    private int width = 0;
    private int cellSize = 0;
    private int variance = 0;

    public Circle(int bleedX, int bleedY, int pointsPerCircle, int height, int width, int cellSize, int variance) {
        this.bleedX = bleedX;
        this.bleedY = bleedY;

        this.pointsPerCircle = pointsPerCircle;

        this.height = height;
        this.width = width;

        this.cellSize = cellSize;
        this.variance = variance;

        //why no one tested this?
        this.grid = new ArrayList<>();
    }

    public Random getRandom() {
        return random;
    }

    public int getBleedX() {
        return bleedX;
    }

    public void setBleedX(int bleedX) {
        this.bleedX = bleedX;
    }

    public int getBleedY() {
        return bleedY;
    }

    public void setBleedY(int bleedY) {
        this.bleedY = bleedY;
    }

    public int getPointsPerCircle() {
        return pointsPerCircle;
    }

    public void setPointsPerCircle(int pointsPerCircle) {
        this.pointsPerCircle = pointsPerCircle;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getCellSize() {
        return cellSize;
    }

    public void setCellSize(int cellSize) {
        this.cellSize = cellSize;
    }

    public int getVariance() {
        return variance;
    }

    public void setVariance(int variance) {
        this.variance = variance;
    }

    @Override
    public int getEstimatedPointCount() {
        int maxRadius = Math.max(width + bleedX, height + bleedY);
        int circleCount = maxRadius / cellSize;
        return 1 + (circleCount * pointsPerCircle); // Center point + points per circle
    }

    @Override
    public void generateInto(List<Vector2D> target) {
        Vector2D center = new Vector2D(width - bleedX * 1f / 4, height - bleedY * 1f / 2);
        target.clear();
        target.add(new Vector2D(center.x, center.y));

        int maxRadius = Math.max(width + bleedX, height + bleedY);
        double slice, angle;
        int x, y;

        cellSize = (int) (cellSize - 0.5F * cellSize);
        cellSize = Math.max(cellSize, 8);

        Vector2D point = new Vector2D(0, 0); // Reuse single Vector2D

        for (int radius = cellSize; radius < maxRadius; radius += cellSize) {
            slice = 2 * Math.PI / pointsPerCircle;
            for (int i = 0; i < pointsPerCircle; i++) {
                angle = slice * i;
                x = (int) (center.x + radius * Math.cos(angle)) + (variance > 0 ? random.nextInt(variance) : 0);
                y = (int) (center.y + radius * Math.sin(angle)) + (variance > 0 ? random.nextInt(variance) : 0);

                point.set(x, y);
                target.add(new Vector2D(point.x, point.y));
            }
        }
    }
}
