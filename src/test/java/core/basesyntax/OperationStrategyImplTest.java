package core.basesyntax.operation;

import core.basesyntax.FruitTransaction;
import core.basesyntax.operation.handler.BalanceOperation;
import core.basesyntax.operation.handler.PurchaseOperation;
import core.basesyntax.operation.handler.ReturnOperation;
import core.basesyntax.operation.handler.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;
    private OperationHandler balanceOperation;
    private OperationHandler purchaseOperation;
    private OperationHandler supplyOperation;
    private OperationHandler returnOperation;

    @BeforeEach
    public void setup() {
        balanceOperation = new BalanceOperation();
        purchaseOperation = new PurchaseOperation();
        supplyOperation = new SupplyOperation();
        returnOperation = new ReturnOperation();

        Map<FruitTransaction.Operation, OperationHandler> handlers = new HashMap<>();
        handlers.put(FruitTransaction.Operation.BALANCE, balanceOperation);
        handlers.put(FruitTransaction.Operation.PURCHASE, purchaseOperation);
        handlers.put(FruitTransaction.Operation.SUPPLY, supplyOperation);
        handlers.put(FruitTransaction.Operation.RETURN, returnOperation);

        operationStrategy = new OperationStrategyImpl(handlers);
    }

    @Test
    public void validOperation_returnCorrectHandler_ok() {
        Assert.assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.BALANCE)
                instanceof BalanceOperation);
        Assert.assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.PURCHASE)
                instanceof PurchaseOperation);
        Assert.assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.SUPPLY)
                instanceof SupplyOperation);
        Assert.assertTrue(operationStrategy.getHandler(FruitTransaction.Operation.RETURN)
                instanceof ReturnOperation);
    }
}
