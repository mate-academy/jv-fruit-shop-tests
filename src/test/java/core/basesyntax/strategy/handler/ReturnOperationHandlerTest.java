package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.CantReturnFruitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final Integer QUANTITY_0 = 0;
    private static final Integer QUANTITY_20 = 20;
    private static final Integer QUANTITY_30 = 30;
    private static final Integer QUANTITY_50 = 50;
    private final OperationHandler returnOperationHandler = new ReturnOperationHandler();

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void processData_validData_ok() {
        Storage.getStorage().put(APPLE, QUANTITY_20);
        returnOperationHandler.processData(APPLE, QUANTITY_30);
        Assertions.assertEquals(QUANTITY_50, Storage.getStorage().get(APPLE));
    }

    @Test
    void processData_noBalanceInStorage_ok() {
        Storage.getStorage().put(APPLE,QUANTITY_0);
        returnOperationHandler.processData(APPLE,QUANTITY_20);
        Assertions.assertEquals(QUANTITY_20,Storage.getStorage().get(APPLE));
    }

    @Test
    void processData_fruitNotExistInStorage_notOk() {
        CantReturnFruitException exception = null;
        try {
            returnOperationHandler.processData(APPLE, QUANTITY_50);
        } catch (CantReturnFruitException e) {
            exception = e;
        }
        String expected = "There isn't apple in Storage, can't return";
        Assertions.assertEquals(expected, exception.getMessage());
    }
}
