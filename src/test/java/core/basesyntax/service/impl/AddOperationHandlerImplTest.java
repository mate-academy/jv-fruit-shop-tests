package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationHandler;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerImplTest {
    private static final String KEY_APPLE = "apple";

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new AddOperationHandlerImpl(fruitDao));
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandlerImpl(fruitDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,new ReturnOperationHandlerImpl(fruitDao));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY,KEY_APPLE,10));
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE,KEY_APPLE,10));
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN,KEY_APPLE,10));
        TransactionServiceImpl transactionService =
                new TransactionServiceImpl(new OperationStrategyImpl(handlerMap));
        transactionService.doOperationService(list);
    }

    @Test
    public void operationWithFruitTransactionIs_Ok() {
        Integer expected = 30;
        assertEquals(expected, FruitStorage.storage.get(KEY_APPLE));
    }

    @AfterClass
    public static void afterClass() {
        FruitStorage.storage.clear();
    }
}
