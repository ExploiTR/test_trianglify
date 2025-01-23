package com.example.test_trianglify.trianglify.utilities.triangulator;

import android.util.Log;

import com.example.test_trianglify.trianglify.presenters.Presenter;
import com.example.test_trianglify.trianglify.views.TrianglifyView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

/**
 * A Java implementation of an incremental 2D Delaunay triangulation algorithm.
 * <p>
 * author Johannes Diemke
 */
public class DTIterativeLegalize {

    private final Presenter.TrianglifyGenerateListener listener;
    private List<Vector2D> pointSet;
    private TriangleSoup triangleSoup;

    /**
     * Constructor of the SimpleDelaunayTriangulation class used to create a new
     * triangulation instance.
     *
     * @param pointSet The point set to be triangulated
     */
    public DTIterativeLegalize(List<Vector2D> pointSet, Presenter.TrianglifyGenerateListener listener) {
        this.pointSet = pointSet;
        this.triangleSoup = new TriangleSoup();
        this.listener = listener;
        listener.onTriangulationGenerationInProgress(1, 1, "Preparing Triangulation");
    }

    /**
     * This method generates a Delaunay triangulation from the specified point
     * set.
     */
    public void triangulate() throws NotEnoughPointsException {
        Log.i("TrianglifyView", "Start triangulate()");

        if (Presenter.ASKED_TO_ELEM) {
            Log.w("TrianglifyView", "Interrupt");
            return;
        }

        triangleSoup = new TriangleSoup();

        if (pointSet == null || pointSet.size() < 3) {
            throw new NotEnoughPointsException("Less than three points in point set.");
        }

        float maxCoordinate = computeMaxCoordinateScale();

        final Vector2D p1 = new Vector2D(0.0f, 3.0f * maxCoordinate);
        final Vector2D p2 = new Vector2D(3.0f * maxCoordinate, 0.0f);
        final Vector2D p3 = new Vector2D(-3.0f * maxCoordinate, -3.0f * maxCoordinate);

        final Triangle2D superTriangle = new Triangle2D(p1, p2, p3);

        triangleSoup.add(superTriangle);

        listener.onTriangulationGenerationInProgress(1, 1, "Generating Triangles");

        for (int i = 0; i < pointSet.size(); i++) {

            if (Presenter.ASKED_TO_ELEM) {
                Log.w("TrianglifyView", "Interrupt");
                return;
            }

            if (TrianglifyView.ENABLE_GC) {
                if (pointSet.size() % (i + 250) == 0)
                    System.gc();
            }


            listener.onTriangulationGenerationInProgress(pointSet.size(), i + 1, "Generating Vertices");

            Triangle2D triangle = triangleSoup.findContainingTriangle(pointSet.get(i));

            if (triangle == null) {
                Edge2D edge = triangleSoup.findNearestEdge(pointSet.get(i));

                Triangle2D first = triangleSoup.findOneTriangleSharing(edge);
                Triangle2D second = triangleSoup.findNeighbour(first, edge);

                Vector2D firstNoneEdgeVertex = first.getNoneEdgeVertex(edge);
                Vector2D secondNoneEdgeVertex = second.getNoneEdgeVertex(edge);

                triangleSoup.remove(first);
                triangleSoup.remove(second);

                Triangle2D triangle1 = new Triangle2D(edge.a, firstNoneEdgeVertex, pointSet.get(i));
                Triangle2D triangle2 = new Triangle2D(edge.b, firstNoneEdgeVertex, pointSet.get(i));
                Triangle2D triangle3 = new Triangle2D(edge.a, secondNoneEdgeVertex, pointSet.get(i));
                Triangle2D triangle4 = new Triangle2D(edge.b, secondNoneEdgeVertex, pointSet.get(i));

                triangleSoup.add(triangle1);
                legalizeEdge(triangle1, new Edge2D(edge.a, firstNoneEdgeVertex), pointSet.get(i));

                triangleSoup.add(triangle2);
                triangleSoup.add(triangle3);
                triangleSoup.add(triangle4);

                legalizeEdge(triangle2, new Edge2D(edge.b, firstNoneEdgeVertex), pointSet.get(i));
                legalizeEdge(triangle3, new Edge2D(edge.a, secondNoneEdgeVertex), pointSet.get(i));
                legalizeEdge(triangle4, new Edge2D(edge.b, secondNoneEdgeVertex), pointSet.get(i));
            } else {
                Vector2D a = triangle.a;
                Vector2D b = triangle.b;
                Vector2D c = triangle.c;

                triangleSoup.remove(triangle);

                Triangle2D first = new Triangle2D(a, b, pointSet.get(i));
                Triangle2D second = new Triangle2D(b, c, pointSet.get(i));
                Triangle2D third = new Triangle2D(c, a, pointSet.get(i));

                triangleSoup.add(first);
                legalizeEdge(first, new Edge2D(a, b), pointSet.get(i));

                triangleSoup.add(second);
                legalizeEdge(second, new Edge2D(b, c), pointSet.get(i));

                triangleSoup.add(third);
                legalizeEdge(third, new Edge2D(c, a), pointSet.get(i));
            }
        }

        triangleSoup.removeTrianglesUsing(superTriangle.a);
        triangleSoup.removeTrianglesUsing(superTriangle.b);
        triangleSoup.removeTrianglesUsing(superTriangle.c);

        if (TrianglifyView.ENABLE_GC) {
            System.gc();
        }

    }

