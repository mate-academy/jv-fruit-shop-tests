package model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class InputDataTypeTest {
    private Fruit fruit;

    @BeforeEach
    void setUp() {
        fruit = new Fruit("banana", 125);
    }

    @Test
    void getOperation_ok() {
        InputDataType expected = new InputDataType(Operation.BALANCE, fruit);
        assertEquals(expected.getOperation(), Operation.BALANCE,
                "Actual and expected operation must be the same");
    }

    @Test
    void getFruit_ok() {
        InputDataType expected = new InputDataType(Operation.BALANCE, fruit);
        assertEquals(expected.getFruit(), fruit,
                "Actual and expected fruit must be equals");
    }
}
