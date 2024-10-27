package fpt.edu.vn.se173549;

import android.app.AlertDialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class StudentAdapter extends ArrayAdapter<Student> {
    List<Major> majors;
    List<Student> students;
    private StudentDatabase db;

    public StudentAdapter(Context context, List<Student> studentsInput, List<Major> majorsList) {
        super(context, 0, studentsInput);
        majors = majorsList;
        db = StudentDatabase.getInstance(context);
        students = studentsInput;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Student student = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.student_item, parent, false);
        }

        TextView studentName = convertView.findViewById(R.id.studentName);
        TextView studentDate = convertView.findViewById(R.id.studentDate);
        TextView studentEmail = convertView.findViewById(R.id.studentEmail);
        TextView studentGender = convertView.findViewById(R.id.studentGender);
        TextView studentMajor = convertView.findViewById(R.id.studentMajor);
        Button buttonDelete = convertView.findViewById(R.id.buttonDelete);

        studentName.setText(student.getName());
        studentMajor.setText(majors.stream().filter(x -> x.getIdMajor().equals(student.getIdMajor())).findFirst().get().getNameMajor());
        studentDate.setText(student.getDate().toString());
        studentEmail.setText(student.getEmail());
        studentGender.setText(student.isGender() ? "Male" : "Female");

        buttonDelete.setOnClickListener(v -> deleteStudent(student));

        return convertView;
    }

    private void deleteStudent(Student student) {
        // Show confirmation dialog for deletion
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Student")
                .setMessage("Are you sure you want to delete this student?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    // Proceed with deletion
                    if (db.deleteStudent(student.getId())) { // Assuming getId() returns the student's ID
                        students.remove(student);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Student deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error while deleting student", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null) // Dismiss dialog on No
                .show();
    }
}
