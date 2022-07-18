package core.basesyntax.strategy.handler.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class AdditionOperationHandlerTest {
    private static final int EXPECTED = 119;
    private static final String BANANA_KEY = "banana";

    @BeforeClass
    public static void beforeClass() {

        FruitShopDao fruitShopDao = new FruitShopDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new AdditionOperationHandler(fruitShopDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new AdditionOperationHandler(fruitShopDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new SubtractionOperationHandler(fruitShopDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new AdditionOperationHandler(fruitShopDao));
        List<FruitTransaction> list = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA_KEY, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA_KEY, 11),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA_KEY, 10),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA_KEY, 20)
        ));
        OperationStrategy operationStrategy = new OperationStrategy(handlerMap);
        TransactionProcessor transactionProcessor = new TransactionProcessorImpl(operationStrategy);
        transactionProcessor.process(list);
    }

    @Test
    public void handle() {
        assertEquals(EXPECTED, Storage.fruits.get(BANANA_KEY));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
