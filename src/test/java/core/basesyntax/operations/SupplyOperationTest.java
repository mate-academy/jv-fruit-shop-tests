package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationTest {
    private SupplyOperation supplyOperation;

    @BeforeEach
    void setUp() {
        supplyOperation = new SupplyOperation();
        Storage.storage.clear();
    }

    @Test
    void handle_supplyOperation_Ok() {
        int expected = 100;
        Storage.storage.put("banana", 50);

        supplyOperation.handle("banana", 50);
        int actual = Storage.storage.get("banana");

        assertEquals(expected, actual);
    }

    @Test
    void handle_supplyNegativeQuantity_notOk() {
        Storage.storage.put("banana", 10);
        assertThrows(InvalidInputDataException.class, () -> supplyOperation.handle("banana", -5),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_supplyZeroQuantity_notOk() {
        Storage.storage.put("banana", 10);
        assertThrows(InvalidInputDataException.class, () -> supplyOperation.handle("banana", 0),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_supplyNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> supplyOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }
}
