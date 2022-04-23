package ie.mtu.application.student.launcher;

import ie.mtu.application.student.controller.SettingController;
import ie.mtu.application.student.model.DBConnection;
import ie.mtu.application.student.model.NameInfo;
import ie.mtu.application.student.model.Student;
import ie.mtu.application.student.model.University;
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

//        Student student = new Student(new NameInfo("JPAFirst", "JPALast"), 123456, Date.valueOf(LocalDate.now()));
//        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("mtudb");
//        EntityManager entityManager = entityManagerFactory.createEntityManager();
//        entityManager.getTransaction().begin();
//        entityManager.persist(student);
//        entityManager.getTransaction().commit();
//        entityManager.close();
//        entityManagerFactory.close();

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
