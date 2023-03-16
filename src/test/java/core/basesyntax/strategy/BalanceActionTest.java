package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import org.junit.Assert;
import org.junit.Test;

public class BalanceActionTest {
    @Test
    public void balanceAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        Assert.assertEquals(10L, (long) Storage.getFruits().get("banana"));
    }
}
