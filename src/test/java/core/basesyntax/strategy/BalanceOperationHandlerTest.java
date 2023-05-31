package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.enums.Operation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static final int APPLE_QUANTITY = 100;
    private static OperationHandler balanceOperationHandler;

    @BeforeClass
    public static void setUp() {
        FruitStorage.fruitsStorage.put("apple", APPLE_QUANTITY);

        balanceOperationHandler = new BalanceOperationHandler();
    }

    @Test
    public void calculateValueByOperation_checkIfCorrect_isOk() {
        Integer expected = 150;
        balanceOperationHandler.calculateValueByOperation(new FruitTransaction(Operation.BALANCE,
                "apple", expected));

        assertEquals(expected, FruitStorage.fruitsStorage.get("apple"));
    }
}
