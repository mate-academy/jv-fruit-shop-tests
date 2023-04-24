package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    public void testGettersAndSetters_Ok() {
        Fruit fruit = new Fruit("apple", 10);
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setOperation(operation);
        Assertions.assertEquals(fruit, fruitTransaction.getFruit());
        Assertions.assertEquals(operation, fruitTransaction.getOperation());
    }

    @Test
    public void testGetByValidCode_Ok() {
        String code = "s";
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.SUPPLY;
        FruitTransaction.Operation operation = FruitTransaction.Operation.getByCode(code);
        Assertions.assertEquals(expectedOperation, operation);
    }

    @Test
    public void testGetByInvalidCode_NotOk() {
        String code = "invalidCode";
        Assertions.assertThrows(RuntimeException.class, () -> {
            FruitTransaction.Operation operation = FruitTransaction.Operation.getByCode(code);
        });
    }
}
