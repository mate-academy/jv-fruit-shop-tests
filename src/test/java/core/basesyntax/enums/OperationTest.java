package core.basesyntax.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationTest {

    public static final String[] VALID_CODES = {"b", "s", "p", "r"};
    public static final Operation[] EXPECTED_OPERATIONS = Operation.values();

    @Test
    public void fromCode_ValidCodes_ReturnsExpectedOperations() {
        for (int i = 0; i < VALID_CODES.length; i++) {
            Operation expectedOperation = EXPECTED_OPERATIONS[i];
            Operation actualOperation = Operation.fromCode(VALID_CODES[i]);
            Assertions.assertEquals(expectedOperation, actualOperation);
        }
    }

    @Test
    public void fromCode_InvalidCode_ThrowsIllegalArgumentException() {
        String invalidCode = "x";
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            Operation.fromCode(invalidCode);
        });
    }
}
