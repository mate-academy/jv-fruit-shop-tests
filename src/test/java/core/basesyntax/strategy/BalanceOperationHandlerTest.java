package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void init() {
        handler = new BalanceOperationHandler();
        Storage.storage.clear();
    }

    @Test
    public void balanceOperationHandler_correctWork_ok() {
        String operation = "b";
        Fruit fruit = new Fruit("banana");
        int putQuantity = 100;
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, putQuantity);
        Transaction transaction = new Transaction(operation, fruit.getName(), putQuantity);
        handler.apply(transaction);
        Map<Fruit, Integer> actual = Storage.storage;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void balanceOperationHandler_correctQuantity_ok() {
        String operation = "b";
        Fruit fruit = new Fruit("banana");
        int putQuantity = 100;
        int expected = 100;
        Storage.storage.put(fruit,putQuantity);
        Transaction transaction = new Transaction(operation, fruit.getName(), putQuantity);
        int actual = handler.apply(transaction);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
