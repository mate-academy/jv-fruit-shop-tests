package core.basesyntax.service;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategyImpl;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import static org.junit.Assert.assertEquals;

public class SubtractOperationHandlerTest {
    private static final String KEY_APPLE = "apple";

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap
                = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new AddOperationHandler(fruitDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE, new SubtractOperationHandler(fruitDao));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, KEY_APPLE, 10));
        list.add(new FruitTransaction(FruitTransaction.Operation.PURCHASE, KEY_APPLE, 10));
        TransactionService transactionService =
                new TransactionServiceImpl(new OperationStrategyImpl(handlerMap));
        transactionService.process(list);
    }

    @Test
    public void subtractOperationHandle_Ok() {
        Integer expected = 0;
        assertEquals(expected, Storage.fruits.get(KEY_APPLE));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
