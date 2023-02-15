package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionOperationsCalculator;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionOperationsCalculatorImplTest {
    private final FruitDao dao = new FruitDaoImpl();
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap
            = new HashMap<>();
    private final OperationStrategy strategy = new OperationStrategyImpl(operationHandlersMap);
    private final FruitTransactionOperationsCalculator transactionOperationsCalculator
            = new FruitTransactionOperationsCalculatorImpl(strategy);

    @Before
    public void setUp() {
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
    }

    @Test
    public void process_Ok() {
        transactionOperationsCalculator.process(dao);
    }

    @Test(expected = NullPointerException.class)
    public void checkNullparameter_Ok() {
        transactionOperationsCalculator.process(null);
    }

}