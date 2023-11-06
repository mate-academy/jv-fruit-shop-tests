package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import data.base.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import service.strategy.OperationHandler;
import service.strategy.hendlers.SupplyOperationHandler;

public class SupplyOperationHandlerTest {
    private static OperationHandler operationHandler;

    @BeforeAll
    static void beforeAll() {
        operationHandler = new SupplyOperationHandler();
    }

    @AfterEach
    void tearDown() {
        Storage.STORAGE.clear();
    }

    @Test
    void updateNumberOfFruit_validQuantity_Ok() {
        Storage.STORAGE.put("banana", 20);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setQuantity(1);
        fruitTransaction.setFruit("banana");
        operationHandler.handleOperation(fruitTransaction);
        assertEquals(21, Storage.STORAGE.get("banana"));
    }
}

