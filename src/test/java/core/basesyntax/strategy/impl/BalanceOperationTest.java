package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private static BalanceOperation balanceOperation;
    private static Storage storage;

    @BeforeAll
    static void beforeAll() {
        storage = new Storage();
        balanceOperation = new BalanceOperation();
    }

    @BeforeEach
    void clearStorage() {
        storage.clear();
    }

    @Test
    void operateValidData() {
        balanceOperation.operate(new FruitTransaction("b", "apple", 100), storage);
        assertEquals(100, storage.getQuantityByFruit("apple"));
    }

    @Test
    void operateValidDataEdge() {
        balanceOperation.operate(new FruitTransaction("b", "apple", 0), storage);
        assertEquals(0, storage.getQuantityByFruit("apple"));
    }
}
