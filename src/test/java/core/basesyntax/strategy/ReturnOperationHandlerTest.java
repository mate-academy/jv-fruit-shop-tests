package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler returnOperationHandler;
    private static Storage fruitsStorage;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        returnOperationHandler = new ReturnOperationHandler();
        fruitsStorage = new Storage();
        transaction = new FruitTransaction();
    }

    @Test
    public void processOperation_Ok() {
        fruitsStorage.getFruitsStorage().put("banana", 1);
        transaction.setFruit("banana");
        transaction.setQuantity(22);
        returnOperationHandler.processOperation(transaction, fruitsStorage);
        int actual = fruitsStorage.getFruitsStorage().get(transaction.getFruit());
        Assert.assertEquals(23,actual);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_negativeQuantity_notOk() {
        fruitsStorage.getFruitsStorage().put("apple", 33);
        transaction.setFruit("apple");
        transaction.setQuantity(-99);
        returnOperationHandler.processOperation(transaction,fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_fruitDoesNotExistInTheStorage_notOk() {
        transaction.setFruit("apple");
        transaction.setQuantity(9);
        returnOperationHandler.processOperation(transaction, fruitsStorage);
    }

    @After
    public void tearDown() {
        fruitsStorage.getFruitsStorage().clear();
    }
}
