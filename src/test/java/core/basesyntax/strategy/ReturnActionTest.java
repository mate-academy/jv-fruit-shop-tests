package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.ActionHandler;
import core.basesyntax.strategy.actions.ReturnActionHandler;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnActionTest {
    private static ActionHandler actionHandler;

    @BeforeClass
    public static void initialize() {
        actionHandler = new ReturnActionHandler();
    }

    @Before
    public void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    public void apply_normalInput_ok() {
        Storage.put("banana", 10);
        actionHandler.apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "banana",
                        5));
        assertEquals(15L, (long) Storage.getFruits().get("banana"));
    }

    @Test
    public void apply_supplyingNonExistent_ok() {
        Storage.put("apple", 5);
        assertEquals(5L, (long) Storage.getFruits().get("apple"));
    }
}
