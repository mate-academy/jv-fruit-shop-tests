package core.basesyntax.strategy.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.CantPutFruitException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final Integer QUANTITY_20 = 20;
    private static final Integer QUANTITY_50 = 50;
    private final OperationHandler balanceOperationHandler = new BalanceOperationHandler();

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void processData_validData_ok() {
        balanceOperationHandler.processData(APPLE, QUANTITY_20);

        assertEquals(QUANTITY_20, Storage.getStorage().get(APPLE));
    }

    @Test
    void processData_differentFruits_ok() {
        balanceOperationHandler.processData(APPLE, QUANTITY_50);
        balanceOperationHandler.processData(BANANA, QUANTITY_20);

        assertEquals(QUANTITY_50, Storage.getStorage().get(APPLE));
        assertEquals(QUANTITY_20, Storage.getStorage().get(BANANA));
    }

    @Test
    void processData_alreadyExist_notOk() {
        balanceOperationHandler.processData(APPLE, QUANTITY_20);
        CantPutFruitException exception = assertThrows(CantPutFruitException.class,
                () -> balanceOperationHandler.processData(APPLE, QUANTITY_50));

        String expected = "apple already exist in Storage";

        assertEquals(expected, exception.getMessage());
    }
}
