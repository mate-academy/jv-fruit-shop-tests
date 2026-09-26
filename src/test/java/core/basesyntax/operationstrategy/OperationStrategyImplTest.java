package core.basesyntax.operationstrategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.dao.FruitsDao;
import core.basesyntax.dao.FruitsDaoImpl;
import core.basesyntax.fruittransaction.Operation;
import core.basesyntax.handlers.BalanceOperation;
import core.basesyntax.handlers.OperationHandler;
import core.basesyntax.handlers.PurchaseOperation;
import core.basesyntax.handlers.ReturnOperation;
import core.basesyntax.handlers.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static OperationStrategy strategy;

    @BeforeAll
    static void beforeAll() {
        FruitsDao dao = new FruitsDaoImpl();
        final Map<Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceOperation(dao));
        operationHandlers.put(Operation.PURCHASE, new PurchaseOperation(dao));
        operationHandlers.put(Operation.RETURN, new ReturnOperation(dao));
        operationHandlers.put(Operation.SUPPLY, new SupplyOperation(dao));
        strategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getTransactionHandler_validOperation_ok() {
        assertEquals(BalanceOperation.class,
                strategy.getTransactionHandler(Operation.BALANCE).getClass());
        assertEquals(PurchaseOperation.class,
                strategy.getTransactionHandler(Operation.PURCHASE).getClass());
        assertEquals(ReturnOperation.class,
                strategy.getTransactionHandler(Operation.RETURN).getClass());
        assertEquals(SupplyOperation.class,
                strategy.getTransactionHandler(Operation.SUPPLY).getClass());
    }

    @Test
    void getTransactionHandler_nullOperation_notOk() {
        assertNull(strategy.getTransactionHandler(null));
    }
}
