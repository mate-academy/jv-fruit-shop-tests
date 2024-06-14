package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CantWorkWithThisFileException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final String APPLE = "apple";
    private static final int NUMBER = 10;
    private static final int WRONG_NUMBER = -10;
    private static OperationHandler operationHandler;

    @BeforeAll
    static void setUp() {
        operationHandler = new SupplyHandler();
    }

    @Test
    void getOperation_GetIntFromFruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.BALANCE, APPLE, NUMBER);
        int expectedResult = NUMBER;
        assertEquals(expectedResult, operationHandler.getOperation(fruitTransaction),
                "Operation returned wrong result");
    }

    @Test
    void getOperation_QuantityUnderZero_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.BALANCE, APPLE, WRONG_NUMBER);
        assertThrows(CantWorkWithThisFileException.class,
                () -> operationHandler.getOperation(fruitTransaction),
                "Operation returned wrong result");
    }

    @Test
    void getOperation_NullFruitTransaction_NotOk() {
        FruitTransaction fruitTransaction = null;
        assertThrows(CantWorkWithThisFileException.class,
                () -> operationHandler.getOperation(fruitTransaction),
                "Operation returned wrong result");
    }
}
