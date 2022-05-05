package ie.mtu.application.student.launcher;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.*;
import ie.mtu.application.student.view.MainGUI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.sql.Date;
import java.time.LocalDate;

/**
 * This class will take care of initializing the main classes needed to run the application properly
 */
public class LauncherStudent {
    /**
     * This method will execute the whole application
     * @param args arguments passed in the terminal
     */
    public static void main(String[] args) {
        // JPA test to add a student : (not working because of the persistence.xml, bad configuration and many errors :()
        // first method :
//        StudentManager manager = new StudentManager();
//        manager.setup();
//        manager.create();
//        manager.exit();

        // second method :
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("mtudb");
//        EntityManager em = factory.createEntityManager();
//        em.getTransaction().begin();
//        Student student = new Student(new NameInfo("JPAFirst", "JPALast"), 123456, Date.valueOf(LocalDate.now()));
//        em.persist(student);
//        em.getTransaction().commit();
//        em.close();

        // Model
        University universityMTU = new University();
        DBConnection database = new DBConnection();

        // Controller
        SettingController controller = new SettingController(universityMTU);

        // View
        MainGUI mainView = new MainGUI();
        mainView.setController(controller);
        mainView.main(args);
    }
}
