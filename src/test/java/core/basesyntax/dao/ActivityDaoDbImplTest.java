package core.basesyntax.dao;

import core.basesyntax.model.Fruit;
import core.basesyntax.storage.Storage;
import java.util.Map;
import java.util.Set;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ActivityDaoDbImplTest {
    private static ActivityDaoDb activityDaoDb;
    private static Fruit fruitFirst;
    private static Fruit fruitSecond;

    @BeforeClass
    public static void setUp() {
        activityDaoDb = new ActivityDaoDbImpl();
        fruitFirst = new Fruit("tangerine");
        fruitSecond = new Fruit("peach");
    }

    @Test
    public void test_put_ok() {
        int expected = 11;
        activityDaoDb.put(fruitFirst, expected);
        int actual = Storage.data.get(fruitFirst);
        Assert.assertEquals("Must be present Tangerine", expected, actual);
    }

    @Test
    public void test_getCount_empty_storage_ok() {
        int expected = 0;
        int actual = activityDaoDb.getCount(fruitSecond);
        Assert.assertEquals("Must be equals", expected, actual);
    }

    @Test
    public void test_getAll_ok() {
        activityDaoDb.put(fruitFirst, 5);
        activityDaoDb.put(fruitSecond, 10);
        Set<Map.Entry<Fruit, Integer>> allFruit = activityDaoDb.getAll();
        boolean flag;
        flag = Storage.data.size() == allFruit.size();
        for (Map.Entry<Fruit, Integer> fruit: allFruit) {
            if (Storage.data.get(fruit.getKey()) == null) {
                flag = false;
                break;
            }
            int countDao = Storage.data.get(fruit.getKey());
            int countStorage = fruit.getValue();
            if (countDao != countStorage) {
                flag = false;
                break;
            }
        }
        Assert.assertTrue("Must be equal", flag);
    }

    @After
    public void cleanUp() {
        Storage.data.clear();
    }
}
