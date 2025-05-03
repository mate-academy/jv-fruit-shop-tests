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

public class AddingOperationHandlerTest {
    private static FruitDao fruitDao;
    private static OperationHandler operationHandler;

    @BeforeClass
    public static void beforeClass() {
        fruitDao = new FruitDaoImpl();
        operationHandler = new AddingOperationHandler(fruitDao);
    }

    @Before
    public void setUp() {
        Storage.fruitStorage.clear();
    }

    @Test
    public void addInStorage_Ok() {
        Map<Fruit, Integer> expected = new HashMap<>();
        expected.put(new Fruit("banana"), 90);
        expected.put(new Fruit("apple"), 112);
        operationHandler.apply(new TransactionDto(Operation.BALANCE, new Fruit("banana"), 20));
        operationHandler.apply(new TransactionDto(Operation.BALANCE, new Fruit("apple"), 100));
        operationHandler.apply(new TransactionDto(Operation.SUPPLY, new Fruit("banana"), 70));
        operationHandler.apply(new TransactionDto(Operation.RETURN, new Fruit("apple"), 12));
        Map<Fruit, Integer> actual = fruitDao.getAll();
        Assert.assertEquals(expected, actual);
    }
}
