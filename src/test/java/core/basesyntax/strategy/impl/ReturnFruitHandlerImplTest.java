package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.enumeration.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.FruitHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnFruitHandlerImplTest {
    private static final int QUANTITY = 50;
    private static final String FRUIT = "apple";
    private static FruitHandler fruitHandler;

    @BeforeAll
    static void setUp() {
        fruitHandler = new ReturnFruitHandlerImpl();
    }

    @AfterEach
    void tearDown() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void doAction_validData_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(Operation.RETURN, FRUIT,
                QUANTITY);
        Storage.getFruitStorage().put(FRUIT, 110);
        assertDoesNotThrow(() -> fruitHandler.doAction(fruitTransaction));
        assertEquals(160, Storage.getFruitStorage().get(fruitTransaction.getFruit()));
    }
}
