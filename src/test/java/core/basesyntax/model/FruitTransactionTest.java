package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String FAKE_CODE = "g";
    private static final String VALID_CODE = "b";

    @Test
    public void caseToOperation_NonExistingCode_Throws() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.getByCode(FAKE_CODE));
    }

    @Test
    public void caseToOperation_ExistingCode_DoesNotThrow() {
        assertDoesNotThrow(() ->
                FruitTransaction.Operation.getByCode(VALID_CODE));
    }
}
