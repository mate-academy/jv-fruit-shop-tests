package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void getSetFruit_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        Fruit expected = new Fruit("banana", 20);
        transaction.setFruit(expected);
        Fruit actual = transaction.getFruit();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getSetOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction();
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        transaction.setOperation(expected);
        FruitTransaction.Operation actual = transaction.getOperation();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        Fruit fruit = new Fruit("banana", 20);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);

        Assertions.assertEquals(fruitTransaction.getOperation(),
                FruitTransaction.Operation.BALANCE);
    }

    @Test
    void getOperationByCode_Ok() {
        String code = "b";
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction.Operation actual = FruitTransaction.Operation.getByCode(code);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getByInvalidCode_NotOk() {
        String code = "q";
        Assertions.assertThrows(RuntimeException.class,
                () -> FruitTransaction.Operation.getByCode(code));
    }
}
