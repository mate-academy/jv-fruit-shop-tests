package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationTest {
    private Operation operation;

    @BeforeEach
    void setUp() {
        operation = Operation.BALANCE;
    }

    @Test
    void getCode_ok() {
        String expectedCode = "b";
        assertEquals(expectedCode, operation.getCode(),
                "Actual and expected code's must be equals");
    }

    @Test
    void getByCode_ok() {
        Operation expectedOperation = Operation.BALANCE;
        Operation actualOperation = Operation.getByCode("b");
        assertEquals(expectedOperation, actualOperation,
                "Actual and expected operations must be equals");
    }
}
