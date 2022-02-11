package com.student.model;

public class Module {

    String moduleName;
    int gradeModule;

    public Module(String name, int grade)
    {
        this.moduleName = name;
        this.gradeModule = grade;
    }

    public String getModuleName() {
        return moduleName;
    }

    public int getGradeModule() {
        return gradeModule;
    }
}
