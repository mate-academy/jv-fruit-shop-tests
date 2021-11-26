package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitMapDao;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemoveOperationTest {
    private static FruitStorageDao fruitDao;
    private static OperationHandler operationRemove;
    private static final int QUANTITY_FRUIT = 100;
    private static final Fruit APPLE = new Fruit("apple");

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitMapDao();
        operationRemove = new RemoveOperation(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.map.clear();
        Storage.map.put(APPLE, QUANTITY_FRUIT);
    }

    @Test
    public void remove_somePositiveQuantity_Ok() {
        int expected = 90;
        operationRemove.apply(APPLE, 10);
        int result = Storage.map.get(APPLE);
        assertEquals(expected, result);
    }

    @Test(expected = RuntimeException.class)
    public void remove_negativeResultQuantity_notOk() {
        operationRemove.apply(APPLE, 1000);
    }
}
