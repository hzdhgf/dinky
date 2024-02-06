package org.dinky.data.enums;

public enum HdfsSecurityType {
    KERBEROS("kerberos");

    private final String type;

    public String getType() {
        return this.type;
    }

    HdfsSecurityType(String type) {
        this.type = type;
    }
}
