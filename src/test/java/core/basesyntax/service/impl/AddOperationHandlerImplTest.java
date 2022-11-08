package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import org.junit.After;
import org.junit.Test;

public class AddOperationHandlerImplTest {
    private static final String KEY_APPLE = "apple";

    @Test
    public void operationWithFruitTransaction_isOk() {
        FruitDao fruitDao = new FruitDaoImpl();
        FruitTransaction fruitTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY,KEY_APPLE,10);
        AddOperationHandlerImpl addOperationHandler =
                new AddOperationHandlerImpl(fruitDao);
        addOperationHandler.operationWithFruitTransaction(fruitTransaction);
        Integer expected = 10;
        assertEquals(expected, fruitDao.get(KEY_APPLE));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
