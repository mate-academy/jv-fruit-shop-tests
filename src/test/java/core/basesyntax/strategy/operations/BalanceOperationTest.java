package core.basesyntax.strategy.operations;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.StorageFruits;
import core.basesyntax.exceptions.WrongDataException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class BalanceOperationTest {
    private BalanceOperation balanceOperation;
    private FruitDao fruitDao;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        balanceOperation = new BalanceOperation(fruitDao);
    }

    @Test (expected = WrongDataException.class)
    public void apply_dailyTransactionNull_notOk() {
        balanceOperation.apply(null);
    }

    @After
    public void tearDown() throws Exception {
        StorageFruits.fruits.clear();
    }
}
