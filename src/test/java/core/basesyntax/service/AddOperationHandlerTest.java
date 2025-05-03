package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AddOperationHandlerTest {
    private static final String KEY_APPLE = "apple";

    @BeforeClass
    public static void beforeClass() {
        FruitDao fruitDao = new FruitDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap
                = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE, new AddOperationHandler(fruitDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY, new AddOperationHandler(fruitDao));
        handlerMap.put(FruitTransaction.Operation.RETURN, new AddOperationHandler(fruitDao));
        List<FruitTransaction> list = new ArrayList<>();
        list.add(new FruitTransaction(FruitTransaction.Operation.BALANCE, KEY_APPLE, 10));
        list.add(new FruitTransaction(FruitTransaction.Operation.SUPPLY, KEY_APPLE, 10));
        list.add(new FruitTransaction(FruitTransaction.Operation.RETURN, KEY_APPLE, 10));
        TransactionService transactionService =
                new TransactionServiceImpl(new OperationStrategyImpl(handlerMap));
        transactionService.process(list);
    }

    @Test
    public void handle_Ok() {
        Integer expected = 30;
        assertEquals(expected, Storage.fruits.get(KEY_APPLE));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
