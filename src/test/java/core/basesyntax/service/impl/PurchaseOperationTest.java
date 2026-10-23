package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class PurchaseOperationTest {
    private static Storage storage;
    private static PurchaseOperation purchaseOperation;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        purchaseOperation = new PurchaseOperation(storage);
    }

    @Test
    public void removeOperationTest() {
        storage.add("apple", 15);
        purchaseOperation.apply("apple", 10);
        assertEquals(5, storage.getAll().get("apple"));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
