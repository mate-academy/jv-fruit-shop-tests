package core.basesyntax.operation;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.operation.BalanceOperationHandler;
import core.basesyntax.strorage.FruitStorage;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceOperationHandlerTest {
    private static OperationHandler balanceHandler;

    @BeforeClass
    public static void setUp() {
        balanceHandler = new BalanceOperationHandler();
    }

    @Test
    public void apply_validCase_ok() {
        balanceHandler.apply(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "banana", 20));
        balanceHandler.apply(new FruitTransaction(FruitTransaction.Operation.BALANCE,
                "apple", 80));
        int actualApple = FruitStorage.fruits.get("apple");
        int expectedBanana = 20;
        int expectedApple = 80;
        int actualBanana = FruitStorage.fruits.get("banana");
        assertEquals("Not valid banana quantity", actualBanana, expectedBanana);
        assertEquals("Not valid apple quantity",actualApple, expectedApple);
    }

    @Test(expected = NullPointerException.class)
    public void apply_nullValue() {
        balanceHandler.apply(null);
    }
}
