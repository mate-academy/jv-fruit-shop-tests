package core.basesyntax.service.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final FruitTransaction.Operation VALID_OPERATION
            = FruitTransaction.Operation.BALANCE;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 100;
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void handle_ValidQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, VALID_QUANTITY);
        balanceHandler.handle(transaction);
        assertEquals(VALID_QUANTITY, Storage.storage.get(transaction.getFruit()));
    }
}
