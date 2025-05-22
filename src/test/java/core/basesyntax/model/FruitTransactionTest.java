package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fromCode_nonExistCode_NotOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromCode("Invalid code");
        });
    }

    @Test
    void fromCode_ExistCode_Ok() {
        assertDoesNotThrow(() -> FruitTransaction.Operation.fromCode("p"));
    }
}
