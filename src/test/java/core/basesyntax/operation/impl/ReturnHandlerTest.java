package core.basesyntax.operation.impl;

import static core.basesyntax.operation.Operation.RETURN;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitsTranslation;
import core.basesyntax.operation.Operation;
import core.basesyntax.operation.OperationHandler;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

public class ReturnHandlerTest {
    private static final String TEST_FRUIT = "apple";
    private static final int TEST_BALANCE_FIRST = 100;
    private static final int TEST_BALANCE_SECOND = 70;
    private static final Operation TEST_OPERATION = RETURN;
    private OperationHandler returnOperationHandler;

    @Before
    public void setUp() {
        returnOperationHandler = new ReturnHandler();
        Storage.fruitsMap.put(TEST_FRUIT,TEST_BALANCE_FIRST);
    }

    @After
    public void tearDown() {
        Storage.fruitsMap.clear();
    }

    @Test
    public void returnHandler_Work_Ok() {
        FruitsTranslation fruitTransaction = new FruitsTranslation(TEST_OPERATION,
                TEST_FRUIT, TEST_BALANCE_SECOND);
        returnOperationHandler.getOperationResult(fruitTransaction);
        int expected = TEST_BALANCE_FIRST + TEST_BALANCE_SECOND;
        int actual = Storage.fruitsMap.get(TEST_FRUIT);
        Assertions.assertEquals(expected, actual);
    }
}
