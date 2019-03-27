package com.example.preet.dooramo;

public class RequestDetailsHelper {

    /**
     * Beans helper class for request details
     */
    private String request, id, aptNo, contact, email, service, dateTime, status, name;

    public RequestDetailsHelper(String request, String id, String aptNo, String contact,
                                String email, String service, String dateTime,
                                String status, String name) {
        this.request = request;
        this.id = id;
        this.aptNo = aptNo;
        this.contact = contact;
        this.email = email;
        this.service = service;
        this.dateTime = dateTime;
        this.status = status;
        this.name = name;
    }

    public RequestDetailsHelper() {
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAptNo() {
        return aptNo;
    }

    public void setAptNo(String aptNo) {
        this.aptNo = aptNo;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
