package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final OperationHandler purchaseOperationHandler
            = new PurchaseOperationHandler();

    @After
    public void beforeEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void apply_purchase_Ok() {
        Storage.storage.put(new Fruit("banana"), 10);
        TransactionDto transactionDto
                = new TransactionDto("p","banana", 5);
        purchaseOperationHandler.apply(transactionDto);
        int expected = 5;
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseMoreThanAvailable_Ok() {
        Storage.storage.put(new Fruit("apple"), 150);
        TransactionDto transactionDto
                = new TransactionDto("p","apple", 4000);
        purchaseOperationHandler.apply(transactionDto);
    }

    @Test
    public void apply_purchaseMoreThanAvailable_notOk() {
        Storage.storage.put(new Fruit("apple"), 150);
        TransactionDto transactionDto
                = new TransactionDto("p","apple", 100);
        purchaseOperationHandler.apply(transactionDto);
    }
}
