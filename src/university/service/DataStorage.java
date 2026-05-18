package university.service;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class DataStorage {
    public void save(String path, Serializable data) {
        try (ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(path))) {
            output.writeObject(data);
        } catch (IOException e) {
            throw new IllegalStateException("Could not save data to " + path + ": " + e.getMessage(), e);
        }
    }

    public Object load(String path) {
        try (ObjectInputStream input = new ObjectInputStream(new FileInputStream(path))) {
            return input.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
    }
}
