package org.comrades.springtime.module;

public enum Role {

    ROLE_USER("USER"),
    ROLE_ADMIN("ADMIN");


    private String roleName;
    Role(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return roleName;
    }

}
