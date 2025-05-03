package core.basesyntax.model;

import core.basesyntax.service.exceptions.InvalidDataException;
import core.basesyntax.service.exceptions.InvalidQuantityException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fruitTransaction_nullOperation_notOk() {
        Assertions.assertThrows(InvalidDataException.class, () -> {
            new FruitTransaction(null, new Fruit("apple"),1);
        });
    }

    @Test
    void fruitTransaction_nullFruit_notOk() {
        Assertions.assertThrows(InvalidDataException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.BALANCE, null,1);
        });
    }

    @Test
    void fruitTransaction_negativeQuantity_notOk() {
        Assertions.assertThrows(InvalidQuantityException.class, () -> {
            new FruitTransaction(FruitTransaction.Operation.BALANCE, new Fruit("apple"),-1);
        });
    }

    @Test
    void setOperation_nullOperation_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        Assertions.assertThrows(InvalidDataException.class, () -> {
            transaction.setOperation(null);
        });
    }

    @Test
    void setFruit_nullFruit_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        Assertions.assertThrows(InvalidDataException.class, () -> {
            transaction.setFruit(null);
        });
    }

    @Test
    void setQuantity_negativeQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction();
        Assertions.assertThrows(InvalidQuantityException.class, () -> {
            transaction.setQuantity(-10);
        });
    }

    @Test
    void operation_fromCode_correctCode_Ok() {
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE,
                FruitTransaction.Operation.fromCode("b"));
        Assertions.assertEquals(FruitTransaction.Operation.SUPPLY,
                FruitTransaction.Operation.fromCode("s"));
        Assertions.assertEquals(FruitTransaction.Operation.PURCHASE,
                FruitTransaction.Operation.fromCode("p"));
        Assertions.assertEquals(FruitTransaction.Operation.RETURN,
                FruitTransaction.Operation.fromCode("r"));
    }

    @Test
    void operation_fromCode_invalidCode_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode("a");
        });
    }

    @Test
    void operation_fromCode_nullCode_notOk() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            FruitTransaction.Operation.fromCode(null);
        });
    }
}
