package core.basesyntax.model;

import core.basesyntax.model.FruitTransaction.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    public void constructorAndGetters_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("apple", 12, Operation.SUPPLY);

        Assertions.assertEquals(fruitTransaction.getFruitName(), "apple");
        Assertions.assertEquals(fruitTransaction.getQuantity(), 12);
        Assertions.assertEquals(fruitTransaction.getOperation(), Operation.SUPPLY);
    }

    @Test
    public void constructorAndGetters_notOk() {
        FruitTransaction fruitTransaction = new FruitTransaction("apple", 12, Operation.SUPPLY);

        Assertions.assertNotEquals(fruitTransaction.getFruitName(), "orange");
        Assertions.assertNotEquals(fruitTransaction.getQuantity(), 1);
        Assertions.assertNotEquals(fruitTransaction.getOperation(), Operation.BALANCE);
    }

    @Test
    void settersTest_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction("apple", 12, Operation.SUPPLY);

        fruitTransaction.setFruitName("orange");
        fruitTransaction.setQuantity(1);
        fruitTransaction.setOperation(Operation.BALANCE);

        Assertions.assertEquals(fruitTransaction.getFruitName(), "orange");
        Assertions.assertEquals(fruitTransaction.getQuantity(), 1);
        Assertions.assertEquals(fruitTransaction.getOperation(), Operation.BALANCE);
    }

    @Test
    void testOperationFromCode() {
        Operation supplyOperation = Operation.SUPPLY;
        Operation purchaseOperation = Operation.PURCHASE;
        Operation returnOperation = Operation.RETURN;
        Operation balanceOperation = Operation.BALANCE;

        Assertions.assertEquals(supplyOperation, Operation.fromCode("s"));
        Assertions.assertEquals(purchaseOperation, Operation.fromCode("p"));
        Assertions.assertEquals(returnOperation, Operation.fromCode("r"));
        Assertions.assertEquals(balanceOperation, Operation.fromCode("b"));
    }

    @Test
    void testInvalidOperationCode() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> Operation.fromCode("sdfa"));
    }
}
