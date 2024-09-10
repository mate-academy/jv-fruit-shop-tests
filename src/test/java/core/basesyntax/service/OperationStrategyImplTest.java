package core.basesyntax.service;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperation;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.PurchaseOperation;
import core.basesyntax.operation.ReturnOperation;
import core.basesyntax.operation.SupplyOperation;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> handlers;
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        handlers = Map.of(
                FruitTransaction.Operation.BALANCE, new BalanceOperation(),
                FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
                FruitTransaction.Operation.RETURN, new ReturnOperation(),
                FruitTransaction.Operation.SUPPLY, new SupplyOperation()
        );
        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    void operation_BalanceOperation_Ok() {
        OperationHandler expected = new BalanceOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void operation_PurchaseOperation_Ok() {
        OperationHandler expected = new PurchaseOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void operation_ReturnOperation_Ok() {
        OperationHandler expected = new ReturnOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void operation_SupplyOperation_Ok() {
        OperationHandler expected = new SupplyOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void operation_UnknownOperation_NotOk() {
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy.getHandler(null));
    }
}
