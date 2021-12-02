import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import suai.webkatalog.model.Users;

import java.lang.reflect.Field;

public class TestUsers {
    private Users users = Users.getInstance();

    @Before
    public void setUsers() throws NoSuchFieldException, IllegalAccessException {
        Field instance = Users.class.getDeclaredField("instance");
        instance.setAccessible(true);
        instance.set(null, null);
        users = Users.getInstance();
        users.addUser("Ilya", "1234");
        users.addUser("Liza", "888");
        users.addUser("Kate", "999");
    }

    @Test
    public void testGetSize() {
        Assertions.assertEquals(3, users.getSize());
    }

    @Test
    public void testLoadFile() {
        users.loadFile("src/test/java/data/users.txt");
        Assertions.assertEquals(3, users.getSize());
        Assertions.assertTrue(users.containUser("Ilya","1234"));
        Assertions.assertFalse(users.containUser("Bob"));
    }

    @Test
    public void testContainUser() {
        Assertions.assertTrue(users.containUser("Ilya"));
        Assertions.assertTrue(users.containUser("Ilya", "1234"));
        Assertions.assertFalse(users.containUser("LLya"));
        Assertions.assertFalse(users.containUser("ILya", "0000"));
    }

    @Test
    public void testAddUser() {
        users.addUser("Denis", "9999");
        Assertions.assertTrue(users.containUser("Denis"));
        Assertions.assertTrue(users.containUser("Denis", "9999"));
    }
    @Test
    public void testRemoveUser(){
        users.removeUser("Ilya");
        Assertions.assertFalse(users.containUser("Ilya"));
    }
    @Test
    public void testSaveFile(){
        String fileName = "src/test/java/data/users.txt";
        users.saveFile(fileName);
        users.getUsers().clear();
        users.loadFile(fileName);
        Assertions.assertEquals(3,users.getSize());
        Assertions.assertTrue(users.containUser("Liza", "888"));
        Assertions.assertTrue(users.containUser("Ilya", "1234"));
        Assertions.assertTrue(users.containUser("Kate", "999"));
    }

}
