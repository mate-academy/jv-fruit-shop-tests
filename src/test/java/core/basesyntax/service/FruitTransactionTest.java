package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    @Test
    void setOperation_supply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 10);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
    }

    @Test
    void setFruit_supply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 10);
        fruitTransaction.setFruit("Banana");
        assertEquals("Banana", fruitTransaction.getFruit());
    }

    @Test
    void setQuantity_supply_ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Apple", 10);
        fruitTransaction.setQuantity(5);
        assertEquals(5, fruitTransaction.getQuantity());
    }

    @Test
    void getByCode_correctOperation_ok() {
        FruitTransaction.Operation expectedOperation = FruitTransaction.Operation.RETURN;
        FruitTransaction.Operation actualOperation = FruitTransaction.Operation.getByCode("r");
        assertEquals(expectedOperation, actualOperation);
    }

    @Test
    void getByCode_notCorrectOperation_noOk() {
        assertThrows(RuntimeException.class, () ->
                        FruitTransaction.Operation.getByCode("unknown"),
                "Expected RuntimeException but it was not thrown");
    }
}
