package fpt.edu.vn.se173549;

import java.io.Serializable;
import java.time.LocalDate;

public class Student implements Serializable {
    private Long id;
    private String name;
    private LocalDate date;
    private boolean gender;
    private String email;
    private String address;
    private Long idMajor;

    public Student() {
    }

    public Student(Long id, String name, LocalDate date, boolean gender, String email, String address, Long idMajor) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.gender = gender;
        this.email = email;
        this.address = address;
        this.idMajor = idMajor;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Long getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(Long idMajor) {
        this.idMajor = idMajor;
    }
}
