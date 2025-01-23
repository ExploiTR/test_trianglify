package com.example.test_trianglify.trianglify.utilities.patterns;

import com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by suyash on 17/3/17.
 */

public interface Patterns {
    // Add method to get estimated point count
    int getEstimatedPointCount();

    // Modified to accept a pre-allocated list
    void generateInto(List<Vector2D> target);

    default List<Vector2D> generate() {
        List<Vector2D> points = new ArrayList<>(getEstimatedPointCount());
        generateInto(points);
        return points;
    }
}
