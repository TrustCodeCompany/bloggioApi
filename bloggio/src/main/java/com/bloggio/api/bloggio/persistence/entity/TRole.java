package com.bloggio.api.bloggio.persistence.entity;

public enum TRole {

    T_ROLE_USER(1),
    T_ROLE_ADMIN(2);

    private String desc_role;
    private int value;

    private TRole(int value) {
        this.value = value;
    }

    public String getDesc_role() {
        return desc_role;
    }

    public void setDesc_role(String desc_role) {
        this.desc_role = desc_role;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

}
