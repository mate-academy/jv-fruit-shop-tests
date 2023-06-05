package core.basesyntax.strategy.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.transaction.FruitTransaction;
import core.basesyntax.transaction.Operation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReturnOperationHandlerTest {
    private ReturnOperationHandler returnOperationHandler;

    @BeforeEach
    public void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test
    public void handle_validFruitTransaction_Ok() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int returnedQuantity = 3;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.RETURN, fruit, returnedQuantity);
        returnOperationHandler.handle(fruitTransaction);
        int expectedQuantity = initialQuantity + returnedQuantity;
        Assertions.assertEquals(expectedQuantity, Storage.FRUITS.get(fruit));
    }

    @Test
    public void handle_nullFruit_NotOk() {
        int returnedQuantity = 3;
        FruitTransaction fruitTransaction =
                new FruitTransaction(Operation.RETURN, null, returnedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_emptyFruit_NotOk() {
        int returnedQuantity = 3;
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.RETURN, "", returnedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_negativeQuantity_NotOk() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int returnedQuantity = -3;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.RETURN, fruit, returnedQuantity);
        Assertions.assertThrows(IllegalArgumentException.class,
                () -> returnOperationHandler.handle(fruitTransaction));
    }

    @Test
    public void handle_insufficientQuantity_NotOk() {
        String fruit = "Apple";
        int initialQuantity = 10;
        int returnedQuantity = 15;
        Storage.FRUITS.put(fruit, initialQuantity);
        FruitTransaction fruitTransaction
                = new FruitTransaction(Operation.RETURN, fruit, returnedQuantity);
        Assertions.assertThrows(RuntimeException.class,
                () -> returnOperationHandler.handle(fruitTransaction));
    }

    @AfterEach
    void tearDown() {
        Storage.FRUITS.clear();
    }
}
