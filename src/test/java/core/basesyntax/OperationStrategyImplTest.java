package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        FruitTransactionDao dao = new FruitTransactionDaoImpl();
        strategy = new OperationStrategyImpl(Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(dao),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(dao),
                FruitTransaction.Operation.RETURN, new ReturnOperation(dao),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation(dao)
        ));
    }

    @Test
    void handle_balanceOperation_Ok() {
        OperationHandler operationHandler = strategy.getOperationHandler(
                FruitTransaction.Operation.BALANCE);
        assertEquals(operationHandler.getClass(), BalanceOperation.class);
    }

    @Test
    void handle_purchaseOperation_Ok() {
        OperationHandler operationHandler = strategy.getOperationHandler(
                FruitTransaction.Operation.PURCHASE);
        assertEquals(operationHandler.getClass(), PurchaseOperation.class);
    }

    @Test
    void handle_returnOperation_Ok() {
        OperationHandler operationHandler = strategy.getOperationHandler(
                FruitTransaction.Operation.RETURN);
        assertEquals(operationHandler.getClass(), ReturnOperation.class);
    }

    @Test
    void handle_supplyOperation_Ok() {
        OperationHandler operationHandler = strategy.getOperationHandler(
                FruitTransaction.Operation.SUPPLY);
        assertEquals(operationHandler.getClass(), SupplyOperation.class);
    }
}
