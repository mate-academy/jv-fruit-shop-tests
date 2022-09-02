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
    public static void beforeClass() {
        activityDaoDb = new ActivityDaoDbImpl();
        fruitFirst = new Fruit("tangerine");
        fruitSecond = new Fruit("peach");
    }

    @Test
    public void test_put_ok() {
        activityDaoDb.put(fruitFirst, 11);
        int actualCount = Storage.data.get(fruitFirst);
        Assert.assertEquals("Must be present Tangerine", 11, actualCount);
    }

    @Test
    public void test_getCount_ok() {
        activityDaoDb.put(fruitFirst, 12);
        int actualCount = Storage.data.get(fruitFirst);
        Assert.assertEquals("Must be 12", 12, actualCount);
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
    public void afterClass() {
        Storage.data.clear();
    }
}
