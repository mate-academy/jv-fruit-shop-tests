package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import strategy.BalanceOperation;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation = new BalanceOperation();

    @BeforeAll
    static void beforeAll() {
        Storage.fruits.put("Banana",10);
    }

    @Test
    void balanceOperation_WithNegativeBalance_NotOk() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Banana", -5);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            balanceOperation.processOperation(transaction);
        });

        assertEquals("Balance can not be negative!Try Again", exception.getMessage());

    }

    @Test
    void balanceOperation_Ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "Banana", 15);
        balanceOperation.processOperation(transaction);
        assertEquals(15,Storage.fruits.get("Banana"));
    }

}
