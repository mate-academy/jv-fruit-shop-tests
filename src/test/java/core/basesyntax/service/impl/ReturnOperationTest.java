package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReturnOperationTest {
    private static Storage storage;
    private static ReturnOperation returnOperation;

    @BeforeAll
    public static void setUp() {
        storage = new Storage();
        returnOperation = new ReturnOperation(storage);
    }

    @Test
    public void returnOperationTest() {
        returnOperation.apply("apple", 10);
        assertEquals(10, storage.getAll().get("apple"));
    }

    @Test
    public void moreReturnOperationTest() {
        returnOperation.apply("apple", 10);
        returnOperation.apply("apple", 5);
        assertEquals(15, storage.getAll().get("apple"));
    }

    @AfterEach
    public void clearStorage() {
        storage.getAll().clear();
    }
}
