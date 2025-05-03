package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static ReturnOperationHandler returnOperationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeClass
    public static void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @After
    public void afterEach() {
        Storage.clear();
    }

    @Test(expected = RuntimeException.class)
    public void doActivity_fruitIsNull_notOk() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, null, 123);
        returnOperationHandler.doActivity(fruitTransaction);
    }

    @Test
    public void doActivity_defaultCase_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.RETURN, "banana", 20);
        Integer expectedQuantity = 20;
        returnOperationHandler.doActivity(fruitTransaction);
        Integer actualQuantity = Storage.get(fruitTransaction.getFruit());
        assertEquals("Must be correct data in storage ", expectedQuantity, actualQuantity);
    }
}
