package ru.pussy_penetrator.response;

public enum StatusResponse {
    SUCCESS("success"), ERROR("error");

    private final String status;

    StatusResponse(String status) {
        this.status = status;
    }

    String get() {
        return this.status;
    }
}
