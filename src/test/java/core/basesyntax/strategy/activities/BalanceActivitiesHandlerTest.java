package core.basesyntax.strategy.activities;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.dao.StorageDao;
import core.basesyntax.db.dao.StorageDaoImpl;
import core.basesyntax.exception.StrategyException;
import org.junit.Before;
import org.junit.Test;

public class BalanceActivitiesHandlerTest {
    private ActivitiesHandler balanceActivities;
    private StorageDao storage;

    @Before
    public void beforeEachTest() {
        balanceActivities = new BalanceActivitiesHandler();
        storage = new StorageDaoImpl();
    }

    @Test
    public void handler_change_Ok() {
        storage.add("banana", 100);
        balanceActivities.changeFruit("banana", 45);
        int actual = storage.get("banana");
        int expected = 145;
        assertEquals(expected, actual);
    }

    @Test(expected = StrategyException.class)
    public void handler_fruitNull_notOk() {
        balanceActivities.changeFruit(null,56);
    }

    @Test(expected = StrategyException.class)
    public void handler_amountNull_notOk() {
        balanceActivities.changeFruit("banana",null);
    }

    @Test(expected = StrategyException.class)
    public void handler_negativeAmount_notOk() {
        balanceActivities.changeFruit("apple",-56);
    }
}
