package core.basesyntax.impl;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.processing.BalanceProcessing;
import core.basesyntax.service.processing.OperationProcessing;
import core.basesyntax.service.processing.PurchaseProcessing;
import core.basesyntax.service.processing.ReturnProcessing;
import core.basesyntax.service.processing.SupplyProcessing;
import core.basesyntax.strategy.OperationProcessingStrategy;
import core.basesyntax.strategy.impl.OperationProcessingStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationProcessingStrategyImplTest {
    private static OperationProcessingStrategy operationProcessingStrategy;
    private static Map<FruitTransaction.Operation, OperationProcessing> operationProcessingMap;
    private static FruitsDao fruitsDao;
    private static Map<String, Integer> fruitsAtStorage;

    @BeforeClass
    public static void beforeClass() {
        fruitsAtStorage = new HashMap<>();
        fruitsDao = new FruitsDaoImpl(fruitsAtStorage);
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
    }

    @Test(expected = RuntimeException.class)
    public void get_inputValueIsNull_notOk() {
        operationProcessingStrategy.get(null);
    }

    @Test
    public void get_normalValueBalance_ok() {
        OperationProcessing actual =
                operationProcessingStrategy.get(FruitTransaction.Operation.BALANCE);
        OperationProcessing expected = new BalanceProcessing(fruitsDao);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_normalValuePurchase_ok() {
        OperationProcessing actual =
                operationProcessingStrategy.get(FruitTransaction.Operation.PURCHASE);
        OperationProcessing expected = new PurchaseProcessing(fruitsDao);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_normalValueReturn_ok() {
        OperationProcessing actual =
                operationProcessingStrategy.get(FruitTransaction.Operation.RETURN);
        OperationProcessing expected = new ReturnProcessing(fruitsDao);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_normalValueSupply_ok() {
        OperationProcessing actual =
                operationProcessingStrategy.get(FruitTransaction.Operation.SUPPLY);
        OperationProcessing expected = new SupplyProcessing(fruitsDao);
        Assert.assertEquals(expected, actual);
    }
}
