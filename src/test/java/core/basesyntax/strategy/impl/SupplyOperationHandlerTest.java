package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.strategy.OperationHandler;
import core.basesyntax.service.strategy.impl.SupplyOperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
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
        fruitTransaction.setQuantity(1);
        fruitTransaction.setFruit("banana");
        operationHandler.updateNumberOfFruit(fruitTransaction);
        assertEquals(21, Storage.STORAGE.get("banana"));
    }
}
