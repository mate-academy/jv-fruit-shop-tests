package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    public static void setUp() {
        handler = new SupplyHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void handle_addToEmptyFruitStorage_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                5);
        handler.handle(transaction);
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("apple", 0);
        assertSame(5, actualAppleQuantity);
    }

    @Test
    public void handle_addZeroToNotEmptyFruitStorageForZeroQuantity_ok() {
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                0);
        handler.handle(transaction);
        int expectedAppleQuantity = 0;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("apple", 0);
        assertSame(expectedAppleQuantity, actualAppleQuantity);
    }

    @Test
    public void handle_addToFruitStorageForNegativeQuantity_notOk() {
        assertThrows(RuntimeException.class, () -> handler
                .handle(new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                        "banana",
                        -7)));
    }

    @Test
    public void handle_addToFruitStorageForNullFruitTransaction_notOk() {
        assertThrows(NullPointerException.class, () -> handler.handle(null));
    }

    @Test
    public void handle_addToFruitStorageForMultipleFruits_ok() {
        FruitTransaction transaction1 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "apple",
                5);
        FruitTransaction transaction2 = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                "banana",
                7);
        handler.handle(transaction1);
        handler.handle(transaction2);
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("apple", 0);
        Integer actualBananaQuantity = Storage.FRUITS.getOrDefault("banana", 0);
        assertSame(5, actualAppleQuantity);
        assertSame(7, actualBananaQuantity);
    }
}
