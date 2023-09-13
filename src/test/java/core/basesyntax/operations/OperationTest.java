package core.basesyntax.operations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {
    private static final String INCORRECT_OPERATION = "f";
    private static final String CORRECT_OPERATION = "b";
    private static final Operation EXPECTED_OPERATION = Operation.BALANCE;

    @Test
    void operation_notExistReturnNull_Ok() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.getOperationFromString(INCORRECT_OPERATION));
    }

    @Test
    void operation_ExistOperationReturnOperationType_Ok() {
        Operation actualOperation = Operation.getOperationFromString(CORRECT_OPERATION);
        assertEquals(EXPECTED_OPERATION, actualOperation);
    }
}
