package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static final AddOperationHandler ADD_OPERATION_HANDLER
            = new AddOperationHandler();

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void addFNonExistFruit_Ok() {
        TransactionDto transaction
                = new TransactionDto("b", new Fruit("banana"), 15);
        ADD_OPERATION_HANDLER.apply(transaction);
        int expected = transaction.getQuantity();
        int actual = Storage.storage.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addExistFruit_Ok() {
        Storage.storage.put(new Fruit("banana"), 100);
        TransactionDto transaction
                = new TransactionDto("b", new Fruit("banana"), 15);
        ADD_OPERATION_HANDLER.apply(transaction);
        int expected = transaction.getQuantity() + 100;
        int actual = Storage.storage.get(transaction.getFruit());
        Assert.assertEquals(expected, actual);
    }
}
