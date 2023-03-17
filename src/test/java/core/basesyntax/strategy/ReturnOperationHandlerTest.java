package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.ProductStorage;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static ReturnOperationHandler returnOperationHandler;
    private static FruitTransaction fruitTransaction;

    @After
    public void clearStorage() {
        ProductStorage.products.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        returnOperationHandler = new ReturnOperationHandler();
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullTransaction_notOk() {
        returnOperationHandler.apply(null);
    }

    @Test
    public void apply_correctTransaction_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        returnOperationHandler.apply(fruitTransaction);
        Integer expectedQuantity = 50;
        Integer actualQuantity = ProductStorage.products.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }
}
