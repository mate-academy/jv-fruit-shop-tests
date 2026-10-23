package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class SupplyOperationTest {
    private static Storage storage;
    private static SupplyOperation supplyOperation;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        supplyOperation = new SupplyOperation(storage);
    }

    @Test
    public void supplyOperationTest() {
        supplyOperation.apply("apple", 10);
        assertEquals(10, storage.getAll().get("apple"));
    }

    @Test
    public void moreSupplyOperationTest() {
        supplyOperation.apply("apple", 10);
        supplyOperation.apply("apple", 5);
        assertEquals(15, storage.getAll().get("apple"));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
