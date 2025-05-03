package core.basesyntax.handler;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {

    private BalanceHandler balanceOperation;

    @BeforeEach
    void setUp() {
        balanceOperation = new BalanceHandler();
    }

    @Test
    void apply_shouldModifyStorageForBalanceOperation() {
        String fruit = "apple";
        int quantity = 100;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, quantity);

        balanceOperation.handle(transaction);

        int result = Storage.getFruitQuantity(fruit);
        assertEquals(quantity, result);
    }

    @Test
    void apply_shouldHandleZeroQuantity() {
        String fruit = "banana";
        int quantity = 0;
        FruitTransaction transaction = new FruitTransaction(FruitTransaction
                .Operation.BALANCE, fruit, quantity);

        balanceOperation.handle(transaction);

        int result = Storage.getFruitQuantity(fruit);
        assertEquals(quantity, result);
    }

    @AfterEach
    void clearStorage() {
        Storage.clearStorage();
    }
}
