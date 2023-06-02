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

class BalanceFruitHandlerImplTest {
    private static final int QUANTITY = 50;
    private static final String FRUIT = "apple";
    private static FruitHandler fruitHandler;

    @BeforeAll
    static void setUpBeforeAll() {
        fruitHandler = new BalanceFruitHandlerImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void doAction_fruitQuantityLessThanZero_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.BALANCE, FRUIT, -5);
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> fruitHandler.doAction(fruitTransaction));
        assertEquals("The balance of fruits in the store"
                + " cannot be less than zero", exception.getMessage());
    }

    @Test
    void doAction_validData_ok() {
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.BALANCE, FRUIT, QUANTITY);
        Storage.getFruitStorage().put(FRUIT, QUANTITY);
        assertDoesNotThrow(() -> fruitHandler.doAction(fruitTransaction));
    }
}
