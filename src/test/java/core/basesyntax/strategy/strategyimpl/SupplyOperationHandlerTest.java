package core.basesyntax.strategy.strategyimpl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.impl.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SupplyOperationHandlerTest {
    private static final Fruit APPLE = new Fruit("apple");
    private FruitDao fruitDao;
    private OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        operationHandler = new SupplyOperationHandler(fruitDao);
        Storage.fruits.put(APPLE, 100);
    }

    @Test
    public void handle_supplyOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, APPLE, 50);
        operationHandler.handle(fruitTransaction);
        int expected = 150;
        int actual = Storage.fruits.get(APPLE);
        Assert.assertEquals(expected, actual);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
