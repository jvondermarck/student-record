package com.student.model;

import java.io.*;
import java.util.*;

public class WriteReader extends ObjectOutputStream implements Serializable {

    @Serial
    private static final long serialVersionUID = 1103313081811883669L;


    public WriteReader() throws IOException {
        super();
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        // this line added after another question
        // showed a problem with the original
        reset();
    }

    public void addStudent(Student student)
    {
        try {
            FileOutputStream f;
            File file = new File("c://hello.txt");
            if (file.length() == 0)
                f = new FileOutputStream("src/main/java/com/student/test.ser", false);
            else
                f = new FileOutputStream("src/main/java/com/student/test.ser", true);

            BufferedOutputStream  bis = new BufferedOutputStream(f, 4096 );
            ObjectOutputStream o = new ObjectOutputStream(bis);

            // Write objects to file
            o.writeObject(student);
            o.flush();

            o.close();
            f.close();

            //FileInputStream fi = new FileInputStream("src/main/java/com/student/test.txt");
            //ObjectInputStream oi = new ObjectInputStream(fi);
/*
            ArrayList<Student> studentList = new ArrayList<>();

            try {
                //FileInputStream fis = new FileInputStream("src/main/java/com/student/test.txt");
                //ObjectInputStream os = new ObjectInputStream(fis);

                try (FileInputStream fis = new FileInputStream("src/main/java/com/student/test.txt")) {
                    ObjectInputStream os = new ObjectInputStream(fis);
                    Student student1 = (Student) os.readObject();
                    studentList.add(student1);
                    Student student2 = (Student) os.readObject();
                    studentList.add(student2);
                    os.close();
                    fis.close();
                } catch (EOFException e) {
                    return;
                }

            } catch (IOException | ClassNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            for(Student s : studentList)
                System.out.println("hello " + s.getFullname());


 */

            // Read objects
            //Student student1 = (Student) oi.readObject();
            //Person pr2 = (Person) oi.readObject();

            //System.out.println("lol " +  student1.getFullname());
            //System.out.println(pr2.toString());

            //oi.close();
            //fi.close();

        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserialize() {
        // personmutable.ser file must exist in the current directory
        ArrayList<Student> namesList;
        File fileObject = new File("src/main/java/com/student/test.ser");

        try  {

            FileInputStream fis = new FileInputStream(fileObject);
            ObjectInputStream ois = new ObjectInputStream(fis);

            Object obj = ois.readObject();
            Iterable<?> ar = (Iterable<?>) obj;

            namesList = new ArrayList<>();
            for (Object x : ar) {
                namesList.add((Student) x);
            }

            for(Student s : namesList)
                System.out.println("hello " + s.getFullname());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
