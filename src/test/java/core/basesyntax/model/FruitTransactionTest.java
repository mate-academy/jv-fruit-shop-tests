package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private final FruitTransaction.Operation operation = FruitTransaction.Operation.BALANCE;
    private final String fruit = "banana";
    private final Integer quantity = 100;
    private final String code = "b";
    private final String wrongCode = "g";

    @Test
    void createFruitTransaction_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(operation, fruit, quantity);
        Assertions.assertEquals(fruitTransaction.getFruit(), fruit);
        Assertions.assertEquals(fruitTransaction.getOperation(), operation);
        Assertions.assertEquals(fruitTransaction.getQuantity(), quantity);
    }

    @Test
    void getOperation_Ok() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.getOperation(code);
        Assertions.assertEquals(actual, operation);
    }

    @Test
    void getOperation_Null_NotOk() {
        try {
            FruitTransaction.Operation actual = FruitTransaction.Operation.getOperation(null);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RunTimeException should occur when the value is empty");
    }

    @Test
    void getOperation_wrongValue_NotOk() {
        try {
            FruitTransaction.Operation actual = FruitTransaction.Operation.getOperation(wrongCode);
        } catch (RuntimeException e) {
            return;
        }
        Assertions.fail("RunTimeException should occur when the value is non-existing");
    }
}
