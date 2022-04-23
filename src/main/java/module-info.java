module com.student.student {
    requires javafx.controls;
	requires java.sql;
    requires org.apache.derby.tools;
    requires org.junit.jupiter.api;
    //requires javafx.fxml;
    //requires org.controlsfx.controls;
    requires org.junit.platform.commons;
    requires javax.persistence;
    exports ie.mtu.application.student.controller;
    exports ie.mtu.application.student.view;
    exports ie.mtu.application.student.model;
    exports ie.mtu.application.student.launcher;
}