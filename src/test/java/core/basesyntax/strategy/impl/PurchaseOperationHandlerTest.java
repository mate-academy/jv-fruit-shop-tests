package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.OperationHandlerException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler operationHandler;
    private static TransactionDto transactionDto;
    private static Fruit fruit;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new PurchaseOperationHandler();
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void apply_purchaseEnoughFruits_ok() {
        Fruit fruit = new Fruit("banana");
        Storage.storage.put(fruit, 100);
        transactionDto = new TransactionDto("p", "banana", 20);
        operationHandler.apply(transactionDto);
        int expectedQuantity = 80;
        int actualQuantity = Storage.storage.get(fruit);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test(expected = OperationHandlerException.class)
    public void apply_purchaseNotEnoughFruits_notOk() {
        Fruit fruit = new Fruit("apple");
        Storage.storage.put(fruit, 10);
        transactionDto = new TransactionDto("p", "apple", 20);
        operationHandler.apply(transactionDto);
    }

    @Test(expected = OperationHandlerException.class)
    public void apply_purchaseNoFruit_notOk() {
        transactionDto = new TransactionDto("p", "banana", 50);
        operationHandler.apply(transactionDto);
    }
}
