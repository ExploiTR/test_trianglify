package com.example.test_trianglify;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.test_trianglify.trianglify.presenters.Presenter;
import com.example.test_trianglify.trianglify.views.TrianglifyView;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private TrianglifyView trianglifyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        trianglifyView = findViewById(R.id.trianglifyView);
        Button generateButton = findViewById(R.id.generateButton);

        trianglifyView.setGenerateListener(new Presenter.TrianglifyGenerateListener() {
            @Override
            public void onTriangulationGenerationStarted() {
                Log.d(TAG, "Generation started");
            }

            @Override
            public void onTriangulationGenerationInProgress(int total, int current, String message) {
                Log.d(TAG, "Progress: " + current + "/" + total + " - " + message);
            }

            @Override
            public void onTriangulationGenerated() {
                Log.d(TAG, "Generation completed");
            }

            @Override
            public void onGenerationFailed() {
                Log.e(TAG, "Generation failed");
            }
        });

        generateButton.setOnClickListener(v -> {
            long startTime = System.currentTimeMillis();
            trianglifyView.smartUpdate();
            Log.d(TAG, "Generation triggered at: " + startTime);
        });
    }
}