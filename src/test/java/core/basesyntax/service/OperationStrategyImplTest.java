package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    @Test
    public void fromString_getCorrectOperation_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.fromString("b");
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        assertEquals(expected, actual);
    }

    @Test
    public void fromString_getIncorrectOperationType_notOk() {
        assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation.fromString(
                    "invalid_value");
        });
    }
}
