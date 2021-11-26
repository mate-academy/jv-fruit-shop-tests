package core.basesyntax.service.operationhandler.impl;

import core.basesyntax.bd.Storage;
import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.Operation;
import core.basesyntax.model.TransactionDto;
import core.basesyntax.service.operationhandler.OperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class RemovingOperationHandlerTest {
    private static FruitDao fruitDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new RemovingOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
        Storage.fruitStorage.put(new Fruit("banana"), 90);
        Storage.fruitStorage.put(new Fruit("apple"), 112);
    }

    @Test
    public void removeFromStorage_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 60);
        expected.put(new Fruit("apple"), 90);
        operationHandler.apply(new TransactionDto(Operation.PURCHASE, new Fruit("banana"), 30));
        operationHandler.apply(new TransactionDto(Operation.PURCHASE, new Fruit("apple"), 22));
        Map<Fruit, Integer> actual = fruitDao.getAll();
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void removeFromStorageMoreThanHas_NotOk() {
        operationHandler.apply(new TransactionDto(Operation.PURCHASE, new Fruit("banana"), 30));
        operationHandler.apply(new TransactionDto(Operation.PURCHASE, new Fruit("apple"), 300));
    }
}
