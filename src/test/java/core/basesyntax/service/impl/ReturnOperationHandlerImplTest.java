package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReturnOperationHandlerImplTest {
    private static final String KEY_APPLE = "apple";

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.RETURN,new ReturnOperationHandlerImpl(fruitDao));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN,KEY_APPLE,10));
        ReturnOperationHandlerImpl returnOperationHandler =
                new ReturnOperationHandlerImpl(fruitDao);
        returnOperationHandler.operationWithFruitTransaction(list.get(0));
    }

    @Test
    public void operationWithFruitTransaction_IsOk() {
        Integer expected = 10;
        assertEquals(expected, FruitStorage.storage.get(KEY_APPLE));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
