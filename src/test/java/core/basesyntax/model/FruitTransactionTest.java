package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void setOperation_creatingNewTransactionWithValidOperation_OK() {
        String operationCode = "b";
        String fruit = "apple";
        int quantity = 23;
        FruitTransaction transaction =
                new FruitTransaction(operationCode, fruit, quantity);
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
        Assertions.assertEquals(fruit, transaction.getFruit());
        Assertions.assertEquals(quantity, transaction.getQuantity());
        Assertions.assertEquals(operationCode, transaction.getOperation().getCode());

        operationCode = "s";
        transaction = new FruitTransaction(operationCode, fruit, quantity);
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
        Assertions.assertEquals(operationCode, transaction.getOperation().getCode());

        operationCode = "p";
        transaction = new FruitTransaction(operationCode, fruit, quantity);
        Assertions.assertEquals(FruitTransaction.Operation.PURCHASE, transaction.getOperation());
        Assertions.assertEquals(operationCode, transaction.getOperation().getCode());

        operationCode = "r";
        transaction = new FruitTransaction(operationCode, fruit, quantity);
        Assertions.assertEquals(FruitTransaction.Operation.RETURN, transaction.getOperation());
        Assertions.assertEquals(operationCode, transaction.getOperation().getCode());
    }

    @Test
    void setOperation_creatingNewTransactionWithNotValidOperation_notOK() {
        String operationCode = "d";
        String fruit = "peach";
        int quantity = 43;
        Assertions.assertThrows(RuntimeException.class,
                () -> new FruitTransaction(operationCode, fruit, quantity));
    }
}
