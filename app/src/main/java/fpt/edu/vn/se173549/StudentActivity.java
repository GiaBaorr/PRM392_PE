package fpt.edu.vn.se173549;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Comparator;
import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private ListView listView;
    private ListView majorListView;

    private Button addStudentButton;
    private Button addMajorButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_student);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        initView();
    }

    private void initView() {
        addStudentButton = findViewById(R.id.addStudentButton);
        addMajorButton = findViewById(R.id.addMajorButton);

        listView = findViewById(R.id.listView);
        majorListView = findViewById(R.id.majorListView);
        getAllStudent();

        addStudentButton.setOnClickListener(view -> {
            Intent intent = new Intent(StudentActivity.this, AddStudentActivity.class);
            startActivity(intent);
        });

        addMajorButton.setOnClickListener(view -> {
            Intent intent = new Intent(StudentActivity.this, AddMajorActivity.class);
            startActivity(intent);
        });
    }

    private void getAllStudent() {
        StudentDatabase db = StudentDatabase.getInstance(getApplicationContext());
        List<Student> students = db.getAllStudents();
        students.sort(Comparator.comparing(Student::getName));
        List<Major> majors = db.getAllMajors();
        majors.sort(Comparator.comparing(Major::getNameMajor));

        StudentAdapter adapter = new StudentAdapter(this, students, majors);
        listView.setAdapter(adapter);
        MajorAdapter majorAdapter = new MajorAdapter(this, majors, students);
        majorListView.setAdapter(majorAdapter);
    }
}