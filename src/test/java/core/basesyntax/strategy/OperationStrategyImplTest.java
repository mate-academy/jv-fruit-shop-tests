package core.basesyntax.strategy;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.operation.BalanceOperation;
import core.basesyntax.strategy.operation.FruitOperationHandler;
import core.basesyntax.strategy.operation.PurchaseOperation;
import core.basesyntax.strategy.operation.ReturnOperation;
import core.basesyntax.strategy.operation.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategyImpl operationStrategy;
    private static FruitOperationHandler fruitOperationHandler;
    private static final StorageDao STORAGE_DAO = new StorageDaoImpl();

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, FruitOperationHandler> operationHandlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(STORAGE_DAO),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(STORAGE_DAO),
                FruitTransaction.Operation.RETURN, new ReturnOperation(STORAGE_DAO),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(STORAGE_DAO));
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void operationType_OperationBalance_Ok() {
        Class<? extends FruitOperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.BALANCE).getClass();
        Class<BalanceOperation> expected = BalanceOperation.class;
        assertEquals(expected, actual);
    }

    @Test
    void operationType_OperationPurchase_Ok() {
        Class<? extends FruitOperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.PURCHASE).getClass();
        Class<PurchaseOperation> expected = PurchaseOperation.class;
        assertEquals(expected, actual);
    }

    @Test
    void operationType_OperationSupply_Ok() {
        Class<? extends FruitOperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.SUPPLY).getClass();
        Class<SupplyOperation> expected = SupplyOperation.class;
        assertEquals(expected, actual);
    }

    @Test
    void operationType_OperationReturn_Ok() {
        Class<? extends FruitOperationHandler> actual
                = operationStrategy.get(FruitTransaction.Operation.RETURN).getClass();
        Class<ReturnOperation> expected = ReturnOperation.class;
        assertEquals(expected, actual);
    }
}
