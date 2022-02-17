package com.student.model;

public interface Observer {
    void updateView(University university);
    void displayMessage(String errorMessage, String colorHexa);
}
