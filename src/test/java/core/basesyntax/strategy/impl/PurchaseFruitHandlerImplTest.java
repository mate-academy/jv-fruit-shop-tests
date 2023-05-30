package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseFruitHandlerImplTest {
    private static final int QUANTITY_OF_FRUIT_APPLE = 50;
    private static final String NAME_OF_FRUIT_APPLE = "apple";
    private FruitTransaction fruitTransaction;
    private FruitHandler fruitHandler;

    @BeforeEach
    void setUp() {
        fruitHandler = new PurchaseFruitHandlerImpl();
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, NAME_OF_FRUIT_APPLE,
                QUANTITY_OF_FRUIT_APPLE);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void purchaseFruitHandler_fruitQuantityMoreThanStorageQuantity_notOk() {
        Storage.getFruitStorage().put(NAME_OF_FRUIT_APPLE, 40);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitHandler.doAction(fruitTransaction));
        Assertions.assertEquals("It is impossible to buy more fruit "
                + "than is available in the store", exception.getMessage());
    }

    @Test
    void purchaseFruitHandler_validData_ok() {
        Storage.getFruitStorage().put(NAME_OF_FRUIT_APPLE, 110);
        Assertions.assertDoesNotThrow(() -> fruitHandler.doAction(fruitTransaction));
        Assertions.assertEquals(60, Storage.getFruitStorage().get(fruitTransaction.getFruit()));
    }
}
