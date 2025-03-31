package core.basesyntax.operationhandler.operation;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.storage.Storage;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private final ReturnOperation returnOperation = new ReturnOperation();

    @BeforeEach
    void setup() {
        Storage.getFruitStorage().clear();
    }

    @Test
    void testAddNewBanana_OK() {
        returnOperation.handleOperation("banana",14);
        assertEquals(14, Storage.getFruitStorage().get("banana"));
    }

    @Test
    void testAddToExistingBanana_OK() {
        Storage.getFruitStorage().put("banana", 16);
        returnOperation.handleOperation("banana",14);
        assertEquals(30, Storage.getFruitStorage().get("banana"));
    }
}
