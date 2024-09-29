package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperation;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperation;
import core.basesyntax.service.operations.ReturnOperation;
import core.basesyntax.service.operations.SupplyOperation;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategy operationStrategy;

    @BeforeEach
    void setup() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlers = new HashMap<>();
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @Test
    void getOperationHandler_correctOperation_Ok() {
        OperationHandler supplyHandler = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.SUPPLY);
        OperationHandler balanceHandler = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.BALANCE);
        OperationHandler purchaseHandler = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.PURCHASE);
        OperationHandler returnHandler = operationStrategy
                .getOperationHandler(FruitTransaction.Operation.RETURN);

        Assertions.assertInstanceOf(SupplyOperation.class, supplyHandler);
        Assertions.assertInstanceOf(BalanceOperation.class, balanceHandler);
        Assertions.assertInstanceOf(PurchaseOperation.class,purchaseHandler);
        Assertions.assertInstanceOf(ReturnOperation.class, returnHandler);
    }
}
