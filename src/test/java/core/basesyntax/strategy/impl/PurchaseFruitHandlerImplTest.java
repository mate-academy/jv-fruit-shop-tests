package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class PurchaseFruitHandlerImplTest {
    private static final int QUANTITY_OF_FRUIT_APPLE = 50;
    private static final String NAME_OF_FRUIT_APPLE = "apple";
    private static FruitTransaction fruitTransaction;
    private static FruitHandler fruitHandler;

    @BeforeAll
    static void setUp() {
        fruitHandler = new PurchaseFruitHandlerImpl();
        fruitTransaction = new FruitTransaction(Operation.PURCHASE, NAME_OF_FRUIT_APPLE,
                QUANTITY_OF_FRUIT_APPLE);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void doAction_fruitQuantityMoreThanStorageQuantity_notOk() {
        Storage.getFruitStorage().put(NAME_OF_FRUIT_APPLE, 40);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitHandler.doAction(fruitTransaction));
        assertEquals("It is impossible to buy more fruit "
                + "than is available in the store", exception.getMessage());
    }

    @Test
    void doAction_validData_ok() {
        Storage.getFruitStorage().put(NAME_OF_FRUIT_APPLE, 110);
        assertDoesNotThrow(() -> fruitHandler.doAction(fruitTransaction));
        assertEquals(60, Storage.getFruitStorage().get(fruitTransaction.getFruit()));
    }
}
