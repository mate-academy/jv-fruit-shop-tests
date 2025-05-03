package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private BalanceOperationHandler balanceOperationHandler;

    @BeforeEach
    public void setUp() {
        balanceOperationHandler = new BalanceOperationHandler();
        Storage.FRUITS.clear();
    }

    @Test
    public void handle_validFruitTransaction_Ok() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int addedQuantity = 5;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, fruit, addedQuantity);
        balanceOperationHandler.handle(fruitTransaction);
        int expectedQuantity = initialQuantity + addedQuantity;
        Assertions.assertEquals(expectedQuantity, Storage.FRUITS.get(fruit));
    }

    @Test
    public void handle_nullFruit_NotOk() {
        int addedQuantity = 5;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, null, addedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_emptyFruit_NotOk() {
        int addedQuantity = 5;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, "", addedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_negativeQuantity_NotOk() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int addedQuantity = -5;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.BALANCE, fruit, addedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> balanceOperationHandler.handle(fruitTransaction));
    }
}
