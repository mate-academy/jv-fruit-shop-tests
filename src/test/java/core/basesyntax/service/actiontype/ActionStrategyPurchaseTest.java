package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.FruitTransaction;
import org.junit.Test;

public class ActionStrategyPurchaseTest {
    private static ActionsDao actionsDao;

    @Test
    public void strategyPurchase_correctCalculating_ok() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        actionsDao.add("orange", 10);
        ActionStrategyPurchase actionStrategyPurchase = new ActionStrategyPurchase(actionsDao);
        int actual = actionStrategyPurchase.getNewValue(new FruitTransaction("p", "orange", 5));
        assertEquals(5, actual);
    }
}
