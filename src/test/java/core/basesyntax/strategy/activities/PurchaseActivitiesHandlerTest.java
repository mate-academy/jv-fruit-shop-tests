package core.basesyntax.strategy.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.exception.StrategyException;
import org.junit.Before;
import org.junit.Test;

public class PurchaseActivitiesHandlerTest {
    private ActivitiesHandler purchaseActivities;
    private StorageDao storage;

    @Before
    public void beforeEachTest() {
        purchaseActivities = new PurchaseActivitiesHandler();
        storage = new StorageDaoImpl();
    }

    @Test
    public void handler_change_Ok() {
        storage.add("banana", 100);
        purchaseActivities.changeFruit("banana", 45);
        int actual = storage.get("banana");
        int expected = 55;
        assertEquals(expected, actual);
    }

    @Test(expected = StrategyException.class)
    public void handler_fruitNull_notOk() {
        purchaseActivities.changeFruit(null,56);
    }

    @Test(expected = StrategyException.class)
    public void handler_amountNull_notOk() {
        purchaseActivities.changeFruit("banana",null);
    }

    @Test(expected = StrategyException.class)
    public void handler_negativeAmount_notOk() {
        purchaseActivities.changeFruit("apple",-56);
    }

    @Test(expected = StrategyException.class)
    public void handler_excessiveAmount_notOk() {
        storage.add("banana", 50);
        purchaseActivities.changeFruit("banana", 100);
    }
}
