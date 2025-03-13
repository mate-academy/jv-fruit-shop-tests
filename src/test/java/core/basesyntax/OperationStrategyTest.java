package core.basesyntax;

import core.basesyntax.model.BalanceOperation;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.OperationHandler;
import core.basesyntax.model.PurchaseOperation;
import core.basesyntax.model.ReturnOperation;
import core.basesyntax.model.SupplyOperation;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlers = new HashMap<>();

    @BeforeEach
    void setUp() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
    }

    @Test
    void getOperation_normalMap_Ok() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        OperationHandler actual = operationStrategy.getOperation(FruitTransaction
                .Operation.PURCHASE);
        OperationHandler expected = operationHandlers.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getOperation_emptyMap_notOk() {
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlers);
        operationHandlers.clear();
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy
                .getOperation(FruitTransaction.Operation.PURCHASE));
    }
}
