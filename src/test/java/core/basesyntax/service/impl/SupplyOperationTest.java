package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private OperationHandler supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.putFruit("banana", 50);
    }

    @Test
    void handle_validSupply_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 20);
        supplyOperation.handle(transaction);
        assertEquals(70, Storage.getFruitQuantity("banana"));
    }

    @Test
    void handle_newFruit_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 10);
        supplyOperation.handle(transaction);
        assertEquals(10, Storage.getFruitQuantity("apple"));
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }
}
