package com.example.test_trianglify.trianglify.utilities.triangulator;

import androidx.annotation.NonNull;

/**
 * 2D vector class implementation.
 *
 * @author Johannes Diemke
 */
public class Vector2D {

    public float x;
    public float y;

    /**
     * Constructor of the 2D vector class used to create new vector instances.
     *
     * @param x The x coordinate of the new vector
     * @param y The y coordinate of the new vector
     */
    public Vector2D(float x, float y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Subtracts the given vector from this.
     *
     * @param vector The vector to be subtracted from this
     * @return A new instance holding the result of the vector subtraction
     */
    public Vector2D sub(Vector2D vector) {
        return new Vector2D(this.x - vector.x, this.y - vector.y);
    }

    /**
     * Adds the given vector to this.
     *
     * @param vector The vector to be added to this
     * @return A new instance holding the result of the vector addition
     */
    public Vector2D add(Vector2D vector) {
        return new Vector2D(this.x + vector.x, this.y + vector.y);
    }

    /**
     * Multiplies this by the given scalar.
     *
     * @param scalar The scalar to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public Vector2D multiply(float scalar) {
        return new Vector2D(this.x * scalar, this.y * scalar);
    }

    /**
     * Computes the magnitude or length of this.
     *
     * @return The magnitude of this
     */
    public float mag() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y);
    }

    /**
     * Computes the dot product of this and the given vector.
     *
     * @param vector The vector to be multiplied by this
     * @return A new instance holding the result of the multiplication
     */
    public float dot(Vector2D vector) {
        return this.x * vector.x + this.y * vector.y;
    }

    /**
     * Computes the 2D pseudo cross product Dot(Perp(this), vector) of this and
     * the given vector.
     *
     * @param vector The vector to be multiplied to the perpendicular vector of
     *               this
     * @return A new instance holding the result of the pseudo cross product
     */
    public float cross(Vector2D vector) {
        return this.y * vector.x - this.x * vector.y;
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Add copy constructor
    public Vector2D(Vector2D other) {
        this.x = other.x;
        this.y = other.y;
    }

    @NonNull
    @Override
    public String toString() {
        return "Vector2D[" + x + ", " + y + "]";
    }

}