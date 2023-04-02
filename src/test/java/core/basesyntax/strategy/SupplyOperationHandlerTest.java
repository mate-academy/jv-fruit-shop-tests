package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final int APPLE_QUANTITY = 100;
    private static OperationHandler supplyOperationHandler;

    @BeforeClass
    public static void setUp() {
        supplyOperationHandler = new SupplyOperationHandler();
        FruitStorage.fruitsStorage.put("apple", 100);
    }

    @Test
    public void calculateValueByOperation() {
        Integer expected = FruitStorage.fruitsStorage.get("apple") + APPLE_QUANTITY;

        supplyOperationHandler.calculateValueByOperation(new FruitTransaction(
                Operation.SUPPLY, "apple", APPLE_QUANTITY));

        assertEquals(expected, FruitStorage.fruitsStorage.get("apple"));
    }
}
