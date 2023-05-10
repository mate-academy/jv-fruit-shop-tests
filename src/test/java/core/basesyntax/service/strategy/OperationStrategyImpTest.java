package core.basesyntax.service.strategy;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.impl.FruitTransactionDaoIml;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitTransactionService;
import core.basesyntax.service.impl.FruitTransactionServiceImpl;
import core.basesyntax.service.operation.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

class OperationStrategyImpTest {
    private FruitTransactionDao fruitTransactionDao;
    FruitTransactionService fruitTransactionService;
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
