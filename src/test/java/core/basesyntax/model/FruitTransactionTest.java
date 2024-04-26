package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final String FAKE_CODE = "5";

    @Test
    public void getOperationByCode_Test() {
        assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.getByCode(FAKE_CODE));
    }
}
