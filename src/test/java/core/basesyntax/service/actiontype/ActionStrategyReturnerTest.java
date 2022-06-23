package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.FruitTransaction;
import org.junit.Test;

public class ActionStrategyReturnerTest {
    private static ActionsDao actionsDao;

    @Test
    public void strategyReturner_correctCalculating_ok() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        actionsDao.add("orange", 10);
        ActionStrategyReturner actionStrategyReturner = new ActionStrategyReturner(actionsDao);
        int actual = actionStrategyReturner.getNewValue(new FruitTransaction("r", "orange", 50));
        assertEquals(60, actual);
    }
}

