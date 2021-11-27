package core.basesyntax.strategy.impl;

import core.basesyntax.dao.FruitStorageDao;
import core.basesyntax.dao.FruitStorageDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.strategy.OperationHandler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PurchaseOperationHandlerTest {
    private static OperationHandler handler;
    private static FruitStorageDao fruitStorageDao;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() {
        fruitStorageDao = new FruitStorageDaoImpl();
    }

    @Before
    public void beforeEach() {
        fruitStorageDao.getAll().clear();
        fruitStorageDao.add(new Fruit("apple"), 20);
        handler = new PurchaseOperationHandler(fruitStorageDao);
    }

    @Test
    public void purchaseHandler_validWork_Ok() {
        handler.handleOperation("apple",20);
        int expected = 0;
        int actual = fruitStorageDao.getQuantity(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void purchaseHandler_notEnoughQuantity_Ok() throws RuntimeException {
        thrown.expectMessage("Not enough fruits to buy that amount");
        thrown.expect(RuntimeException.class);
        handler.handleOperation("apple",21);
    }
}
