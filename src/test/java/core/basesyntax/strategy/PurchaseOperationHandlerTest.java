package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private OperationHandler handler;

    @Before
    public void init() {
        handler = new PurchaseOperationHandler();
        Storage.storage.clear();
    }

    @Test
    public void purchaseOperationHandler_correctWork_ok() {
        String operation = "p";
        Fruit fruit = new Fruit("banana");
        int putQuantity = 100;
        int removeQuantity = 50;
        int resultQuantity = putQuantity - removeQuantity;
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(fruit, resultQuantity);
        Storage.storage.put(fruit,putQuantity);
        Transaction transaction = new Transaction(operation, fruit.getName(), removeQuantity);
        handler.apply(transaction);
        Map<Fruit, Integer> actual = Storage.storage;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseOperationHandler_correctQuantity_ok() {
        String operation = "p";
        Fruit fruit = new Fruit("banana");
        int putQuantity = 100;
        int removeQuantity = 50;
        int expected = 50;
        Storage.storage.put(fruit,putQuantity);
        Transaction transaction = new Transaction(operation, fruit.getName(), removeQuantity);
        int actual = handler.apply(transaction);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void purchaseOperationHandler_incorrectQuantity_notOk() {
        String operation = "p";
        Fruit fruit = new Fruit("banana");
        int putQuantity = 0;
        int removeQuantity = 100;
        Storage.storage.put(fruit,putQuantity);
        Transaction transaction = new Transaction(operation, fruit.getName(), removeQuantity);
        handler.apply(transaction);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
