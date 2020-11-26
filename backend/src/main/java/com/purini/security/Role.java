package com.purini.security;

public enum Role {
    ROLE_SAMPLE("Sample Role");

    private String description;

    Role(String description) {
        this.description = description;
    }

    public String description() {
        return description;
    }

    public String suffix() {
        return name().substring(5);
    }
}
