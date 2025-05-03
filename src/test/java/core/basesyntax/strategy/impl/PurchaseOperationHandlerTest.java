package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.FruitShopException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseOperationHandlerTest {
    private static final int ZERO_QUANTITY = 0;
    private static final int BIGGER_THAN_EXPECTED_QUANTITY = 30;
    private static final int VALID_QUANTITY = 10;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put(FRUIT, VALID_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void quantityZeroOrLess_NotOk() {
        fruitTransaction.setQuantity(ZERO_QUANTITY);
        assertThrows(FruitShopException.class, () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void tooMuchQuantity_NotOk() {
        fruitTransaction.setQuantity(BIGGER_THAN_EXPECTED_QUANTITY);
        assertThrows(FruitShopException.class, () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void correctQuantity_Ok() {
        fruitTransaction.setQuantity(VALID_QUANTITY);
        operationHandler.handle(fruitTransaction);
        int actual = Storage.STORAGE.get(FRUIT);
        assertEquals(ZERO_QUANTITY, actual);
    }
}
