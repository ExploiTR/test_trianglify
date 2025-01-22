package com.example.test_trianglify.trianglify.utilities.triangulator;

/**
 * 2D edge class implementation.
 * 
 * @author Johannes Diemke
 */
public class Edge2D {

    public com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D a;
    public com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D b;

    /**
     * Constructor of the 2D edge class used to create a new edge instance from
     * two 2D vectors describing the edge's vertices.
     * 
     * @param a
     *            The first vertex of the edge
     * @param b
     *            The second vertex of the edge
     */
    public Edge2D(com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D a, com.example.test_trianglify.trianglify.utilities.triangulator.Vector2D b) {
        this.a = a;
        this.b = b;
    }

}