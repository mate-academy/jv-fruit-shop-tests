package core.basesyntax.handlers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static final String FRUIT_NAME = "banana";
    private static final int CORRECT_QUANTITY = 50;
    private static final int ZERO_QUANTITY = 0;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final String EXCEPTION_MESSAGE_NEGATIVE_QUANTITY =
            "Quantity of fruit can`t be a negative";
    private static OperationHandler balanceHandler;

    @BeforeAll
    static void beforeAll() {
        balanceHandler = new BalanceHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.storage.clear();
    }

    @Test
    void handle_correctQuantity_ok() {
        balanceHandler.handle(FRUIT_NAME, CORRECT_QUANTITY);

        assertTrue(Storage.storage.containsKey(FRUIT_NAME));
        assertEquals(CORRECT_QUANTITY, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_zeroQuantity_ok() {
        balanceHandler.handle(FRUIT_NAME, ZERO_QUANTITY);

        assertTrue(Storage.storage.containsKey(FRUIT_NAME));
        assertEquals(ZERO_QUANTITY, Storage.storage.get(FRUIT_NAME));
    }

    @Test
    void handle_negativeQuantity_notOk() {
        var negativeQuantity = assertThrows(RuntimeException.class,
                () -> balanceHandler.handle(FRUIT_NAME, NEGATIVE_QUANTITY));

        assertEquals(EXCEPTION_MESSAGE_NEGATIVE_QUANTITY, negativeQuantity.getMessage());
    }
}
