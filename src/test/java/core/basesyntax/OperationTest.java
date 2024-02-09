package core.basesyntax;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationTest {

    @BeforeEach
    public void setUp() {
        Operation operation;
    }

    @Test
    public void getOperation_FromNullValue_ThrowException() {
        assertThrows(RuntimeException.class, () -> Operation.getOperationFromCode(null));
    }

    @Test
    public void getOperationFromCode_ThrowException() {
        assertThrows(RuntimeException.class, () -> Operation.getOperationFromCode("invalid"));
    }

    @Test
    void getOperationFromCode_Success() {
        assertEquals(Operation.BALANCE, Operation.getOperationFromCode("b"));
        assertEquals(Operation.SUPPLY, Operation.getOperationFromCode("s"));
        assertEquals(Operation.PURCHASE, Operation.getOperationFromCode("p"));
        assertEquals(Operation.RETURN, Operation.getOperationFromCode("r"));
    }
}
