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

public class BalanceOperationHandlerImplTest {
    private static final String KEY_APPLE = "apple";

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(fruitDao));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,KEY_APPLE,10));
        BalanceOperationHandlerImpl balanceOperationHandler =
                new BalanceOperationHandlerImpl(fruitDao);
        balanceOperationHandler.operationWithFruitTransaction(list.get(0));
    }

    @Test
    public void operationWithFruitTransaction_isOk() {
        Integer expected = 10;
        assertEquals(expected, FruitStorage.storage.get(KEY_APPLE));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
