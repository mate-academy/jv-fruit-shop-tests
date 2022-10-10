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

public class PurchaseOperationHandlerTest {
    private static final Fruit APPLE = new Fruit("apple");
    private FruitDao fruitDao;
    private OperationHandler operationHandler;

    @Before
    public void setUp() throws Exception {
        fruitDao = new FruitDaoImpl();
        operationHandler = new PurchaseOperationHandler(fruitDao);
        Storage.fruits.put(APPLE, 100);
    }

    @Test
    public void handle_purchaseOperation_Ok() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, 50);
        operationHandler.handle(fruitTransaction);
        int expected = 50;
        int actual = Storage.fruits.get(APPLE);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void handle_purchaseValueMoreThanValueInStorage_NotOk() {
        FruitTransaction fruitTransaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, APPLE, 150);
        operationHandler.handle(fruitTransaction);
    }

    @After
    public void tearDown() throws Exception {
        Storage.fruits.clear();
    }
}
