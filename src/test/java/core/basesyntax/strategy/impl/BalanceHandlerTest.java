package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private BalanceHandler balanceHandler;

    @BeforeEach
    void setUp() {
        balanceHandler = new BalanceHandler();
    }

    @DisplayName("Checking for passing pull as fruitTransaction value")
    @Test
    void handle_nullFruitTransaction_notOk() {
        assertThrows(NullPointerException.class, () ->
                balanceHandler.handle(null));
    }

    @DisplayName("Checking for handling transaction with less than minimum balance amount")
    @Test
    void handle_fruitTransactionWithLessThanMinimumBalanceAmount_notOk() {
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", 0);
        assertThrows(RuntimeException.class, () ->
                balanceHandler.handle(fruitTransaction));
    }

    @DisplayName("Checking for handling transaction with minimum balance amount")
    @Test
    void handle_fruitTransactionWithMinimumBalanceAmount_ok() {
        int minimumQuantity = 1;
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", minimumQuantity);
        balanceHandler.handle(fruitTransaction);
        assertTrue(minimumQuantity == Storage.storageMap.get(fruitTransaction.getFruit()));
    }

    @DisplayName("Checking for handling transaction")
    @Test
    void handle_ok() {
        int quantity = 10;
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana", quantity);
        balanceHandler.handle(fruitTransaction);
        assertTrue(quantity == Storage.storageMap.get(fruitTransaction.getFruit()));
    }
}
