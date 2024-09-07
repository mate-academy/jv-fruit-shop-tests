package core.basesyntax;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceOperation;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.PurchaseOperation;
import core.basesyntax.strategy.ReturnOperation;
import core.basesyntax.strategy.SupplyOperation;
import core.basesyntax.strategy.imlp.OperationStrategyImpl;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private final Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperation(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperation(),
            FruitTransaction.Operation.RETURN, new ReturnOperation(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperation()
    );
    private final OperationStrategyImpl operationStrategy = new OperationStrategyImpl(
            operationHandlerMap);

    @Test
    void getHandler_returnOperation_ok() {
        OperationHandler expected = new ReturnOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.RETURN);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getHandler_supplyOperation_ok() {
        OperationHandler expected = new SupplyOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getHandler_purchaseOperation_ok() {
        OperationHandler expected = new PurchaseOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getHandler_balanceOperation_ok() {
        OperationHandler expected = new BalanceOperation();
        OperationHandler actual = operationStrategy.getHandler(FruitTransaction.Operation.BALANCE);
        Assertions.assertEquals(expected.getClass(), actual.getClass());
    }

    @Test
    void getHandler_null_not_ok() {
        Assertions.assertThrows(RuntimeException.class,() -> {
            operationStrategy.getHandler(null);
        });
    }

}
