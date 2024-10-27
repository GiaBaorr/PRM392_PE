package fpt.edu.vn.se173549;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class AddMajorActivity extends AppCompatActivity {

    private EditText editTextMajorName;
    private Button buttonSave;
    private StudentDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_add_major);
        initView();
    }

    private void initView() {
        editTextMajorName = findViewById(R.id.editTextMajorName);
        buttonSave = findViewById(R.id.buttonSave);

        db = StudentDatabase.getInstance(getApplicationContext());

        buttonSave.setOnClickListener(view -> addMajor());
    }

    private void addMajor() {
        String majorName = editTextMajorName.getText().toString().trim();
        if (!majorName.isEmpty()) {
            Major newMajor = new Major();
            newMajor.setNameMajor(majorName);
            db.insertMajor(newMajor);
            Toast.makeText(AddMajorActivity.this, "Major added successfully", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), StudentActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(AddMajorActivity.this, "Please enter a major name", Toast.LENGTH_SHORT).show();
        }
    }
}