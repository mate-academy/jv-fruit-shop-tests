package core.basesyntax.strategy.operation;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractionOperationTest {
    private static OperationHandler operationHandler;
    private static Storage storage;
    private static FruitDao fruitDao;
    private static FruitTransaction banana;
    private static FruitTransaction apple;
    private static Integer expected;
    private static Integer expected1;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        fruitDao.put("banana", 40);
        fruitDao.put("apple", 30);
        operationHandler = new SubtractionOperation(fruitDao);
    }

    @Test
    public void subtractionOperationHandler_Ok() {
        banana = new FruitTransaction(BALANCE,"banana", 30);
        apple = new FruitTransaction(BALANCE, "apple", 20);
        operationHandler.handle(banana);
        expected = 10;
        Integer actual = fruitDao.get("banana");
        assertEquals("Test failed!Number of bananas should be 10 and there was: "
                + actual, expected, actual);
        operationHandler.handle(apple);
        expected1 = 10;
        Integer actual1 = fruitDao.get("apple");
        assertEquals("Test failed!Number of apples should be 10 and there was: "
                + actual, expected1, actual1);
    }

    @Test(expected = RuntimeException.class)
    public void subtractionOperationHandler_NotOk() {
        operationHandler.handle(null);
    }

    @AfterClass
    public static void afterClass() {
        storage.getFruitStorage().clear();
    }
}
