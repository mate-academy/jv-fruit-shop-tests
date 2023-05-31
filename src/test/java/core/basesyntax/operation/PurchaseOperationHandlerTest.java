package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.exception.ProductNotFoundException;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.operation.PurchaseOperationHandler;
import core.basesyntax.strorage.FruitStorage;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static OperationHandler purchaseHandler;

    @BeforeClass
    public static void setUp() {
        purchaseHandler = new PurchaseOperationHandler();
    }

    @Before
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void apply_valid_ok() {
        FruitStorage.fruits.put("banana", 100);
        purchaseHandler.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 80));
        int actual = FruitStorage.fruits.get("banana");
        int expected = 20;
        assertEquals("Buyers purchased the product, balance: ", expected, actual);
    }

    @Test(expected = ProductNotFoundException.class)
    public void apply_notValidQuantity_ok() {
        FruitStorage.fruits.put("banana", 50);
        purchaseHandler.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 80));
    }

    @Test(expected = ProductNotFoundException.class)
    public void apply_notFoundFruit_ok() {
        FruitStorage.fruits.put("banana", 50);
        purchaseHandler.apply(new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 80));
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullValue_notOk() {
        purchaseHandler.apply(null);
    }
}
