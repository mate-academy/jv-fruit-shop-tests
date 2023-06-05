package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyHandlerTest {
    private SupplyHandler supplyHandler;

    @BeforeEach
    void setUp() {
        supplyHandler = new SupplyHandler();
    }

    @Test
    public void handle_inputValidTransaction_ok() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",
                10);
        supplyHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 10;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void handle_inputZeroQuantity_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",
                0);
        assertThrows(RuntimeException.class,
                () -> supplyHandler.handle(transaction));
    }

    @Test
    public void handle_negativeInputValue_notOk() {
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",
                -10);
        assertThrows(RuntimeException.class,
                () -> supplyHandler.handle(transaction));
    }

    @Test
    public void handle_supplyExistingFruit_ok() {
        Storage.put("banana", 10);
        FruitTransaction transaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana",
                10);
        supplyHandler.handle(transaction);
        int actualQuantity = Storage.get(transaction.getFruit());
        int expectedQuantity = 20;
        assertEquals(expectedQuantity, actualQuantity);
    }

    @AfterEach
    public void afterEach() {
        Storage.fruitStorage.clear();
    }
}
