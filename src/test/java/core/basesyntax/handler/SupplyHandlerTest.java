package core.basesyntax.handler;

import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceHandler;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static final int FRUIT_QUANTITY = 16;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BalanceHandler();
        fruitTransaction = new FruitTransaction(SUPPLY, "kiwi", FRUIT_QUANTITY);
    }

    @Test
    void calculate_allOk() {
        assertDoesNotThrow(() -> operationHandler.calculate(fruitTransaction));
        assertEquals(FRUIT_QUANTITY, operationHandler.calculate(fruitTransaction));
    }
}
