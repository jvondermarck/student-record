package ie.mtu.application.student.model;

public class Module {

    private final String moduleName;
    private final int gradeModule;
    private final int id;

    public Module(String name, int grade, int id)
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

    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id + "," + this.moduleName + "," + this.gradeModule;
    }
}
