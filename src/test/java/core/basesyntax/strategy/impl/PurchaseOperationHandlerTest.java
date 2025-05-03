package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final String DEFAULT_FRUIT = "banana";
    private static final Integer DEFAULT_QUANTITY = 123;
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @After
    public void afterEach() {
        Storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doActivity_fruitIsNull_notOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, null, 20);
        purchaseOperationHandler.doActivity(fruitTransaction);
    }

    @Test
    public void doActivity_defaultCase_ok() {
        Storage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 20);
        Integer expectedQuantity = 103;
        purchaseOperationHandler.doActivity(fruitTransaction);
        Integer actualQuantity = Storage.get(fruitTransaction.getFruit());
        assertEquals("Must be correct data in storage", expectedQuantity, actualQuantity);
    }

    @Test (expected = RuntimeException.class)
    public void doActivity_amountMoreThanBalance_notOk() {
        Storage.put(DEFAULT_FRUIT, DEFAULT_QUANTITY);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana", 126);
        purchaseOperationHandler.doActivity(fruitTransaction);
    }

}
