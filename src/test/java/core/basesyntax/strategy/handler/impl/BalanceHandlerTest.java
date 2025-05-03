package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class BalanceHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    public static void setUp() {
        handler = new BalanceHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void handle_addFruitToStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                5);
        handler.handle(transaction);
        int expectedAppleQuantity = 5;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("apple", 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void handle_addFruitWithZeroQuantityToStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                "apple",
                0);
        handler.handle(transaction);
        int expectedAppleQuantity = 0;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("apple", 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void handle_addNegativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> handler
                .handle(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        -5)));
    }
}
