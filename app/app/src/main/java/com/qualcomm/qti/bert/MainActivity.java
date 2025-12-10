package com.qualcomm.qti.bert;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    static {
        System.loadLibrary("native-lib");  // Load native library
    }

    private EditText editTextInput;
    private TextView textViewResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextInput = findViewById(R.id.editTextInput);
        Button buttonAnalyze = findViewById(R.id.buttonAnalyze);
        textViewResult = findViewById(R.id.textViewResult);

        // Initialize native model (load DLC from assets)
        try {
            initModel();
        } catch (IOException e) {
            Toast.makeText(this, R.string.error_model, Toast.LENGTH_LONG).show();
        }

        buttonAnalyze.setOnClickListener(v -> {
            String inputText = editTextInput.getText().toString().trim();
            if (!inputText.isEmpty()) {
                String sentiment = analyzeSentiment(inputText);
                textViewResult.setText(getString(R.string.result_label) + " " + sentiment);
            }
        });
    }

    // Native method declarations
    public native void initModel() throws IOException;
    public native String analyzeSentiment(String inputText);
}