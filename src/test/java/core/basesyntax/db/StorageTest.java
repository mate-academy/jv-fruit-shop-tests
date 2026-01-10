package core.basesyntax.db;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class StorageTest {

    @Test
    void shouldReturnBalanceOperation() {
        Storage.Operation operation =
                Storage.Operation.fromCode("b");

        assertEquals(Storage.Operation.BALANCE, operation);
    }

    @Test
    void shouldThrowExceptionForUnknownCode() {
        assertThrows(RuntimeException.class,
                () -> Storage.Operation.fromCode("x"));
    }
}
