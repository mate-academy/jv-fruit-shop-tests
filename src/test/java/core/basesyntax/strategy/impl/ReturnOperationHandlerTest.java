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

public class ReturnOperationHandlerTest {
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
        handler = new ReturnOperationHandler(fruitStorageDao);
    }

    @Test
    public void returnHandler_validWork_Ok() {
        handler.handleOperation("apple", 20);
        int expected = 40;
        int actual = fruitStorageDao.getQuantity(new Fruit("apple"));
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void returnHandler_noSuchFruit_Ok() throws RuntimeException {
        thrown.expectMessage("No such fruit in shop");
        thrown.expect(RuntimeException.class);
        handler.handleOperation("not and apple", 20);

    }
}
