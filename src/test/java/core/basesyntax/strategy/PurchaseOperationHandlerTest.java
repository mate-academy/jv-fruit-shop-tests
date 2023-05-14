package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.ProductStorage;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static PurchaseOperationHandler purchaseOperationHandler;
    private static FruitTransaction fruitTransaction;

    @After
    public void clearStorage() {
        ProductStorage.products.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        purchaseOperationHandler = new PurchaseOperationHandler();
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullTransaction_notOk() {
        purchaseOperationHandler.apply(null);
    }

    @Test
    public void apply_correctTransaction_ok() {
        ProductStorage.products.put("banana", 10);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10);
        purchaseOperationHandler.apply(fruitTransaction);
        Integer expectedQuantity = 0;
        Integer actualQuantity = ProductStorage.products.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test(expected = RuntimeException.class)
    public void apply_purchaseMoreThanExist_notOk() {
        ProductStorage.products.put("banana", 9);
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.PURCHASE,
                "banana", 10);
        purchaseOperationHandler.apply(fruitTransaction);
        Integer expectedQuantity = 0;
        Integer actualQuantity = ProductStorage.products.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }
}
