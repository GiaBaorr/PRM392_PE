package fpt.edu.vn.se173549;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class StudentDatabase extends SQLiteOpenHelper {

    private static StudentDatabase instance;

    // Table names
    private static final String TABLE_STUDENT = "Student";
    private static final String TABLE_MAJOR = "Major";
    // Columns
    private static final String COLUMN_ID = "ID";

    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_GENDER = "gender";
    private static final String COLUMN_EMAIL = "email";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_ID_MAJOR = "idMajor";

    private static final String COLUMN_NAME_MAJOR = "nameMajor";


    public static synchronized StudentDatabase getInstance(Context context) {
        if (instance == null) {
            instance = new StudentDatabase(context.getApplicationContext());
        }
        return instance;
    }

    public StudentDatabase(Context context) {
        super(context, "student.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(majorTableDDL());
        db.execSQL(studentTableDDL());

        populateData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_STUDENT);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MAJOR);

        onCreate(db);
    }

    private String studentTableDDL() {
        return "CREATE TABLE " + TABLE_STUDENT + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                + COLUMN_DATE + " TEXT, "  // Storing date as TEXT in "yyyy-MM-dd" format
                + COLUMN_GENDER + " INTEGER, " // 1 for male, 0 for female
                + COLUMN_EMAIL + " TEXT, "
                + COLUMN_ADDRESS + " TEXT, "
                + COLUMN_ID_MAJOR + " INTEGER, "
                + "FOREIGN KEY(" + COLUMN_ID_MAJOR + ") REFERENCES " + TABLE_MAJOR + "(" + COLUMN_ID + ")"
                + ")";
    }

    private String majorTableDDL() {
        return "CREATE TABLE " + TABLE_MAJOR + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME_MAJOR + " TEXT"
                + ")";
    }

    private void populateData(SQLiteDatabase db) {
        String insertMajor1 = "INSERT INTO Major (nameMajor) VALUES ('Software Engineer');";
        String insertMajor2 = "INSERT INTO Major (nameMajor) VALUES ('AI');";
        String insertMajor3 = "INSERT INTO Major (nameMajor) VALUES ('Marketing');";

        db.execSQL(insertMajor1);
        db.execSQL(insertMajor2);
        db.execSQL(insertMajor3);

        String insertStudent1 = "INSERT INTO Student (name, date, gender, email, address, idMajor) " +
                "VALUES ('Tran Gia Bao 1', '2000-01-01', 1, 'bao1@example.com', '123 Ly Thuong Kiet', 1);";
        String insertStudent2 = "INSERT INTO Student (name, date, gender, email, address, idMajor) " +
                "VALUES ('Tran Gia Bao 2', '1999-05-15', 0, 'bao2@example.com', '456 Vo Van Kiet', 2);";
        String insertStudent3 = "INSERT INTO Student (name, date, gender, email, address, idMajor) " +
                "VALUES ('Tran Gia Bao 3', '2001-08-20', 1, 'bao3@example.com', '789 Le Dai Hanh', 2);";

        db.execSQL(insertStudent1);
        db.execSQL(insertStudent2);
        db.execSQL(insertStudent3);
    }

    public List<Student> getAllStudents() {
        List<Student> studentList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Query to select all students
        String query = "SELECT * FROM " + TABLE_STUDENT;
        Cursor cursor = db.rawQuery(query, null);

        // Loop through the cursor to retrieve student data
        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String name = cursor.getString(1);
                String dateString = cursor.getString(2);
                LocalDate date = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
                boolean gender = cursor.getInt(3) == 1;
                String email = cursor.getString(4);
                String address = cursor.getString(5);
                long idMajor = cursor.getLong(6);

                // Create a Student object and add it to the list
                Student student = new Student(id, name, date, gender, email, address, idMajor);
                studentList.add(student);
            } while (cursor.moveToNext());
        }

        // Close the cursor and database
        cursor.close();
        db.close();

        return studentList;
    }

    public List<Major> getAllMajors() {
        List<Major> majorList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT ID, nameMajor FROM Major", null);

        if (cursor.moveToFirst()) {
            do {
                long id = cursor.getLong(0);
                String nameMajor = cursor.getString(1);

                Major major = new Major(id, nameMajor);
                majorList.add(major);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return majorList;
    }

    public boolean deleteMajorById(Long majorId) {
        SQLiteDatabase db = this.getWritableDatabase();
        int aRow = db.delete(TABLE_MAJOR, COLUMN_ID + " = ?", new String[]{String.valueOf(majorId)});
        db.close();
        return aRow > 0;
    }

    public boolean deleteStudent(Long id) {
        SQLiteDatabase db = this.getWritableDatabase();
        int aRow = db.delete(TABLE_STUDENT, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
        return aRow > 0;
    }

    public boolean insertMajor(Major major) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("nameMajor", major.getNameMajor());

        long newRowId = db.insert(TABLE_MAJOR, null, values);

        db.close();
        return newRowId != -1;
    }
}
