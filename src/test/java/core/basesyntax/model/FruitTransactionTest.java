package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void operationGetByOperation_ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.getByCode("b");
        Assertions.assertEquals(balanceOperation, (FruitTransaction.Operation.BALANCE));
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.getByCode("r");
        Assertions.assertEquals(returnOperation, (FruitTransaction.Operation.RETURN));
    }

    @Test
    void operationGetByOperation_IsNot_ok() {
        Assertions.assertThrows(RuntimeException.class, () -> FruitTransaction
                .Operation.getByCode("x"));
    }

    @Test
    void enumTestIs_Ok() {
        Assertions.assertEquals(4, FruitTransaction.Operation.values().length);
    }

    @Test
    void fieldsOfFruitTransaction_ok() {
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
