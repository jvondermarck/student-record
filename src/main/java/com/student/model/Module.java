package com.student.model;

import java.io.Serial;
import java.io.Serializable;

public class Module implements Serializable {

    @Serial
    private static final long serialVersionUID = 5952316905054943169L;

    String moduleName;
    int gradeModule;
    String id;

    public Module(String name, int grade, String id)
    {
        this.moduleName = name;
        this.gradeModule = grade;
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getGradeModule() {
        return gradeModule;
    }

    public String getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id + "," + this.moduleName + "," + this.gradeModule;
    }
}
