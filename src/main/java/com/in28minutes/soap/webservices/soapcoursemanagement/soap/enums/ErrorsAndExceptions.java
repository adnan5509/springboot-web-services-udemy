package com.in28minutes.soap.webservices.soapcoursemanagement.soap.enums;

public enum ErrorsAndExceptions {
    COURSE_NOT_FOUND(1, "Course not found");
    private final int code;
    private final String message;

    ErrorsAndExceptions(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "ErrorsAndExceptions{" +
                "code=" + code +
                ", message='" + message + '\'' +
                '}';
    }
}
