package com.fastcampus.ch4.dto.admin;

import java.util.Date;
import java.util.Objects;

public class AdminDto {
    String id;
    int auth;
    Date reg_date;
    String reg_id;
    Date up_date;
    String up_id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdminDto adminDto = (AdminDto) o;
        return auth == adminDto.auth && Objects.equals(id, adminDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, auth);
    }

    public AdminDto() {
    }

    public AdminDto(String id, int auth, String reg_id) {
        this.id = id;
        this.auth = auth;
        this.reg_id = reg_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getAuth() {
        return auth;
    }

    public void setAuth(int auth) {
        this.auth = auth;
    }

    public Date getReg_date() {
        return reg_date;
    }

    public void setReg_date(Date reg_date) {
        this.reg_date = reg_date;
    }

    public String getReg_id() {
        return reg_id;
    }

    public void setReg_id(String reg_id) {
        this.reg_id = reg_id;
    }

    public Date getUp_date() {
        return up_date;
    }

    public void setUp_date(Date up_date) {
        this.up_date = up_date;
    }

    public String getUp_id() {
        return up_id;
    }

    public void setUp_id(String up_id) {
        this.up_id = up_id;
    }

    @Override
    public String toString() {
        return "AdminDto{" +
                "id='" + id + '\'' +
                ", auth=" + auth +
                ", reg_date=" + reg_date +
                ", reg_id='" + reg_id + '\'' +
                ", up_date=" + up_date +
                ", up_id='" + up_id + '\'' +
                '}';
    }
}
