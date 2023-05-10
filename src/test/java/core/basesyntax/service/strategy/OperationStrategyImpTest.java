package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.SupplyOperation;
import core.basesyntax.service.operation.ReturnOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImpTest {
    private FruitTransactionDao fruitTransactionDao;
    private FruitTransactionService fruitTransactionService;
    private OperationStrategy operationStrategy;
    private Map<FruitTransaction.Operation, OperationHandler> operationMap;

    @BeforeEach
    void setUp() {
        fruitTransactionDao = new FruitTransactionDaoIml();
        fruitTransactionService = new FruitTransactionServiceImpl(fruitTransactionDao);
        operationMap = new HashMap<>();
        operationMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperation(fruitTransactionDao, fruitTransactionService));
        operationMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperation(fruitTransactionDao));
        operationMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperation(fruitTransactionDao));
        operationMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperation(fruitTransactionDao));
        operationStrategy = new OperationStrategyImp(operationMap);
    }

    @Test
    void get_ok() {
        assertTrue(operationStrategy.get(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
        assertTrue(operationStrategy.get(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
        assertTrue(operationStrategy.get(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
        assertTrue(operationStrategy.get(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
    }
}
