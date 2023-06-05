package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class PurchaseHandlerTest {
    private static PurchaseHandler purchaseHandler;

    @BeforeAll
    static void beforeAll() {
        purchaseHandler = new PurchaseHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.put("banana", 10);
    }

    @Test
    public void handle_inputValidTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana",
                10);
        purchaseHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 0;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_inputZeroQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana",
                0);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(transaction));
    }

    @Test
    public void handle_negativeInputValue_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana",
                -10);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(transaction));
    }

    @Test
    public void handle_outOfStock_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana",
                20);
        assertThrows(RuntimeException.class,
                () -> purchaseHandler.handle(transaction));
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
