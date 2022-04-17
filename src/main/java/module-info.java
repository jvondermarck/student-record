module com.student.student {
    requires javafx.controls;
	requires java.sql;
    requires org.apache.derby.tools;
    //requires javafx.fxml;
    //requires org.controlsfx.controls;
    exports com.student.controller;
    exports com.student.view;
    exports com.student.model;
    exports com.student.launcher;
}