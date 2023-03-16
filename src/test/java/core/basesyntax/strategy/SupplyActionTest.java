package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.SupplyActionHandler;
import org.junit.Assert;
import org.junit.Test;

public class SupplyActionTest {
    @Test
    public void supplyAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        10));
        new SupplyActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        Assert.assertEquals(15L, (long) Storage.getFruits().get("apple"));
    }

    @Test
    public void supplyAction_supplyingNonExistent_ok() {
        Storage.getFruits().clear();
        new SupplyActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        Assert.assertEquals(5L, (long) Storage.getFruits().get("apple"));
    }
}
