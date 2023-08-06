package core.basesyntax.strategy.handler;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.CantPutFruitException;
import org.junit.Assert;
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
        Assert.assertEquals(QUANTITY_20, Storage.getStorage().get(APPLE));
    }

    @Test
    void processData_differentFruits_ok() {
        balanceOperationHandler.processData(APPLE, QUANTITY_50);
        balanceOperationHandler.processData(BANANA, QUANTITY_20);
        Assert.assertEquals(QUANTITY_50, Storage.getStorage().get(APPLE));
        Assert.assertEquals(QUANTITY_20, Storage.getStorage().get(BANANA));
    }

    @Test
    void processData_alreadyExist_notOk() {
        balanceOperationHandler.processData(APPLE, QUANTITY_20);
        CantPutFruitException exception = null;
        try {
            balanceOperationHandler.processData(APPLE, QUANTITY_50);
        } catch (CantPutFruitException e) {
            exception = e;
        }
        String expected = "apple already exist in Storage";
        Assert.assertEquals(expected, exception.getMessage());
    }
}
