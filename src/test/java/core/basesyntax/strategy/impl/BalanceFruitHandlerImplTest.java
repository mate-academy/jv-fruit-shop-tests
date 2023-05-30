package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceFruitHandlerImplTest {
    private static final int QUANTITY_OF_FRUIT_APPLE = 50;
    private static final String NAME_OF_FRUIT_APPLE = "apple";
    private static FruitTransaction fruitTransaction;
    private static FruitHandler fruitHandler;

    @BeforeAll
    static void setUpBeforeAll() {
        fruitHandler = new BalanceFruitHandlerImpl();
    }

    @BeforeEach
    void setUp() {
        fruitTransaction = new FruitTransaction(Operation.BALANCE, NAME_OF_FRUIT_APPLE,
                QUANTITY_OF_FRUIT_APPLE);
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void balanceFruitHandler_fruitQuantityLessThanZero_notOk() {
        fruitTransaction.setQuantity(-5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitHandler.doAction(fruitTransaction));
        Assertions.assertEquals("The balance of fruits in the store"
                + " cannot be less than zero", exception.getMessage());
    }

    @Test
    void balanceFruitHandler_validData_ok() {
        Storage.getFruitStorage().put(NAME_OF_FRUIT_APPLE, QUANTITY_OF_FRUIT_APPLE);
        Assertions.assertDoesNotThrow(() -> fruitHandler.doAction(fruitTransaction));
    }
}
