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
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationProcessingStrategyImplTest {
    private static OperationProcessingStrategy operationProcessingStrategy;
    private static Map<FruitTransaction.Operation, OperationProcessing> operationProcessingMap;
    private static FruitsDao fruitsDao;

    @BeforeClass
    public static void beforeClass() throws Exception {
        fruitsDao = new FruitsDaoImpl();
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
}
