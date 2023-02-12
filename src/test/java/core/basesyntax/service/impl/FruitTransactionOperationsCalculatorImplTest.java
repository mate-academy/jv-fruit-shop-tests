package core.basesyntax.service.impl;

import core.basesyntax.dao.FruitDao;
import core.basesyntax.dao.FruitDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionOperationsCalculator;
import core.basesyntax.strategy.*;
import org.junit.Before;
import org.junit.Test;
import java.util.HashMap;
import java.util.Map;

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
}