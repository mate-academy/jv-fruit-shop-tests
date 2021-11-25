package core.basesyntax.service.activity;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class RemovingHandlerTest {
    private static FruitStorageDao fruitStorageDao;
    private static ActivityHandler activityHandler;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
        activityHandler = new RemovingHandler(fruitStorageDao);
    }

    @Before
    public void beforeEachMethod() {
        Storage.fruitsStorage.clear();
        Storage.fruitsStorage.addAll(List.of(new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("banana"),
                new Fruit("apple"),
                new Fruit("apple")));
    }

    @Test
    public void apply_invalidData_notOk() {
        thrown.expect(RuntimeException.class);
        thrown.expectMessage("Not enough banana to purchase: 10");
        activityHandler.apply("banana", 10);
    }

    @Test
    public void apply_validData_ok() {
        List<Fruit> expected = List.of(new Fruit("banana"),
                new Fruit("apple"));
        activityHandler.apply("banana", 4);
        activityHandler.apply("apple", 1);
        List<Fruit> actual = fruitStorageDao.getAll();
        Assert.assertEquals(expected, actual);
    }
}
