package ie.mtu.application.student.model;

/**
 * The interface Observer will need to be implemented by the classes from the View package
 * The classes from the Model package will call this method when it will be necessary to update the View GUI
 */
public interface Observer {
    /**
     * The method will be called by the model when it will be necessary to make some change in the view that will implement this class
     * @param university : the main class of the Model package
     */
    void updateView(University university);

    /**
     * Method that will be call when we want to pop up a message in the View
     * @param errorMessage : The message that we want to write
     * @param colorHexa : The hexadecimal color we want the message to be
     */
    void displayMessage(String errorMessage, String colorHexa);
}
