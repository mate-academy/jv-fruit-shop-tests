package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static Storage storage;
    private static BalanceOperation balanceOperation;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation(storage);
    }

    @Test
    public void balanceOperationTest() {
        balanceOperation.apply("apple", 10);
        assertEquals(10, storage.getAll().get("apple"));
    }

    @Test
    public void moreBalanceOperationTest() {
        balanceOperation.apply("apple", 10);
        balanceOperation.apply("apple", 5);
        assertEquals(5, storage.getAll().get("apple"));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
