package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private static Storage storage;
    private static SupplyOperation supplyOperation;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        supplyOperation = new SupplyOperation();
    }

    @BeforeEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void operateValidData() {
        storage.set("apple", 100);
        supplyOperation.operate(new FruitTransaction("b", "apple", 100), storage);
        assertEquals(200, storage.getQuantityByFruit("apple"));
    }

    @Test
    void operateValidDataEdge() {
        storage.set("apple", 0);
        supplyOperation.operate(new FruitTransaction("b", "apple", 0), storage);
        assertEquals(0, storage.getQuantityByFruit("apple"));
    }
}
