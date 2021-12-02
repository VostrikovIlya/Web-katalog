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

public class Admin {
    private static volatile Admin instance;
    private final ConcurrentHashMap<String, String> admins;

    public Admin() {
        this.admins = new ConcurrentHashMap<>();
    }

    public static Admin getInstance() {
        if (instance == null) {
            synchronized (Catalog.class) {
                if (instance == null) {
                    instance = new Admin();
                }
            }
        }
        return instance;
    }

    public boolean containUser(String name, String password) {
        if (admins.containsKey(name)) {
            return admins.get(name).equals(password);
        }
        return false;
    }

    public boolean containUser(String name) {
        return admins.containsKey(name);
    }

    public void addUser(String name, String password) {
        admins.put(name, password);
    }

    public void removeUser(String name) {
        admins.remove(name);
    }

    public void loadFile(String fileName) {
        admins.clear();
        try {
            Path p = Paths.get(fileName);
            Scanner scan = new Scanner(new String(Files.readAllBytes(p)));
            while (scan.hasNext()) {
                admins.put(scan.next(), scan.next());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public void saveFile(String fileName) {
        try (PrintWriter writer = new PrintWriter(fileName)) {
            for (Map.Entry<String, String> e : admins.entrySet()) {
                writer.println(e.getKey() + ' ' + e.getValue());
            }
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();
        }
    }
}
