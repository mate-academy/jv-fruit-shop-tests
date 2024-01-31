package service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import model.FruitTransaction;
import org.junit.jupiter.api.Test;
import strategy.Operation;

class OperationTest {
    private static final FruitTransaction validFruit =
            new FruitTransaction("b", "banana", 100);
    private static final FruitTransaction invalidOperationField =
            new FruitTransaction("q", "banana", 100);

    @Test
    public void validOperationTest_Ok() {
        Operation expected = Operation.BALANCE;
        Operation actual = Operation.fromString(validFruit.getOperation());
        assertEquals(expected, actual,
                "Must be " + expected + " but was " + actual);
    }

    @Test
    public void invalidOperationFieldTest_NotOk() {
        assertThrows(IllegalArgumentException.class,
                () -> Operation.fromString(invalidOperationField.getOperation()));
    }
}
