package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerImplTest {
    private static final String KEY_APPLE = "apple";
    private static AddOperationHandlerImpl addOperationHandler;

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        fruitDao.put(KEY_APPLE,10);
        addOperationHandler = new AddOperationHandlerImpl(fruitDao);
    }

    @Test
    public void operationWithFruitTransaction_isOk() {
        Map<String,Integer> expectedMap = new HashMap<>();
        expectedMap.put(KEY_APPLE,20);
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,KEY_APPLE,10);
        addOperationHandler.operationWithFruitTransaction(fruitTransaction);
        assertEquals(expectedMap, FruitStorage.storage);
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
