package core.basesyntax.operation.impl;

import static core.basesyntax.operation.Operation.BALANCE;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitsTranslation;
import core.basesyntax.operation.Operation;
import core.basesyntax.operation.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class BalanceHandlerTest {
    private static final Operation TEST_OPERATION = BALANCE;
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE = 50;
    private FruitsTranslation fruitTransaction;
    private OperationHandler balanceOperationHandler;

    @Before
    public void setUp() {
        balanceOperationHandler = new BalanceHandler();
        fruitTransaction = new FruitsTranslation(TEST_OPERATION, TEST_FRUIT, TEST_BALANCE);
    }

    @After
    public void tearDown() {
        Storage.fruitsMap.clear();
    }

    @Test
    public void balanceHandler_Work_Ok() {
        balanceOperationHandler.getOperationResult(fruitTransaction);
        int actual = Storage.fruitsMap.get(TEST_FRUIT);
        Assertions.assertEquals(TEST_BALANCE, actual);
    }
}
