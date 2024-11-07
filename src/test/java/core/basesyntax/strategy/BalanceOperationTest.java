package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.OperationType;
import core.basesyntax.model.ShopOperation;
import exception.OperationException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static final String APPLE = "apple";
    private static final int QUANTITY = 100;
    private static final int ANOTHER_QUANTITY = 50;
    private static final int NEGATIVE_QUANTITY = -10;
    private static final int MAP_LENGTH = 1;
    private static OperationHandler balanceOperation;
    private ShopOperation shopOperation;

    @BeforeAll
    static void beforeAll() {
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void setUp() {
        shopOperation = new ShopOperation(OperationType.BALANCE, APPLE, QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void handle_addFruitToEmptyStorage_Ok() {
        balanceOperation.handle(shopOperation);
        assertEquals(QUANTITY, Storage.fruitsStorage.get(APPLE));
        assertEquals(MAP_LENGTH, Storage.fruitsStorage.size());
    }

    @Test
    void handle_updateFruit_Ok() {
        Storage.fruitsStorage.put(APPLE, ANOTHER_QUANTITY);
        balanceOperation.handle(shopOperation);
        assertEquals(QUANTITY, Storage.fruitsStorage.get(APPLE));
        assertEquals(MAP_LENGTH, Storage.fruitsStorage.size());
    }

    @Test
    void handle_shopOperationWithNegativeQuantity_NotOk() {
        shopOperation = new ShopOperation(OperationType.BALANCE, APPLE, NEGATIVE_QUANTITY);
        assertThrows(OperationException.class, () -> balanceOperation.handle(shopOperation));
    }
}
