package university.storage;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public final class DataStorage {
    private DataStorage() {
    }

    public static void save(String path, Serializable data) throws IOException {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(data);
        }
    }

    public static Object load(String path) throws IOException, ClassNotFoundException {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
            return input.readObject();
        }
    }
}
