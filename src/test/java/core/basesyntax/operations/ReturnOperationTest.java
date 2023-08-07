package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnOperationTest {
    private ReturnOperation returnOperation;

    @BeforeEach
    void setUp() {
        returnOperation = new ReturnOperation();
        Storage.storage.clear();
    }

    @Test
    void handle_returnOperation_Ok() {
        int expected = 150;
        Storage.storage.put("banana", 100);

        returnOperation.handle("banana", 50);
        int actual = Storage.storage.get("banana");

        assertEquals(expected, actual);
    }

    @Test
    void handle_returnNegativeQuantity_notOk() {
        Storage.storage.put("banana", 100);
        assertThrows(InvalidInputDataException.class,
                () -> returnOperation.handle("banana", -5),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_returnZeroQuantity_notOk() {
        Storage.storage.put("banana", 100);
        assertThrows(InvalidInputDataException.class,
                () -> returnOperation.handle("banana", 0),
                "InvalidInputDataException to be thrown");
    }

    @Test
    void handle_returnNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> returnOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }
}
