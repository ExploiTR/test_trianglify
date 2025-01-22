package com.example.test_trianglify.trianglify.utilities.colorizers;

import com.example.test_trianglify.trianglify.models.Triangulation;
import com.example.test_trianglify.trianglify.presenters.Presenter;

/**
 * <h1>Title</h1>
 * <b>Description :</b>
 * <p>
 *
 * @author suyash
 * @since 24/3/17.
 */

public interface ColorInterface {
    Triangulation getColororedTriangulation(Presenter.TrianglifyGenerateListener listener);
}
