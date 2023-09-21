package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.BuyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BuyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new BuyOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.STORAGE.put("banana", 20);
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void updateNumberOfFruit_validQuantity_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(19);
        fruitTransaction.setFruit("banana");
        operationHandler.updateNumberOfFruit(fruitTransaction);
        assertEquals(1, Storage.STORAGE.get("banana"));
    }

    @Test
    void updateNumberOfFruit_notValidQuantity_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(21);
        fruitTransaction.setFruit("banana");
        assertThrows(RuntimeException.class,
                () -> operationHandler.updateNumberOfFruit(fruitTransaction));
    }
}
