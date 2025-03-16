package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    @Test
    void getOperation_properCode_ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperation("b");
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getOperation_wrongCode_notOk() {
        String operationCode = "l";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.getOperation(operationCode));
    }

    @Test
    void getOperation_emptyCode_notOk() {
        String operationCode = "";
        Assertions.assertThrows(IllegalArgumentException.class, () ->
                FruitTransaction.Operation.getOperation(operationCode));
    }
}
