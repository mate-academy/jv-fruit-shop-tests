package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionTest {
    @Test
    public void correctInputData_Ok() {
        String balance = "b";
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperation(balance);
        FruitTransaction.Operation expected = Operation.BALANCE;
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void incorrectOInputData_NotOk() {
        String incorrectOperator = "incorect";
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getOperation(incorrectOperator), "Invalid input");
    }

    @Test
    public void incorrectInputData_NNull_NotOk() {
        String incorrectOperator = null;
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getOperation(incorrectOperator), "Invalid input");
    }
}
