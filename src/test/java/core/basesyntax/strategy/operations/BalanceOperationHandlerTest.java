package core.basesyntax.strategy.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.Operation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final Operation VALID_OPERATION = Operation.BALANCE;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.getStorage().clear();
    }

    @Test
    void checkHandleValidTransaction_Ok() {
        FruitTransaction transaction =
                new FruitTransaction(VALID_OPERATION, VALID_FRUIT, VALID_QUANTITY);
        balanceHandler.handle(transaction);
        assertEquals(VALID_QUANTITY, Storage.getStorage().get(transaction.getFruit()));
    }
}
