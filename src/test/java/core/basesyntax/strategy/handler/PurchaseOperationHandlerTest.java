package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.CantGetFruitException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final Integer QUANTITY_20 = 20;
    private static final Integer QUANTITY_30 = 30;
    private static final Integer QUANTITY_50 = 50;
    private final OperationHandler purchaseOperationHandler = new PurchaseOperationHandler();

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void processData_validData_ok() {
        Storage.getStorage().put(APPLE, QUANTITY_50);
        purchaseOperationHandler.processData(APPLE, QUANTITY_20);
        Assertions.assertEquals(QUANTITY_30, Storage.getStorage().get(APPLE));
    }

    @Test
    void processData_notEnoughInStorage_notOk() {
        Storage.getStorage().put(APPLE, QUANTITY_30);
        CantGetFruitException exception = null;
        try {
            purchaseOperationHandler.processData(APPLE, QUANTITY_50);
        } catch (CantGetFruitException e) {
            exception = e;
        }
        String expected = "Can't get 50 apple from storage, available only 30 apple";
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }
}
