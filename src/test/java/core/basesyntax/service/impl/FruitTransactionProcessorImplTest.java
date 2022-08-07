package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.operation.AdditionalOperation;
import core.basesyntax.strategy.operation.OperationHandler;
import core.basesyntax.strategy.operation.SubtractionOperation;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionProcessorImplTest {
    private static FruitDao fruitDao;
    private static FruitTransactionProcessor processor;
    private static List<FruitTransaction> fruitTransactions;
    private static Map<String, Integer> expected;
    private static Storage storage;

    @BeforeClass
    public static void beforeClass() {
        storage = new Storage();
        fruitDao = new FruitDaoImpl(storage);
        Map<FruitTransaction.Operation, OperationHandler> handlerMap = new HashMap<>();
        handlerMap.put(FruitTransaction.Operation.BALANCE,
                new AdditionalOperation(fruitDao));
        handlerMap.put(FruitTransaction.Operation.SUPPLY,
                new AdditionalOperation(fruitDao));
        handlerMap.put(FruitTransaction.Operation.PURCHASE,
                new SubtractionOperation(fruitDao));
        handlerMap.put(FruitTransaction.Operation.RETURN,
                new AdditionalOperation(fruitDao));
        OperationStrategy operationStrategy = new OperationStrategy(handlerMap);
        processor = new FruitTransactionProcessorImpl(operationStrategy);
        fruitTransactions = new ArrayList<>(List.of(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, "banana",20),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, "banana",100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, "banana",13),
                new FruitTransaction(FruitTransaction.Operation.RETURN, "apple",10)));
        expected = new HashMap<>();
        expected.put("banana", 107);
        expected.put("apple", 10);
    }

    @Test
    public void processWork_Ok() {
        processor.process(fruitTransactions);
        String actual = storage.getFruitStorage().toString();
        assertEquals("Test failed! The result of the method is a string "
                + expected.toString() + " but was: " + actual, expected.toString(), actual);
    }

    @Test(expected = RuntimeException.class)
    public void processWork_NotOk() {
        processor.process(null);
    }

    @AfterClass
    public static void afterClass() {
        storage.getFruitStorage().clear();
    }
}
