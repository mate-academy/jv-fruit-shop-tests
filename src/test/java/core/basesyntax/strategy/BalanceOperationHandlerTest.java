package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceOperationHandler;
    private static Storage fruitsStorage;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        balanceOperationHandler = new BalanceOperationHandler();
        fruitsStorage = new Storage();
        transaction = new FruitTransaction();
    }

    @Test
    public void processOperation_Ok() {
        transaction.setFruit("banana");
        transaction.setQuantity(22);
        balanceOperationHandler.processOperation(transaction, fruitsStorage);
        int actual = fruitsStorage.getFruitsStorage().get("banana");
        Assert.assertEquals(22,actual);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_negativeQuantity_notOk() {
        transaction.setFruit("apple");
        transaction.setQuantity(-99);
        balanceOperationHandler.processOperation(transaction,fruitsStorage);
    }

    @After
    public void tearDown() {
        fruitsStorage.getFruitsStorage().clear();
    }
}
