package core.basesyntax.strategy;

import core.basesyntax.dao.FruitMapDao;
import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationTest {
    private static FruitStorageDao fruitDao;
    private static OperationHandler operationAdd;
    private static final int QUANTITY_FRUIT = 10;
    private static final Fruit APPLE = new Fruit("apple");

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitMapDao();
        operationAdd = new AddOperation(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.map.clear();
        Storage.map.put(APPLE, QUANTITY_FRUIT);
    }

    @Test
    public void addOperation_add10_Ok(){
        int expected = 20;
        operationAdd.apply(APPLE, 10);
        int result = Storage.map.get(APPLE);
        assertEquals(expected, result);
    }
}