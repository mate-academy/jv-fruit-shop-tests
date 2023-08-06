package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String APPLE = "apple";
    private static final Integer QUANTITY_20 = 20;
    private static final Integer QUANTITY_30 = 30;
    private static final Integer QUANTITY_50 = 50;
    private final OperationHandler supplyOperationHandler = new SupplyOperationHandler();

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void processData_validData_ok() {
        Storage.getStorage().put(APPLE, QUANTITY_20);
        supplyOperationHandler.processData(APPLE, QUANTITY_30);
        Assertions.assertEquals(QUANTITY_50, Storage.getStorage().get(APPLE));
    }

    @Test
    void processData_fruitNotExistInStorage_ok() {
        supplyOperationHandler.processData(APPLE, QUANTITY_50);
        Assertions.assertEquals(QUANTITY_50, Storage.getStorage().get(APPLE));
    }
}
