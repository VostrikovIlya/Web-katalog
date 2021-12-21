package suai.webkatalog.model;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

public class Users {
    private static volatile Users instance;
    private final ConcurrentHashMap<String, String> users;

    public static Users getInstance() {
        if (instance == null) {
            synchronized (Catalog.class) {
                if (instance == null) {
                    instance = new Users();
                }
            }
        }
        return instance;
    }

    private Users() {
        this.users = new ConcurrentHashMap<>();
    }

    public boolean containUser(String name, String password) {
        if (users.containsKey(name)) {
            return users.get(name).equals(password);
        }
        return false;
    }

    public boolean containUser(String name) {
        return users.containsKey(name);
    }

    public void addUser(String name, String password) {
        users.put(name, password);
    }

    public void removeUser(String name) {
        users.remove(name);
    }

    public int getSize() {
        return users.size();
    }

    public ConcurrentHashMap<String, String> getUsers() {
        return users;
    }

    public void loadFile(String fileName) {
        try {
            Path p = Paths.get(fileName);
            Scanner scan = new Scanner(new String(Files.readAllBytes(p)));
            while (scan.hasNext()) {
                users.put(scan.next(), scan.next());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Map.Entry<String, String> e : users.entrySet()) {
                writer.println(e.getKey() + ' ' + e.getValue());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}

