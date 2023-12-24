package core.basesyntax.handler;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseHandler;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static final int FRUIT_QUANTITY = 44;
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseHandler();
        fruitTransaction = new FruitTransaction(PURCHASE, "lemon", FRUIT_QUANTITY);
    }

    @Test
    void calculate_allOk() {
        int expected = -44;
        int actual = operationHandler.calculate(fruitTransaction);
        assertEquals(expected, actual);
    }
}
