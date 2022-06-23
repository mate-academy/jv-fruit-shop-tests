package core.basesyntax.service.actiontype;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.ActionsDao;
import core.basesyntax.dao.ActionsDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.fruit.FruitTransaction;
import org.junit.Test;

public class ActionStrategySupplierTest {
    private static ActionsDao actionsDao;

    @Test
    public void strategySupplier_correctCalculating_ok() {
        actionsDao = new ActionsDaoImpl(Storage.data);
        actionsDao.add("orange", 10);
        ActionStrategySupplier actionStrategySupplier = new ActionStrategySupplier(actionsDao);
        int actual = actionStrategySupplier.getNewValue(new FruitTransaction("s", "orange", 50));
        assertEquals(60, actual);
    }
}
