package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.PurchaseActionHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class PurchaseActionTest {
    private static ActionHandler actionHandler;

    @BeforeClass
    public static void initialize() {
        actionHandler = new PurchaseActionHandler();
    }

    @Before
    public void beforeEach() {
        Storage.getFruits().clear();
        Storage.put("banana", 10);
    }

    @Test
    public void apply_normalInput_ok() {

        actionHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        5));
        assertEquals(5L, (long) Storage.getFruits().get("banana"));
    }

    @Test(expected = RuntimeException.class)
    public void apply_nonExistentFruit_notOk() {
        actionHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
    }

    @Test(expected = RuntimeException.class)
    public void purchaseAction_purchaseTooMuch_notOk() {
        actionHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        15));
    }
}
