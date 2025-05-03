package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.operations.exception.InvalidInputDataException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        Storage.storage.clear();
    }

    @Test
    void handle_BalanceOperation_Ok() {
        int expected = 50;

        balanceOperation.handle("banana", 50);
        int actual = Storage.storage.get("banana");

        assertEquals(expected, actual);
    }

    @Test
    void handle_balanceNullInputFruit_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> balanceOperation.handle(null, 10),
                "InvalidInputDataException expected to be thrown");
    }

    @Test
    void handle_balanceNegativeInputFruitQuantity_notOk() {
        assertThrows(InvalidInputDataException.class,
                () -> balanceOperation.handle("banana", -5),
                "InvalidInputDataException expected to be thrown");
    }
}

