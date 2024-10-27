package fpt.edu.vn.se173549;

import java.io.Serializable;
import java.time.LocalDate;

public class Major implements Serializable {
    private Long idMajor;
    private String nameMajor;

    public Major() {
    }

    public Major(Long idMajor, String nameMajor) {
        this.idMajor = idMajor;
        this.nameMajor = nameMajor;
    }

    public Long getIdMajor() {
        return idMajor;
    }

    public void setIdMajor(Long idMajor) {
        this.idMajor = idMajor;
    }

    public String getNameMajor() {
        return nameMajor;
    }

    public void setNameMajor(String nameMajor) {
        this.nameMajor = nameMajor;
    }
}
