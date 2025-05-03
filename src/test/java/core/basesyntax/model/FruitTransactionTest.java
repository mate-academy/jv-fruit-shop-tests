package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String NOT_EXISTING_CODE = "k";
    private static final String EXISTING_CODE = "s";

    @Test
    void fromCode_notExistingCode_notOk() {
        assertThrows(RuntimeException.class, () -> Operation.fromCode(NOT_EXISTING_CODE),
                "RuntimeException should be thrown if code for the operation invalid");
    }

    @Test
    void fromCode_ExistingCode_Ok() {
        Operation expected = Operation.SUPPLY;
        Operation actual = Operation.fromCode(EXISTING_CODE);
        assertEquals(expected, actual);
    }
}
