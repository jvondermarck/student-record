package ie.mtu.application.student.model;

/**
 * The class Module that contains a name, a grade and the ID of the student that take this module
 */
public class Module {

    /**
     * The name of the module
     */
    private final String moduleName;
    /**
     * The grade of the module
     */
    private final int gradeModule;
    /**
     * The ID of the Student that take this module
     */
    private final int id;

    /**
     * Set up a module
     * @param name of the moduke
     * @param grade of the module
     * @param id student
     */
    public Module(String name, int grade, int id)
    {
        this.moduleName = name;
        this.gradeModule = grade;
        this.id = id;
    }

    /**
     * @return the name of the moddule
     */
    public String getModuleName() {
        return moduleName;
    }

    /**
     * @return the grade of the moddule
     */
    public int getGradeModule() {
        return gradeModule;
    }

    /**
     * @return the ID of the student that take this module
     */
    public int getId() {
        return this.id;
    }

    @Override
    public String toString() {
        return this.id + "," + this.moduleName + "," + this.gradeModule;
    }
}
