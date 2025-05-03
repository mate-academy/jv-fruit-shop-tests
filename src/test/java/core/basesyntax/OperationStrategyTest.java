package core.basesyntax;

import static org.junit.Assert.assertTrue;

import core.basesyntax.service.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.OperationStrategyImpl;
import core.basesyntax.service.operation.BalanceOperation;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperation;
import core.basesyntax.service.operation.ReturnOperation;
import core.basesyntax.service.operation.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());

        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void operationStrategy_shouldChooseCorrectOperation_Ok() {
        FruitTransaction.Operation balance = FruitTransaction.Operation.BALANCE;
        OperationHandler handlerBalance = operationStrategy.strategy(balance);
        assertTrue(handlerBalance instanceof BalanceOperation);

        FruitTransaction.Operation purchase = FruitTransaction.Operation.PURCHASE;
        OperationHandler handlerPurchase = operationStrategy.strategy(purchase);
        assertTrue(handlerPurchase instanceof PurchaseOperation);

        FruitTransaction.Operation returnoperation = FruitTransaction.Operation.RETURN;
        OperationHandler handlerReturn = operationStrategy.strategy(returnoperation);
        assertTrue(handlerReturn instanceof ReturnOperation);

        FruitTransaction.Operation supply = FruitTransaction.Operation.SUPPLY;
        OperationHandler handlerSupply = operationStrategy.strategy(supply);
        assertTrue(handlerSupply instanceof SupplyOperation);
    }
}
