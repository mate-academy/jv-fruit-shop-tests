package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.BalanceActionHandler;
import core.basesyntax.strategy.actions.PurchaseActionHandler;
import org.junit.Test;

public class PurchaseActionTest {
    @Test
    public void purchaseAction_ok() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new PurchaseActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        5));
        assertEquals(5L, (long) Storage.getFruits().get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseAction_nonExistentFruit_notOk() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new PurchaseActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseAction_purchaseTooMuch_notOk() {
        Storage.getFruits().clear();
        new BalanceActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        10));
        new PurchaseActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        15));
    }
}
