package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Transaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        operationHandler = new ReturnOperationHandler();
    }

    @Before
    public void setUp() {
        Transaction transactionBanana = new Transaction("banana", 30);
        Transaction transactionApple = new Transaction("apple", 30);
        Storage.fruitsStorage.put(transactionBanana.getFruit(), transactionBanana.getQuantity());
        Storage.fruitsStorage.put(transactionApple.getFruit(), transactionApple.getQuantity());
    }

    @Test
    public void calculate_Ok() {
        operationHandler.calculate(new Transaction("apple", 20));
        Integer actual = Storage.fruitsStorage.get("apple");
        Integer expectResult = 50;
        assertEquals(expectResult, actual);
    }

    @Test(expected = RuntimeException.class)
    public void calculate_NegativeAmount_NotOk() {
        operationHandler.calculate(new Transaction("apple", -20));
    }

    @After
    public void tearDown() {
        Storage.fruitsStorage.clear();
    }
}
