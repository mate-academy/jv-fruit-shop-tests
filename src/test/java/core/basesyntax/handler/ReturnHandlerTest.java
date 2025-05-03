package core.basesyntax.handler;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final String ORANGE = "orange";
    private static final int FRUIT_QUANTITY = 23;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceHandler();
        fruitTransaction = new FruitTransaction(RETURN, ORANGE, FRUIT_QUANTITY);
    }

    @Test
    void calculate_allOk() {
        assertDoesNotThrow(() -> operationHandler.calculate(fruitTransaction));
        assertEquals(FRUIT_QUANTITY, operationHandler.calculate(fruitTransaction));
    }
}
