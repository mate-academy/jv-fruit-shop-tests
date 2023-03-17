package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class BalanceActionTest {
    private static ActionHandler actionHandler;

    @BeforeClass
    public static void initialize() {
        actionHandler = new BalanceActionHandler();
    }

    @Before
    public void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    public void apply_normalInput_ok() {
        actionHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        assertEquals(10L, (long) Storage.getFruits().get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeBalance_notOk() {
        actionHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "fruit",
                        -10));
    }
}
