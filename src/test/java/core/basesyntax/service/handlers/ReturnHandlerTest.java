package core.basesyntax.service.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static final FruitTransaction.Operation VALID_OPERATION
            = FruitTransaction.Operation.RETURN;
    private static final String VALID_FRUIT = "apple";
    private static final int VALID_QUANTITY = 20;
    private static final int RETURN_QUANTITY = 10;
    private static OperationHandler returnHandler;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
    }

    @AfterEach
     void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void handle_ValidQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, RETURN_QUANTITY);
        returnHandler.handle(transaction);
        int expected = VALID_QUANTITY + RETURN_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }
}
