package core.basesyntax.service.activity;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddingHandlerTest {
    private static FruitStorageDao fruitStorageDao;
    private static ActivityHandler activityHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
        activityHandler = new AddingHandler(fruitStorageDao);
    }

    @Before
    public void beforeEachMethod() {
        Storage.fruitsStorage.clear();
    }

    @Test
    public void apply_validData_ok() {
        List<Fruit> expected = List.of(new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("apple"),
                new Fruit("apple"));
        activityHandler.apply("banana", 5);
        activityHandler.apply("apple", 2);
        List<Fruit> actual = fruitStorageDao.getAll();
        Assert.assertEquals(expected, actual);
    }
}
