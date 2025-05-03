package core.basesyntax.strategy;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler addOperationHandler;
    private static FruitDao fruitDao;
    private static TransactionDto supplyTransaction;
    private static TransactionDto returnTransaction;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        addOperationHandler = new AddOperationHandler(fruitDao);
        supplyTransaction = new TransactionDto("s", "orange", 10);
        returnTransaction = new TransactionDto("r", "apple", 15);
    }

    @Before
    public void beforeEachTest() {
        Fruit orange = new Fruit("orange");
        Storage.storage.put(orange, 10);
        Fruit apple = new Fruit("apple");
        Storage.storage.put(apple, 15);
    }

    @Test
    public void apply_supplyOperation_ok() {
        Fruit orange = new Fruit("orange");
        addOperationHandler.apply(supplyTransaction);
        int expectedQuantity = 20;
        int actualQuantity = Storage.storage.get(orange);
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void apply_returnOperation_ok() {
        Fruit apple = new Fruit("apple");
        addOperationHandler.apply(returnTransaction);
        int expectedQuantity = 30;
        int actualQuantity = Storage.storage.get(apple);
        Assert.assertEquals(expectedQuantity, actualQuantity);
    }

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }
}
