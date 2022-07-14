package core.basesyntax.service.impl;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.dao.FruitShopDao;
import core.basesyntax.dao.FruitShopDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.impl.AdditionOperationHandler;
import core.basesyntax.strategy.handler.impl.SubtractionOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static final String FIRST_KEY = "banana";
    private static final String SECOND_KEY = "apple";
    private static FruitShopDao fruitShopDao;
    private static TransactionProcessor transactionProcessor;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String, Integer> expected;

    @BeforeClass
    public static void beforeClass() {
        fruitShopDao = new FruitShopDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new AdditionOperationHandler(fruitShopDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new AdditionOperationHandler(fruitShopDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new SubtractionOperationHandler(fruitShopDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new AdditionOperationHandler(fruitShopDao));
        OperationStrategy operationStrategy = new OperationStrategy(handlerMap);
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
        fruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(BALANCE,FIRST_KEY,20),
                new FruitTransaction(SUPPLY,FIRST_KEY,100),
                new FruitTransaction(PURCHASE,FIRST_KEY,13),
                new FruitTransaction(RETURN,SECOND_KEY,10)));
        expected = new HashMap<>();
        expected.put(FIRST_KEY, 107);
        expected.put(SECOND_KEY, 10);
    }

    @Test
    public void process_Ok() {
        transactionProcessor.process(fruitTransactions);
        System.out.println(fruitShopDao.getAll().toString());
        assertEquals(expected.toString(), Storage.fruits.toString());
    }

    @Test
    public void process_NotOk() {
        assertThrows(NullPointerException.class,
                () -> transactionProcessor.process(null));
    }
}
