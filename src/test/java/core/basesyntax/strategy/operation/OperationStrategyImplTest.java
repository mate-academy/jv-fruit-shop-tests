package core.basesyntax.strategy.operation;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionException;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private static final String EXCEPTION_MESSAGE = "Key can't be null";
    private static OperationStrategy operationStrategy;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;

    @BeforeAll
    static void beforeAll() {
        operationHandlerMap = Map.of(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(), FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(), FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(), FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
    }

    @Test
    void strategy_invalidSupplyOperation_notOk() {
        OperationHandler expected = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertNotEquals(expected, actual);
    }

    @Test
    void strategy_validSupplyOperation_ok() {
        OperationHandler expected = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void strategy_validBalanceOperation_ok() {
        OperationHandler expected = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void strategy_validPurchaseOperation_ok() {
        OperationHandler expected = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void strategy_validReturnOperation_ok() {
        OperationHandler expected = operationStrategy.get(FruitTransaction.Operation.RETURN);
        OperationHandler actual = operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void strategy_operationIsNull_notOk() {
        Assertions.assertThrows(TransactionException.class,
                () -> operationStrategy.get(null), EXCEPTION_MESSAGE);
    }

    @Test
    void strategy_getBalanceOperationHandler_ok() {
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.BALANCE);
        BalanceOperationHandler actual = new BalanceOperationHandler();
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void strategy_getSupplyOperationHandler_ok() {
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.SUPPLY);
        SupplyOperationHandler actual = new SupplyOperationHandler();
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void strategy_getPurchaseOperationHandler_ok() {
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.PURCHASE);
        PurchaseOperationHandler actual = new PurchaseOperationHandler();
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void strategy_getReturnOperationHandler_ok() {
        OperationHandler expected = operationHandlerMap.get(FruitTransaction.Operation.RETURN);
        ReturnOperationHandler actual = new ReturnOperationHandler();
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }
}
