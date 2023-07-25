package core.basesyntax.strategy;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BalanceOperationHandlerTest {
    private BalanceOperationHandler handler;

    @BeforeEach
    void setUp() {
        handler = new BalanceOperationHandler();
        Storage.fruits.clear();
    }

    @Test
    void apply_ValidTransaction_ShouldUpdateQuantityAndReturnUpdatedValue() {
        Fruit apple = new Fruit("apple");
        FruitTransaction transaction = new FruitTransaction("BALANCE", apple, 10);

        BalanceOperationHandler handler = new BalanceOperationHandler();

        int updatedQuantity = handler.apply(transaction);

        int expectedQuantity = 10;
        int actualQuantity = Storage.fruits.get(apple);
        assertEquals(expectedQuantity, actualQuantity);

        assertEquals(expectedQuantity, updatedQuantity);
    }

    @Test
    void apply_ValidTransactionWithExistingFruit_ShouldUpdateQuantityAndReturnUpdatedValue() {
        Fruit apple = new Fruit("apple");
        FruitTransaction transaction = new FruitTransaction("BALANCE", apple, 10);

        Storage.fruits.put(apple, 5);

        BalanceOperationHandler handler = new BalanceOperationHandler();

        int updatedQuantity = handler.apply(transaction);

        int expectedQuantity = 10;
        int actualQuantity = Storage.fruits.get(apple);
        assertEquals(expectedQuantity, actualQuantity);

        assertEquals(expectedQuantity, updatedQuantity);
    }
}