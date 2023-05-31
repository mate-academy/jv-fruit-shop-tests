package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.ReturnOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerTest {
    private static final int APPLE_QUANTITY = 100;
    private static OperationHandler returnOperationHandler;

    @BeforeClass
    public static void setUp() {
        returnOperationHandler = new ReturnOperationHandler();
        FruitStorage.fruitsStorage.put("apple", 100);
    }

    @Test
    public void calculateValueByOperation_checkIfCorrect_isOk() {
        Integer expected = FruitStorage.fruitsStorage.get("apple") + APPLE_QUANTITY;

        returnOperationHandler.calculateValueByOperation(new FruitTransaction(
                Operation.RETURN, "apple", APPLE_QUANTITY));

        assertEquals(expected, FruitStorage.fruitsStorage.get("apple"));
    }
}
