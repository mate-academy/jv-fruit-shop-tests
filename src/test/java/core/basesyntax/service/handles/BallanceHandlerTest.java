package core.basesyntax.service.handles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BallanceHandlerTest {
    private static OperationHandler ballanceHandler;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 100;
    private static final FruitTransaction.Operation VALID_OPERATION
            = FruitTransaction.Operation.BALANCE;

    @BeforeAll
    static void beforeAll() {
        ballanceHandler = new BallanceHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void ballanceHandler_validQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, VALID_QUANTITY);
        ballanceHandler.handler(transaction);
        assertEquals(VALID_QUANTITY, Storage.storage.get(transaction.getFruit()));
    }
}
