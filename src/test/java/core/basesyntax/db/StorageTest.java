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
    void shouldReturnSupplyOperation() {
        Storage.Operation operation =
                Storage.Operation.fromCode("s");

        assertEquals(Storage.Operation.SUPPLY, operation);
    }

    @Test
    void shouldReturnPurchaseOperation() {
        Storage.Operation operation =
                Storage.Operation.fromCode("p");

        assertEquals(Storage.Operation.PURCHASE, operation);
    }

    @Test
    void shouldReturnReturnOperation() {
        Storage.Operation operation =
                Storage.Operation.fromCode("r");

        assertEquals(Storage.Operation.RETURN, operation);
    }

    @Test
    void shouldThrowExceptionForUnknownCode() {
        assertThrows(RuntimeException.class,
                () -> Storage.Operation.fromCode("x"));
    }
}
