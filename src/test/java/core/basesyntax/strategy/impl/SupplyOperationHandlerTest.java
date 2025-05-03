package core.basesyntax.strategy.impl;

import static org.junit.Assert.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SupplyOperationHandlerTest {
    private SupplyOperationHandler supplyOperationHandler;

    @BeforeEach
    public void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test
    public void handle_validFruitTransaction_Ok() {
        String fruit = "Apple";
        int initialQuantity = 5;
        int suppliedQuantity = 3;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY, fruit, suppliedQuantity);
        supplyOperationHandler.handle(fruitTransaction);
        int expectedQuantity = initialQuantity + suppliedQuantity;
        Assertions.assertEquals(expectedQuantity, Storage.FRUITS.get(fruit));
    }

    @Test
    public void handle_nullFruit_NotOk() {
        int suppliedQuantity = 3;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY, null, suppliedQuantity);
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_emptyFruit_NotOk() {
        int suppliedQuantity = 3;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY, "", suppliedQuantity);
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_negativeQuantity_NotOk() {
        String fruit = "Apple";
        int initialQuantity = 5;
        int suppliedQuantity = -3;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.SUPPLY, fruit, suppliedQuantity);
        assertThrows(IllegalArgumentException.class,
                () -> supplyOperationHandler.handle(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
