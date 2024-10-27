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

public class MajorAdapter extends ArrayAdapter<Major> {
    private final List<Major> majors;
    private StudentDatabase db;
    private List<Student> students;

    public MajorAdapter(Context context, List<Major> majorsInput, List<Student> s) {
        super(context, 0, majorsInput);
        majors = majorsInput;
        db = StudentDatabase.getInstance(context);
        students = s;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Major major = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.major_item, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.textViewMajorName);
        Button deleteButton = convertView.findViewById(R.id.buttonDeleteMajor);
        textView.setText(major.getNameMajor());

        deleteButton.setOnClickListener(v -> {
            deleteMajor(major);
        });

        return convertView;
    }

    private void deleteMajor(Major major) {
        long count = students.stream().filter(s -> s.getIdMajor() == major.getIdMajor()).count();

        if (count > 0) {
            // Show dialog informing that the major cannot be deleted
            new AlertDialog.Builder(getContext())
                    .setTitle("Cannot Delete Major")
                    .setMessage("There are students currently enrolled in this major. You cannot delete it.")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            return;
        }

        // Show confirmation dialog for deletion
        new AlertDialog.Builder(getContext())
                .setTitle("Delete Major")
                .setMessage("Are you sure you want to delete this major?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    if (db.deleteMajorById(major.getIdMajor())) {
                        majors.remove(major);
                        notifyDataSetChanged();
                        Toast.makeText(getContext(), "Major deleted successfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Error while deleting", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", null) // Dismiss dialog on No
                .show();
    }


}
