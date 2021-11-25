package core.basesyntax.dao;

import core.basesyntax.db.Storage;
import java.util.Set;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DaoImplTest {
    private static final Dao dao = new DaoImpl();

    @Before
    public void setUp() {
        Storage.supplies.clear();
    }

    @Test
    public void get_test() {
        Storage.supplies.put("apple", 2000);
        Storage.supplies.put("orange", 0);
        Storage.supplies.put("banana", null);
        Integer expected = 2000;
        Integer actual = dao.get("apple");
        Assert.assertEquals(expected, actual);
        expected = 0;
        actual = dao.get("orange");
        Assert.assertEquals(expected, actual);
        actual = dao.get("banana");
        Assert.assertNull(actual);
        actual = dao.get("pineapple");
        Assert.assertNull(actual);
    }

    @Test
    public void put_test() {
        dao.put("apple", 2000);
        Assert.assertTrue(Storage.supplies.containsKey("apple"));
        Integer expected = 2000;
        Integer actual = Storage.supplies.get("apple");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void contains_test() {
        Storage.supplies.put("apple", 2000);
        Storage.supplies.put("orange", 1500);
        Storage.supplies.put("banana", 1000);
        Assert.assertTrue(dao.contains("apple"));
        Assert.assertTrue(dao.contains("orange"));
        Assert.assertTrue(dao.contains("banana"));
        Assert.assertFalse(dao.contains("pineapple"));
        Assert.assertFalse(dao.contains("beetroot"));
    }

    @Test
    public void remove_test() {
        Storage.supplies.put("apple", 2000);
        dao.remove("apple");
        Assert.assertFalse(Storage.supplies.containsKey("apple"));
        Assert.assertNull(dao.remove("apple"));
    }

    @Test
    public void getProductNames_test() {
        Storage.supplies.put("apple", 2000);
        Storage.supplies.put("orange", 1500);
        Storage.supplies.put("banana", 1000);
        Set<String> expected = Set.of(
                "apple",
                "banana",
                "orange");
        Set<String> actual = dao.getProductNames();
        Assert.assertEquals(expected, actual);
    }
}
