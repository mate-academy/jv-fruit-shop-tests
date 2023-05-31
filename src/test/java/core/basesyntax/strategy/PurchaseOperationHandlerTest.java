package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseOperationHandlerTest {
    private static final int APPLE_QUANTITY = 150;
    private static OperationHandler purchaseOperationHandler;

    @BeforeClass
    public static void setUp() {
        purchaseOperationHandler = new PurchaseOperationHandler();

        FruitStorage.fruitsStorage.put("apple", 400);
    }

    @Test
    public void calculateValueByOperation_checkIfCorrect_isOk() {
        Integer expected = FruitStorage.fruitsStorage.get("apple") - APPLE_QUANTITY;

        purchaseOperationHandler.calculateValueByOperation(new FruitTransaction(
                Operation.PURCHASE, "apple", APPLE_QUANTITY));

        assertEquals(expected, FruitStorage.fruitsStorage.get("apple"));
    }
}
