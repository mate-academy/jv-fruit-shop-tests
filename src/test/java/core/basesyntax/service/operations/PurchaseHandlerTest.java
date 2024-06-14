package core.basesyntax.service.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.CantWorkWithThisFileException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final String APPLE = "apple";
    private static final int NUMBER = 10;
    private OperationHandler operationHandler;

    @BeforeEach
    void setUp() {
        operationHandler = new PurchaseHandler();
    }

    @Test
    void getOperation_GetIntFromFruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                Operation.BALANCE, APPLE, NUMBER);
        int expectedResult = (-1) * NUMBER;
        assertEquals(expectedResult, operationHandler.getOperation(fruitTransaction),
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
