package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import org.junit.Before;
import org.junit.Test;

public class BalanceActionTest {

    @Before
    public void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    public void apply_normalInput_ok() {
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        assertEquals(10L, (long) Storage.getFruits().get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void apply_negativeBalance_notOk() {
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "fruit",
                        -10));
    }
}
