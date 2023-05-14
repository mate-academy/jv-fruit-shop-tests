package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.ProductStorage;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static BalanceOperationHandler balanceOperationHandler;
    private static FruitTransaction fruitTransaction;

    @After
    public void clearStorage() {
        ProductStorage.products.clear();
    }

    @BeforeClass
    public static void beforeClass() {
        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test(expected = RuntimeException.class)
    public void apply_nullTransaction_notOk() {
        balanceOperationHandler.apply(null);
    }

    @Test
    public void apply_correctTransaction_ok() {
        fruitTransaction = new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 50);
        balanceOperationHandler.apply(fruitTransaction);
        Integer expectedQuantity = 50;
        Integer actualQuantity = ProductStorage.products.get(fruitTransaction.getFruit());
        assertEquals(expectedQuantity, actualQuantity);
    }
}
