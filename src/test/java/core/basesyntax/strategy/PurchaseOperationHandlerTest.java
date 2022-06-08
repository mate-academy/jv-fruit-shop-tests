package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseOperationHandler;
    private static Storage fruitsStorage;
    private static FruitTransaction transaction;

    @BeforeClass
    public static void beforeClass() throws Exception {
        purchaseOperationHandler = new PurchaseOperationHandler();
        fruitsStorage = new Storage();
        transaction = new FruitTransaction();
    }

    @Test
    public void processOperation_Ok() {
        fruitsStorage.getFruitsStorage().put("banana", 100);
        transaction.setFruit("banana");
        transaction.setQuantity(90);
        purchaseOperationHandler.processOperation(transaction, fruitsStorage);
        int actual = fruitsStorage.getFruitsStorage().get(transaction.getFruit());
        Assert.assertEquals(10,actual);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_biggerQuantity_notOk() {
        fruitsStorage.getFruitsStorage().put("apple", 1);
        transaction.setFruit("apple");
        transaction.setQuantity(10);
        purchaseOperationHandler.processOperation(transaction,fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_negativeQuantity_notOk() {
        fruitsStorage.getFruitsStorage().put("apple", 1);
        transaction.setFruit("apple");
        transaction.setQuantity(-99);
        purchaseOperationHandler.processOperation(transaction,fruitsStorage);
    }

    @Test(expected = RuntimeException.class)
    public void processOperation_fruitDoesNotExistInTheStorage_notOk() {
        transaction.setFruit("apple");
        transaction.setQuantity(9);
        purchaseOperationHandler.processOperation(transaction, fruitsStorage);
    }

    @After
    public void tearDown() {
        fruitsStorage.getFruitsStorage().clear();
    }
}
