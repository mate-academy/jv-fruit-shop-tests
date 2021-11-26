package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class SubtractOperationHandlerTest {
    private static final OperationHandler SubtractOperationHandler
            = new SubtractOperationHandler();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_subtract_Ok() {
        Storage.storage.put(new Fruit("banana"), 100);
        TransactionDto transaction 
                = new TransactionDto("p", new Fruit("banana"), 50);
        SubtractOperationHandler.apply(transaction);
        int expected = 50;
        int actual = Storage.storage.get(new Fruit("banana"));
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_subtractLargeQuantity_notOk() {
        Storage.storage.put(new Fruit("banana"), 10);
        TransactionDto transaction
                = new TransactionDto("p", new Fruit("banana"), Integer.MAX_VALUE);
        SubtractOperationHandler.apply(transaction);
    }

    @Test(expected = RuntimeException.class)
    public void apply_subtractWhenNoFruit_notOk() {
        TransactionDto transaction
                = new TransactionDto("p", new Fruit("banana"), 1);
        SubtractOperationHandler.apply(transaction);
    }
}
