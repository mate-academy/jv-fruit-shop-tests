package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

public class FruitTransactionTest {

    @Test
    void initialize_correctTransaction_Ok() {
        FruitTransaction actualFruitTransaction = new FruitTransaction();
        actualFruitTransaction.setFruit("banana");
        actualFruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        actualFruitTransaction.setCount(10);
        FruitTransaction expectedFruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "banana", 10
        );
        assertEquals(expectedFruitTransaction, actualFruitTransaction);
    }

    @Test
    void initialize_incorrectOperation_NotOk() {
        FruitTransaction actualFruitTransaction = new FruitTransaction();
        actualFruitTransaction.setFruit("banana");
        assertThrows(IllegalArgumentException.class,
                () -> actualFruitTransaction
                        .setOperation(FruitTransaction.Operation.getOperation("a"))
        );
    }

    @Test
    void get_correctCodeOperation_Ok() {
        String actualOperationCode =
                FruitTransaction.Operation.BALANCE.getCode();
        String expectedOperationCode = "b";
        assertEquals(expectedOperationCode, actualOperationCode);
    }

}
