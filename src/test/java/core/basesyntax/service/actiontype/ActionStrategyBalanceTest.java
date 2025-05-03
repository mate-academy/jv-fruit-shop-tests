package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.FruitTransaction;
import org.junit.Test;

public class ActionStrategyBalanceTest {
    private static ActionsDao actionsDao;

    @Test
    public void strategyBalance_correctCalculating_ok() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        actionsDao.add("orange", 10);
        ActionStrategyBalance actionStrategyBalance = new ActionStrategyBalance(actionsDao);
        int actual = actionStrategyBalance.getNewValue(new FruitTransaction("b", "orange", 50));
        assertEquals(50, actual);
    }
}
