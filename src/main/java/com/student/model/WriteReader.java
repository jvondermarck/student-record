package com.student.model;

import java.io.*;
import java.util.*;

public class WriteReader extends ObjectOutputStream implements Serializable {

    @Serial
    private static final long serialVersionUID = 1103313081811883669L;
    public static ObjectInputStream objectinputstream;
    public static ObjectOutputStream oos;


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

    public static void addStudent(Student student)
    {
        List<Student> retrieve = new ArrayList<>();
        deserializeStudent(retrieve);

        try {
            if (oos == null){ //se é o primeiro objeto a guardar inicia a stream
                oos = new ObjectOutputStream(new FileOutputStream("src/main/java/com/student/test.ser"));
            }

            for(Student s1 : retrieve)
            {
                oos.writeObject(s1);
            }
            oos.writeObject(student);

            oos.flush();
            System.out.println("Done");

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

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/student/test.ser"))) {
            while (true) {
                namesList.add((Student) ois.readObject());
            }
        } catch (EOFException ex) {
            // fim da leitura aqui
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void addModule(Module module)
    {
        List<Module> retrieve = new ArrayList<>();
        deserializeModule(retrieve);

        try {
            if (oos == null){ //se é o primeiro objeto a guardar inicia a stream
                oos = new ObjectOutputStream(new FileOutputStream("src/main/java/com/student/module.ser"));
            }

            for(Module s1 : retrieve)
            {
                oos.writeObject(s1);
            }
            oos.writeObject(module);

            oos.flush();
            System.out.println("Done");

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

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/student/module.ser"))) {
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
            if (oos != null){ //se é o primeiro objeto a guardar inicia a stream
                oos.close(); //tenta fechar a stream de saída
                oos = null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("src/main/java/com/student/module.ser"))) {
            while (true) {
                Module module = (Module) ois.readObject();
                if(module.id.equals(id))
                    namesList.add(module);
            }
        } catch (EOFException ex) {
            // fim da leitura aqui
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
