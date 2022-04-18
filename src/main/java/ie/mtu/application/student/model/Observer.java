package ie.mtu.application.student.model;

public interface Observer {
    void updateView(University university);
    void displayMessage(String errorMessage, String colorHexa);
}
