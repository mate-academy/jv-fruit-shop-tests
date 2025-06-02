package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.entity.FruitTransaction;
import org.junit.jupiter.api.Test;

public class OperationTest {
    @Test
    void fromCode_shouldThrowIllegalArgumentExceptionForUnknownCode() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode("x");
        });
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void fromCode_shouldReturnCorrectOperation() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_shouldReturnCorrectEnum() {
        assertEquals(FruitTransaction.Operation.BALANCE, FruitTransaction.Operation.fromCode("b"));
        assertEquals(FruitTransaction.Operation.SUPPLY, FruitTransaction.Operation.fromCode("s"));
        assertEquals(FruitTransaction.Operation.PURCHASE, FruitTransaction.Operation.fromCode("p"));
        assertEquals(FruitTransaction.Operation.RETURN, FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void fromCode_shouldThrowExceptionForInvalidCode() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode("x"));
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void fromCode_shouldThrowExceptionForNullCode() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(null));
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void fromCode_shouldThrowExceptionForEmptyCode() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(""));
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }

    @Test
    void fromCode_shouldThrowExceptionForWhitespaceCode() {
        Exception exception = assertThrows(IllegalArgumentException.class,
                () -> FruitTransaction.Operation.fromCode(" "));
        assertTrue(exception.getMessage().contains("Unknown operation code"));
    }
}
