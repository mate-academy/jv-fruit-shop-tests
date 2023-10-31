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

class SupplyOperationHandlerTest {
    private static final int ZERO_QUANTITY = 0;
    private static final int VALID_QUANTITY = 10;
    private static final String FRUIT = "banana";
    private static OperationHandler operationHandler;
    private static FruitTransaction fruitTransaction;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit(FRUIT);
        fruitTransaction.setQuantity(ZERO_QUANTITY);
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put(FRUIT, ZERO_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void qualityZeroOrLess_NotOk() {
        assertThrows(FruitShopException.class, () -> operationHandler.handle(fruitTransaction));
    }

    @Test
    void validQuality_Ok() {
        fruitTransaction.setQuantity(VALID_QUANTITY);
        operationHandler.handle(fruitTransaction);
        int actual = Storage.STORAGE.get(FRUIT);
        assertEquals(VALID_QUANTITY, actual);
    }
}
