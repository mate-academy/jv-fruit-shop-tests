package core.basesyntax.service.strategy;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.counter.BalanceHandlerImpl;
import core.basesyntax.service.counter.OperationHandler;
import core.basesyntax.service.counter.PurchaseHandlerImpl;
import core.basesyntax.service.counter.ReturnHandlerImpl;
import core.basesyntax.service.counter.SupplyHandlerImpl;
import core.basesyntax.service.transaction.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationStrategyMap
            = new HashMap<>();

    @Test
    void getOperationType_correctData_Ok() {
        operationStrategyMap.put(FruitTransaction.Operation.BALANCE, new BalanceHandlerImpl());
        operationStrategyMap.put(FruitTransaction.Operation.SUPPLY, new SupplyHandlerImpl());
        operationStrategyMap.put(FruitTransaction.Operation.PURCHASE, new PurchaseHandlerImpl());
        operationStrategyMap.put(FruitTransaction.Operation.RETURN, new ReturnHandlerImpl());

        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        OperationHandler expected = new PurchaseHandlerImpl();
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        OperationHandler actual = operationStrategy.getOperationType(fruitTransaction);
        assertEquals(actual.getClass(), expected.getClass());
    }

    @Test
    void getOperationType_nullOperation_NotOk() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(null);
        OperationHandler actual = operationStrategy.getOperationType(fruitTransaction);
        assertNull(actual);
    }

    @Test
    void getOperationType_null_NotOk() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationStrategyMap);
        assertThrows(RuntimeException.class, () -> {
            operationStrategy.getOperationType(null);
        });
    }
}
