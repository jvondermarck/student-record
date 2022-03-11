package com.student.utilities;

import com.student.model.Module;
import com.student.model.Student;

import java.io.*;
import java.util.*;

public class ObjectParser extends ObjectOutputStream implements Serializable {

    @Serial
    private static final long serialVersionUID = 1103313081811883669L;
    public static ObjectInputStream objectinputstream;
    public static ObjectOutputStream oos;


    public ObjectParser() throws IOException {
        super();
    }

    @Override
    protected void writeStreamHeader() throws IOException {
        // do not write a header, but reset:
        // this line added after another question
        // showed a problem with the original
        reset();
    }

    public static void addStudent(List<Student> studentList)
    {
        try {
            oos = new ObjectOutputStream(new FileOutputStream("src/main/java/com/student/database_student.ser"));
            for(Student s1 : studentList)
            {
                oos.writeObject(s1);
            }
            oos.flush();

            oos = new ObjectOutputStream(new FileOutputStream("src/main/java/com/student/database_module.ser"));
            for(Student student : studentList) {
                if(student.getModule() != null) {
                    for(Module module : student.getModule()) {
                            oos.writeObject(module);
                    }
                }
            }
            oos.flush();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deserializeStudent(List<Student> namesList) {
        try {
            if (oos != null){ //se é o primeiro objeto a guardar inicia a stream
                oos.close(); //tenta fechar a stream de saída
                oos = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/student/database_student.ser"))) {
            while (true) {
                namesList.add((Student) ois.readObject());
            }
        } catch (EOFException ex) {
            // fim da leitura aqui
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deserializeModule(List<Module> namesList) {
        try {
            if (oos != null){ //se é o primeiro objeto a guardar inicia a stream
                oos.close(); //tenta fechar a stream de saída
                oos = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/student/database_module.ser"))) {
            while (true) {
                namesList.add((Module) ois.readObject());
            }
        } catch (EOFException ex) {
            // fim da leitura aqui
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deserializeModuleStudent(List<Module> namesList, String id) {
        try {
            if (oos != null){
                oos.close();
                oos = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/student/database_module.ser"))) {
            while (true) {
                Module module = (Module) ois.readObject();
                if(module.getId().equals(id))
                    namesList.add(module);
            }
        } catch (EOFException ex) {
            // ignore it
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
