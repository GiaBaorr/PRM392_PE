package fpt.edu.vn.se173549;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView tvTitle, tvDate1, tvDate2, tvResult;
    private Button btnCalculate, btnGoToStudent;

    private String selectedDate1, selectedDate2;
    private SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

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
        initView();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tvTitle);
        tvDate1 = findViewById(R.id.etDate1);
        tvDate2 = findViewById(R.id.etDate2);
        btnCalculate = findViewById(R.id.btnCalculate);
        btnGoToStudent = findViewById(R.id.btnGoToStudent);
        tvResult = findViewById(R.id.tvResult);

        tvDate1.setOnClickListener(v -> showDatePickerDialog(tvDate1, true));
        tvDate2.setOnClickListener(v -> showDatePickerDialog(tvDate2, false));

        btnCalculate.setOnClickListener(v -> calculateDateDifference());
        btnGoToStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, StudentActivity.class);
                startActivity(intent);
            }
        });
    }

    private void showDatePickerDialog(TextView textView, boolean isDate1) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    String date = selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear;
                    textView.setText(date);
                    if (isDate1) {
                        selectedDate1 = date;
                    } else {
                        selectedDate2 = date;
                    }
                },
                year, month, day
        );
        datePickerDialog.show();
    }

    private void calculateDateDifference() {
        if (selectedDate1 != null && selectedDate2 != null) {
            try {
                // Parse the dates
                Date date1 = dateFormat.parse(selectedDate1);
                Date date2 = dateFormat.parse(selectedDate2);

                // Calculate the difference in milliseconds
                long differenceInMillis = Math.abs(date2.getTime() - date1.getTime());

                // Convert the difference to days
                long differenceInDays = differenceInMillis / (24 * 60 * 60 * 1000);

                // Display the result
                tvResult.setText("Kết quả: " + differenceInDays + " ngày");

            } catch (ParseException e) {
                tvResult.setText("Lỗi định dạng ngày!");
            }
        } else {
            tvResult.setText("Vui lòng chọn cả hai ngày.");
        }
    }
}