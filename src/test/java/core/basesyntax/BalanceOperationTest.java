package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceOperation();
        Storage.clear();
    }

    @AfterEach
    void tearDown() {
        Storage.clear();
    }

    @Test
    void handle_validTransaction_ok() {
        String fruit = "apple";
        int balance = 100;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, balance);
        balanceOperation.handle(transaction);
        assertEquals(balance, Storage.getAll().get(fruit));
    }

    @Test
    void handle_updateExistingFruitBalance_ok() {
        String fruit = "banana";
        int oldBalance = 50;
        int newBalance = 200;
        Storage.add(fruit, oldBalance);

        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, fruit, newBalance
        );

        balanceOperation.handle(transaction);
        assertEquals(newBalance, Storage.getAll().get(fruit));
    }
}
