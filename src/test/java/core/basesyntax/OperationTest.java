package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class OperationTest {

    @Test
    void getOperationFromCodeWithNullValue() {
        assertThrows(RuntimeException.class, () -> Operation.getOperationFromCode(null));
    }
}
