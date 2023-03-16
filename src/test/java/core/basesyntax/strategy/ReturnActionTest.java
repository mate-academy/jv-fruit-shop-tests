package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.ReturnActionHandler;
import org.junit.Test;

public class ReturnActionTest {
    @Test
    public void returnAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new ReturnActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        5));
        assertEquals(15L, (long) Storage.getFruits().get("banana"));
    }

    @Test
    public void returnAction_supplyingNonExistent_ok() {
        Storage.getFruits().clear();
        new ReturnActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        assertEquals(5L, (long) Storage.getFruits().get("apple"));
    }
}
