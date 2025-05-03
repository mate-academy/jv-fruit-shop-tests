package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.ProductStorage;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static SupplyOperationHandler supplyOperationHandler;
    private static FruitTransaction fruitTransaction;

    @After
    public void clearStorage() {
        ProductStorage.products.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        supplyOperationHandler = new SupplyOperationHandler();
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullTransaction_notOk() {
        supplyOperationHandler.apply(null);
    }

    @Test
    public void apply_correctTransaction_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.SUPPLY,
                "banana", 50);
        supplyOperationHandler.apply(fruitTransaction);
        Integer expectedQuantity = 50;
        Integer actualQuantity = ProductStorage.products.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }
}