    private float computeMaxCoordinateScale() {
        float maxCoordinate = 0.0f;

        int prog = 0;
        for (Vector2D vector : getPointSet()) {
            maxCoordinate = Math.max(Math.max(vector.x, vector.y), maxCoordinate);
            listener.onTriangulationGenerationInProgress(prog++, getPointSet().size(), "Finalising Grid");
        }

        return maxCoordinate * 16.0f;
    }

    /**
     * This method legalizes edges iteratively by flipping all illegal edges.
     *
     * @param triangle  The triangle
     * @param edge      The edge to be legalized
     * @param newVertex The new vertex
     */
    private void legalizeEdge(Triangle2D triangle, Edge2D edge, Vector2D newVertex) {
        Stack<EdgeTrianglePair> edgesToCheck = new Stack<>();
        edgesToCheck.push(new EdgeTrianglePair(edge, triangle));

        while (!edgesToCheck.isEmpty()) {
            EdgeTrianglePair pair = edgesToCheck.pop();
            Edge2D currentEdge = pair.edge;
            Triangle2D currentTriangle = pair.triangle;

            Triangle2D neighbourTriangle = triangleSoup.findNeighbour(currentTriangle, currentEdge);

            if (neighbourTriangle != null && neighbourTriangle.isPointInCircumCircle(newVertex)) {
                triangleSoup.remove(currentTriangle);
                triangleSoup.remove(neighbourTriangle);

                Vector2D noneEdgeVertex = neighbourTriangle.getNoneEdgeVertex(currentEdge);

                Triangle2D firstTriangle = new Triangle2D(noneEdgeVertex, currentEdge.a, newVertex);
                Triangle2D secondTriangle = new Triangle2D(noneEdgeVertex, currentEdge.b, newVertex);

                triangleSoup.add(firstTriangle);
                triangleSoup.add(secondTriangle);

                edgesToCheck.push(new EdgeTrianglePair(new Edge2D(noneEdgeVertex, currentEdge.a), firstTriangle));
                edgesToCheck.push(new EdgeTrianglePair(new Edge2D(noneEdgeVertex, currentEdge.b), secondTriangle));
            }
        }

        if(TrianglifyView.ENABLE_GC){
            System.gc();
        }

    }

    public void shuffle() {
        Collections.shuffle(pointSet);
    }

    public void shuffle(int[] permutation) {
        List<Vector2D> temp = new ArrayList<>();
        for (int j : permutation)
            temp.add(pointSet.get(j));

        pointSet = temp;
    }

    public List<Vector2D> getPointSet() {
        return pointSet;
    }

    public List<Triangle2D> getTriangles() {
        return triangleSoup.getTriangles();
    }

    private static class EdgeTrianglePair {
        Edge2D edge;
        Triangle2D triangle;

        EdgeTrianglePair(Edge2D edge, Triangle2D triangle) {
            this.edge = edge;
            this.triangle = triangle;
        }
    }
}