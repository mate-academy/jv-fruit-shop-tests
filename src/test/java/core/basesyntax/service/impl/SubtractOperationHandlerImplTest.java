package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.service.TransactionService;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class SubtractOperationHandlerImplTest {
    private static final String KEY_APPLE = "apple";

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new AddOperationHandlerImpl(fruitDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new SubtractOperationHandlerImpl(fruitDao));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,KEY_APPLE,10));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE,KEY_APPLE,10));
        TransactionService transactionService =
                new TransactionServiceImpl(new OperationStrategyImpl(handlerMap));
        transactionService.doOperationService(list);
    }

    @Test
    public void operationWithFruitTransaction_IsOk() {
        Integer expected = 0;
        assertEquals(expected, FruitStorage.storage.get(KEY_APPLE));
    }

    @After
    public void tearDown() {
        FruitStorage.storage.clear();
    }
}
