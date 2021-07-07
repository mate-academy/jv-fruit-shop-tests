package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.Transaction;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.impl.AddOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static OperationHandler handler;

    @BeforeClass
    public static void beforeClass() {
        handler = new AddOperationHandler();
    }

    @Before
    public void init() {
        Storage.storage.clear();
    }

    @Test
    public void addOperationHandler_correctWorkOperation_s_ok() {
        String operation = "s";
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
    public void addOperationHandler_correctWorkOperation_r_ok() {
        String operation = "r";
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
    public void addOperationHandler_correctQuantity_s_ok() {
        Fruit fruit = new Fruit("banana");
        int putQuantity = 100;
        Storage.storage.put(fruit,putQuantity);
        int expected = Storage.storage.get(fruit);
        Storage.storage.clear();
        String operation = "s";
        Transaction transaction = new Transaction(operation, fruit.getName(), putQuantity);
        int actual = handler.apply(transaction);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void addOperationHandler_correctQuantity_r_ok() {
        Fruit fruit = new Fruit("banana");
        int putQuantity = 100;
        Storage.storage.put(fruit,putQuantity);
        int expected = Storage.storage.get(fruit);
        Storage.storage.clear();
        String operation = "r";
        Transaction transaction = new Transaction(operation, fruit.getName(), putQuantity);
        int actual = handler.apply(transaction);
        Assert.assertEquals(expected, actual);
    }
}
