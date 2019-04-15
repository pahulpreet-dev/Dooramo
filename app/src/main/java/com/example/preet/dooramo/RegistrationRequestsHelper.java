package com.example.preet.dooramo;

/**
 * Helper class to populate the RegistrationRequests ListView
 */
public class RegistrationRequestsHelper {
    private String username;
    private String name;
    private String email;
    private String phone;
    private String metaData;
    private String requestByFlag;

    public RegistrationRequestsHelper() {
    }

    public String getRequestByFlag() {
        return requestByFlag;
    }

    public void setRequestByFlag(String requestByFlag) {
        this.requestByFlag = requestByFlag;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getMetaData() {
        return metaData;
    }

    public void setMetaData(String metaData) {
        this.metaData = metaData;
    }

    public RegistrationRequestsHelper(String username, String name, String email,
                                      String phone, String metaData, String requestByFlag) {
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.metaData = metaData;
        this.requestByFlag = requestByFlag;
    }
}
