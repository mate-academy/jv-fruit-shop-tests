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

class SupplyOperationTest {
    private static final String APPLE = "apple";
    private static final String NOT_EXISTED_FRUIT = "banana";
    private static final int QUANTITY = 100;
    private static final int RETURNED_QUANTITY = 50;
    private static final int EXPECTED_QUANTITY = 150;
    private static final int NEGATIVE_QUANTITY = -10;
    private static OperationHandler supplyOperation;
    private ShopOperation shopOperation;

    @BeforeAll
    static void beforeAll() {
        supplyOperation = new SupplyOperation();
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
    void handle_simpleSupplyOperation_Ok() {
        shopOperation = new ShopOperation(OperationType.RETURN, APPLE, RETURNED_QUANTITY);
        supplyOperation.handle(shopOperation);
        assertEquals(EXPECTED_QUANTITY, Storage.fruitsStorage.get(APPLE));
    }

    @Test
    void handle_supplyNotExistedFruit_NotOk() {
        shopOperation = new ShopOperation(
                OperationType.RETURN, NOT_EXISTED_FRUIT, RETURNED_QUANTITY);
        OperationException exception = assertThrows(OperationException.class,
                () -> supplyOperation.handle(shopOperation));
        String expected = "Operation is not correct, fruit doesn't exist: "
                + shopOperation.getFruit();
        assertEquals(expected, exception.getMessage());
    }

    @Test
    void handle_supplyWithNegativeNumber_NotOk() {
        shopOperation = new ShopOperation(
                OperationType.RETURN, APPLE, NEGATIVE_QUANTITY);
        OperationException exception = assertThrows(OperationException.class,
                () -> supplyOperation.handle(shopOperation));
        String expected = "Operation is not correct, quantity can't be negative. Quantity: "
                + shopOperation.getQuantity();
        assertEquals(expected, exception.getMessage());
    }
}
