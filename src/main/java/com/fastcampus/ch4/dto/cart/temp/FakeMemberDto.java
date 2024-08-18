package com.fastcampus.ch4.dto.cart.temp;

import java.util.Objects;

public class FakeMemberDto {
    String id;
    String pswd;
    String name;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FakeMemberDto)) return false;
        FakeMemberDto that = (FakeMemberDto) o;
        return Objects.equals(id, that.id) && Objects.equals(pswd, that.pswd) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, pswd, name);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPswd() {
        return pswd;
    }

    public void setPswd(String pswd) {
        this.pswd = pswd;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
