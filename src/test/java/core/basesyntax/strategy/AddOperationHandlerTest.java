package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static final OperationHandler addOperationHandler
            = new AddOperationHandler();

    @After
    public void afterEachTest() {
        Storage.storage.clear();
    }

    @Test
    public void apply_addFruit_Ok() {
        TransactionDto transaction
                = new TransactionDto("s","apple", 100);
        addOperationHandler.apply(transaction);
        Fruit fruit = new Fruit(transaction.getFruitName());
        int expected = transaction.getQuantity();
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = AssertionError.class)
    public void apply_addFruitQuantity_notOk() {
        Storage.storage.put(new Fruit("apple"), 100);
        TransactionDto transactionDto
                = new TransactionDto("s","apple", 50);
        Fruit fruit = new Fruit(transactionDto.getFruitName());
        addOperationHandler.apply(transactionDto);
        int expected = transactionDto.getQuantity();
        int actual = Storage.storage.get(fruit);
        Assert.assertEquals(expected, actual);
    }
}
