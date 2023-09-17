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
    private static final int TOO_MUCH_QUANTITY = 30;
    private static final int VALID_QUANTITY = 10;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;
    private static FruitTransaction transaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new PurchaseOperationHandler();
        transaction = new FruitTransaction();
        transaction.setOperation(FruitTransaction.Operation.PURCHASE);
        transaction.setFruit(FRUIT);
        transaction.setQuantity(ZERO_QUANTITY);
    }

    @BeforeEach
    void setUp() {
        Storage.FRUITS.put(FRUIT, VALID_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }

    @Test
    void quantityLessZero() {
        assertThrows(FruitShopException.class, () -> operationHandler.handle(transaction));
    }

    @Test
    void notEnoughQuantity_notOk() {
        transaction.setQuantity(TOO_MUCH_QUANTITY);
        assertThrows(FruitShopException.class, () -> operationHandler.handle(transaction));
    }

    @Test
    void correctTransaction_Ok() {
        transaction.setQuantity(VALID_QUANTITY);
        operationHandler.handle(transaction);
        int actualQuantity = Storage.FRUITS.get(FRUIT);
        assertEquals(ZERO_QUANTITY, actualQuantity);
    }
}
