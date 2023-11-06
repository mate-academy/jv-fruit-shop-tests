package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void operationGetByOperationIs_Ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.getByCode("b");
        Assertions.assertEquals(balanceOperation, (FruitTransaction.Operation.BALANCE));
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.getByCode("r");
        Assertions.assertEquals(returnOperation, (FruitTransaction.Operation.RETURN));
    }

    @Test
    void operationGetByOperationIs_Not_Ok() {
        Assertions.assertThrows(RuntimeException.class, () -> FruitTransaction
                .Operation.getByCode("x"));
    }

    @Test
    void enumTestIs_Ok() {
        Assertions.assertEquals(4, FruitTransaction.Operation.values().length);
    }

    @Test
    void fieldsOfFruitTransactionIs_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit("banana");
        fruitTransaction.setQuantity(10);
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals("banana", fruitTransaction.getFruit());
        Assertions.assertEquals(10, fruitTransaction.getQuantity());
        Assertions.assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction
                .getOperation());
    }
}
