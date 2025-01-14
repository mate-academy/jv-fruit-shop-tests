package core.basesyntax;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.FruitTransaction.Operation;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationTest {
    private static final String FRUIT_KEY = "banana";

    private Storage storage;
    private OperationHandler balanceOperation;

    @BeforeEach
    void setUp() {
        storage = new Storage();
        balanceOperation = new BalanceOperation();
    }

    @AfterEach
    void tearDown() {
        storage.clear();
    }

    @Test
    void handle_balanceOperation_validItemAndQuantity_Ok() {
        int expectedValue = 50;
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE,
                FRUIT_KEY, expectedValue);
        balanceOperation.handle(transaction, storage);
        Assertions.assertEquals(expectedValue, storage.getInventory().get(FRUIT_KEY));
    }

    @Test
    void handle_balanceOperation_alreadyExistingItem_NotOk() {
        int startValue = 50;
        balanceOperation = new BalanceOperation();
        FruitTransaction transaction = new FruitTransaction(Operation.BALANCE,
                FRUIT_KEY, startValue);
        balanceOperation.handle(transaction, storage);
        FruitTransaction redundantTransaction = new FruitTransaction(Operation.BALANCE,
                FRUIT_KEY, startValue);
        Exception exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.handle(redundantTransaction, storage));
        String expectedMessage = "Fruit " + FRUIT_KEY
                + " is already present in the inventory";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
        Assertions.assertEquals(startValue, storage.getInventory().get(FRUIT_KEY));
    }

    @Test
    void handle_balanceOperation_negativeQuantity_NotOk() {
        int negativeQuantity = -10;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                FRUIT_KEY, negativeQuantity);
        IllegalArgumentException exception = Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceOperation.handle(transaction, storage));
        String expectedMessage = "Quantity to set must be greater than 0";
        Assertions.assertEquals(expectedMessage, exception.getMessage());
    }
}
