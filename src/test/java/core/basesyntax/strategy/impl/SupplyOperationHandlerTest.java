package core.basesyntax.strategy.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyOperationHandlerTest {
    private static final String BANANA = "banana";
    private static FruitTransaction fruitTransaction;
    private static SupplyOperationHandler supplyOperationHandler;

    @BeforeAll
    static void beforeAll() {
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setFruit(BANANA);
        fruitTransaction.setQuantity(5);
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @BeforeEach
    void setUp() {
        Storage.SHOPSTORAGE.clear();
    }

    @Test
    void handle_addFruitToStorage_ok() {
        Storage.SHOPSTORAGE.put(BANANA, 20);
        supplyOperationHandler.handle(fruitTransaction);
        assertEquals(25, Storage.SHOPSTORAGE.get(BANANA));
    }

    @Test
    void handle_putFruit_ok() {
        supplyOperationHandler.handle(fruitTransaction);
        assertEquals(5, Storage.SHOPSTORAGE.get(BANANA));
    }
}
