package com.student.model;

public interface Observer {
    void updateView(University university);
    void displayError(University university, String errorMessage);
}
