package core.basesyntax.service.handles;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static OperationHandler returnHandler;
    private static FruitTransaction.Operation VALID_OPERATION = FruitTransaction.Operation.RETURN;
    private static String VALID_FRUIT = "apple";
    private static int VALID_QUANTITY = 20;
    private static int VALID_RETURN_QUANTITY = 10;

    @BeforeAll
    static void beforeAll() {
        returnHandler = new ReturnHandler();
    }

    @AfterEach
    void afterEach() {
        Storage.storage.clear();
    }

    @Test
    void returnHandler_validQuantity_ok() {
        Storage.storage.put(VALID_FRUIT, VALID_QUANTITY);
        FruitTransaction transaction = new FruitTransaction(VALID_OPERATION,
                VALID_FRUIT, VALID_RETURN_QUANTITY);
        returnHandler.handler(transaction);
        int expected = VALID_QUANTITY + VALID_RETURN_QUANTITY;
        assertEquals(expected, Storage.storage.get(transaction.getFruit()));
    }
}
