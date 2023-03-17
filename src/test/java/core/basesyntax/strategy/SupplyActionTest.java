package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.storage.Storage;
import core.basesyntax.strategy.actions.SupplyActionHandler;
import org.junit.Before;
import org.junit.Test;

public class SupplyActionTest {

    @Before
    public void clearStorage() {
        Storage.getFruits().clear();
    }

    @Test
    public void apply_normalInput_ok() {
        Storage.put("apple", 10);
        new SupplyActionHandler().apply(
                new FruitTransaction(FruitTransaction.Operation.BALANCE,
                        "apple",
                        5));
        assertEquals(15L, (long) Storage.getFruits().get("apple"));
    }

    @Test
    public void apply_supplyingNonExistent_ok() {
        Storage.put("apple", 5);
        assertEquals(5L, (long) Storage.getFruits().get("apple"));
    }
}
