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

class PurchaseOperationTest {
    private static final String APPLE = "apple";
    private static final String NOT_EXISTED_FRUIT = "banana";
    private static final int PURCHASE_QUANTITY = 75;
    private static final int EXPECTED_QUANTITY = 25;
    private static final int QUANTITY = 100;
    private static final int BIGGER_QUANTITY = 150;
    private static final int NEGATIVE_QUANTITY = -10;
    private static OperationHandler purchaseOperation;
    private ShopOperation shopOperation;

    @BeforeAll
    static void beforeAll() {
        purchaseOperation = new PurchaseOperation();
    }

    @BeforeEach
    void setUp() {
        Storage.fruitsStorage.put(APPLE, QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.fruitsStorage.clear();
    }

    @Test
    void handle_simplePurchase_Ok() {
        shopOperation = new ShopOperation(
                OperationType.PURCHASE, APPLE, PURCHASE_QUANTITY);
        purchaseOperation.handle(shopOperation);
        assertEquals(EXPECTED_QUANTITY, Storage.fruitsStorage.get(APPLE));
    }

    @Test
    void handle_purchaseNotExistedFruit_NotOk() {
        shopOperation = new ShopOperation(
                OperationType.PURCHASE, NOT_EXISTED_FRUIT, PURCHASE_QUANTITY);
        OperationException exception = assertThrows(OperationException.class,
                () -> purchaseOperation.handle(shopOperation));
        String expected = "Operation is not correct, fruit doesn't exist: "
                + shopOperation.getFruit();
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_purchaseWithBiggerQuantity_NotOk() {
        shopOperation = new ShopOperation(
                OperationType.PURCHASE, APPLE, BIGGER_QUANTITY);
        OperationException exception = assertThrows(OperationException.class,
                () -> purchaseOperation.handle(shopOperation));
        String expected = "Operation is impossible, not enough quantity of: "
                + shopOperation.getFruit();
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_purchaseWithNegativeNumber_NotOk() {
        shopOperation = new ShopOperation(OperationType.PURCHASE, APPLE, NEGATIVE_QUANTITY);
        OperationException exception = assertThrows(OperationException.class,
                () -> purchaseOperation.handle(shopOperation));
        String expected = "Operation is not correct, quantity can't be negative. Quantity: "
                + shopOperation.getQuantity();
        assertEquals(expected, exception.getMessage());
    }
}
