package core.basesyntax.strategy.impl;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.OperationHandler;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

class TransactionHandlerImplTest {
    private static final OperationStrategyImpl operationStrategy = new OperationStrategyImpl(Map.of(
            FruitTransaction.Operation.BALANCE, new BalanceOperationHandler(),
            FruitTransaction.Operation.PURCHASE, new PurchaseOperationHandler(),
            FruitTransaction.Operation.RETURN, new ReturnOperationHandler(),
            FruitTransaction.Operation.SUPPLY, new SupplyOperationHandler()));

    @Test
    void parse_getBalanceOperation_Ok() {
        OperationHandler operationHandler =
                operationStrategy.get(FruitTransaction.Operation.BALANCE);
        Assert.assertTrue(operationHandler instanceof BalanceOperationHandler);
    }

    @Test
    void parse_getPurchaseOperation_Ok() {
        OperationHandler operationHandler =
                operationStrategy.get(FruitTransaction.Operation.PURCHASE);
        Assert.assertTrue(operationHandler instanceof PurchaseOperationHandler);
    }

    @Test
    void parse_getReturnOperation_Ok() {
        OperationHandler operationHandler =
                operationStrategy.get(FruitTransaction.Operation.RETURN);
        Assert.assertTrue(operationHandler instanceof ReturnOperationHandler);
    }

    @Test
    void parse_getSupplyOperation_Ok() {
        OperationHandler operationHandler =
                operationStrategy.get(FruitTransaction.Operation.SUPPLY);
        Assert.assertTrue(operationHandler instanceof SupplyOperationHandler);
    }
}
