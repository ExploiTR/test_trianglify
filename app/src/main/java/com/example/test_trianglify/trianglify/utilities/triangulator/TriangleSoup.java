package com.example.test_trianglify.trianglify.utilities.triangulator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Triangle soup class implementation.
 * <p>
 * author Johannes Diemke
 */
public class TriangleSoup {

    private final HashSet<Triangle2D> triangleSoup;

    /**
     * Constructor of the triangle soup class used to create a new triangle soup
     * instance.
     */
    public TriangleSoup() {
        this.triangleSoup = new HashSet<>();
    }

    /**
     * Adds a triangle to this triangle soup.
     *
     * @param triangle The triangle to be added to this triangle soup
     */
    public void add(Triangle2D triangle) {
        triangleSoup.add(triangle);
    }

    /**
     * Removes a triangle from this triangle soup.
     *
     * @param triangle The triangle to be removed from this triangle soup
     */
    public void remove(Triangle2D triangle) {
        triangleSoup.remove(triangle);
    }

    /**
     * Returns the triangles from this triangle soup.
     *
     * @return The triangles from this triangle soup
     */
    public List<Triangle2D> getTriangles() {
        return new ArrayList<>(triangleSoup);
    }

    /**
     * Returns the triangle from this triangle soup that contains the specified
     * point or null if no triangle from the triangle soup contains the point.
     *
     * @param point The point
     * @return Returns the triangle from this triangle soup that contains the
     * specified point or null
     */
    public Triangle2D findContainingTriangle(Vector2D point) {
        for (Triangle2D triangle : triangleSoup)
            if (triangle.contains(point))
                return triangle;
        return null;
    }

    /**
     * Returns the neighbor triangle of the specified triangle sharing the same
     * edge as specified. If no neighbor sharing the same edge exists null is
     * returned.
     *
     * @param triangle The triangle
     * @param edge     The edge
     * @return The triangles neighbor triangle sharing the same edge or null if
     * no triangle exists
     */
    public Triangle2D findNeighbour(Triangle2D triangle, Edge2D edge) {
        for (Triangle2D neighbor : triangleSoup)
            if (neighbor != triangle && neighbor.isNeighbour(edge))
                return neighbor;

        return null;
    }

    /**
     * Returns one of the possible triangles sharing the specified edge. Based
     * on the ordering of the triangles in this triangle soup the returned
     * triangle may differ. To find the other triangle that shares this edge use
     * the findNeighbour method.
     *
     * @param edge The edge
     * @return Returns one triangle that shares the specified edge
     */
    public Triangle2D findOneTriangleSharing(Edge2D edge) {
        for (Triangle2D triangle : triangleSoup)
            if (triangle.isNeighbour(edge))
                return triangle;

        return null;
    }

    /**
     * Returns the edge from the triangle soup nearest to the specified point.
     *
     * @param point The point
     * @return The edge from the triangle soup nearest to the specified point
     */
    public Edge2D findNearestEdge(Vector2D point) {
        EdgeDistancePack closestEdge = null;
        double minDistance = Double.MAX_VALUE;

        for (Triangle2D triangle : triangleSoup) {
            EdgeDistancePack edgeDistancePack = triangle.findNearestEdge(point);
            if (edgeDistancePack.distance < minDistance) {
                minDistance = edgeDistancePack.distance;
                closestEdge = edgeDistancePack;
            }
        }

        return closestEdge != null ? closestEdge.edge : null;
    }

    /**
     * Removes all triangles from this triangle soup that contain the specified
     * vertex.
     *
     * @param vertex The vertex
     */
    public void removeTrianglesUsing(Vector2D vertex) {
        Set<Triangle2D> trianglesToRemove = new HashSet<>();

        for (Triangle2D triangle : triangleSoup)
            if (triangle.hasVertex(vertex))
                trianglesToRemove.add(triangle);

        triangleSoup.removeAll(trianglesToRemove);
    }
}