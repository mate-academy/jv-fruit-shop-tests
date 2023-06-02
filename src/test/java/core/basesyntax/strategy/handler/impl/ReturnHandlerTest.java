package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.handler.OperationHandler;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class ReturnHandlerTest {
    private static OperationHandler handler;

    @BeforeAll
    public static void beforeAll() {
        handler = new ReturnHandler();
    }

    @AfterEach
    public void afterEach() {
        Storage.FRUITS.clear();
    }

    @Test
    public void handle_addToEmptyFruitStorage_ok() {
        Storage.FRUITS.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "apple",
                5);
        handler.handle(transaction);
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("apple", 0);
        assertSame(5, actualAppleQuantity);
    }

    @Test
    public void handle_addToNotEmptyFruitStorage_ok() {
        Storage.FRUITS.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                "banana",
                7);
        handler.handle(transaction);
        int expectedBananaQuantity = 5
                + 7;
        Integer actualAppleQuantity = Storage.FRUITS.getOrDefault("banana", 0);
        assertSame(expectedBananaQuantity, actualAppleQuantity);
    }

    @Test
    public void handle_addZeroToNotEmptyFruitStorageForZeroQuantity_ok() {
        Storage.FRUITS.put("banana", 5);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
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
}
