package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.impl.BalanceOperation;
import core.basesyntax.operation.impl.OperationHandler;
import core.basesyntax.operation.impl.PurchaseOperation;
import core.basesyntax.operation.impl.ReturnOperation;
import core.basesyntax.operation.impl.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlers;
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);

    @BeforeAll
    static void beforeAll() {
        operationHandlers = Map.of(FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation());
    }

    @Test
    void getService_nonExistingOperation_notOk() {
        assertNotNull(operationStrategy.getService(FruitTransaction.Operation.SUPPLY));
    }

    @Test
    void getService_typeCheck_ok() {
        assertTrue(operationStrategy.getService(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
        assertTrue(operationStrategy.getService(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
        assertTrue(operationStrategy.getService(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
        assertTrue(operationStrategy.getService(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
    }
}
