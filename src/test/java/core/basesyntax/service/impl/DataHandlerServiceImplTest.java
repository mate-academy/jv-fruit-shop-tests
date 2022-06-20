package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.DataHandlerService;
import core.basesyntax.service.processing.BalanceProcessing;
import core.basesyntax.service.processing.OperationProcessing;
import core.basesyntax.service.processing.PurchaseProcessing;
import core.basesyntax.service.processing.ReturnProcessing;
import core.basesyntax.service.processing.SupplyProcessing;
import core.basesyntax.strategy.OperationProcessingStrategy;
import core.basesyntax.strategy.impl.OperationProcessingStrategyImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class DataHandlerServiceImplTest {
    private static DataHandlerService handlerService;
    private static OperationProcessingStrategy operationProcessingStrategy;
    private static Map<FruitTransaction.Operation, OperationProcessing> operationProcessingMap;
    private static FruitsDao fruitsDao;
    private static Map<String, Integer> storageWithFruits;
    private List<FruitTransaction> fruitTransactionsList;

    @BeforeClass
    public static void beforeClass() {
        storageWithFruits = new HashMap<>();
        fruitsDao = new FruitsDaoImpl(storageWithFruits);
        operationProcessingMap =
                new HashMap<>();
        operationProcessingMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceProcessing(fruitsDao));
        operationProcessingMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseProcessing(fruitsDao));
        operationProcessingMap.put(FruitTransaction.Operation.RETURN,
                new ReturnProcessing(fruitsDao));
        operationProcessingMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyProcessing(fruitsDao));
        operationProcessingStrategy = new OperationProcessingStrategyImpl(operationProcessingMap);
        handlerService = new DataHandlerServiceImpl(operationProcessingStrategy);
    }

    @Before
    public void setUp() {
        fruitTransactionsList = new ArrayList<>();
        fruitTransactionsList
                .add(FruitTransaction.of(FruitTransaction.Operation.BALANCE,
                        "banana", 200));
        fruitTransactionsList
                .add(FruitTransaction.of(FruitTransaction.Operation.RETURN,
                        "banana", 10));
        fruitTransactionsList
                .add(FruitTransaction.of(FruitTransaction.Operation.PURCHASE,
                        "banana", 20));
        fruitTransactionsList
                .add(FruitTransaction.of(FruitTransaction.Operation.SUPPLY,
                        "banana", 50));
    }

    @Test(expected = RuntimeException.class)
    public void handleData_nullListOfTransactions_notOk() {
        handlerService.handleData(null);
    }

    @Test
    public void handleData_normalValue_ok() {
        boolean actual = handlerService.handleData(fruitTransactionsList);
        Assert.assertTrue(actual);
    }
}
