package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.dto.FruitTransaction;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {

    private Fruit apple;
    private PurchaseOperationHandler purchaseHandler;
    private FruitTransaction transaction;

    @BeforeEach
    public void setUp() {
        Storage.fruits.clear();
        purchaseHandler = new PurchaseOperationHandler();
        apple = new Fruit("apple");
        transaction = new FruitTransaction("p", apple, 5);
    }

    @Test
    public void apply_PurchaseExistingFruit_ShouldReduceQuantityAndReturnNewValue() {
        Storage.fruits.put(apple, 10);
        int result = purchaseHandler.apply(transaction);
        assertEquals(5, result);
        assertEquals(5, Storage.fruits.get(apple));
    }

    @Test
    void apply_PurchaseMoreThanAvailableQuantity_ShouldNotGoNegativeAndReturnExistingValue() {
        Storage.fruits.put(apple, 3);
        int result = purchaseHandler.apply(transaction);

        assertEquals(3, result);
        assertEquals(3, Storage.fruits.get(apple));
    }
}
