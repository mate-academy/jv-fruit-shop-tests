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
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class OperationStrategyTest {
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlers = new HashMap<>();
    private static OperationStrategy operationStrategy;

    @BeforeAll
    static void beforeAll() {
        operationStrategy = new OperationStrategyImpl(operationHandlers);
    }

    @BeforeEach
    void setUp() {
        operationHandlers.put(FruitTransaction.Operation.BALANCE, new BalanceOperation());
        operationHandlers.put(FruitTransaction.Operation.PURCHASE, new PurchaseOperation());
        operationHandlers.put(FruitTransaction.Operation.RETURN, new ReturnOperation());
        operationHandlers.put(FruitTransaction.Operation.SUPPLY, new SupplyOperation());
    }

    @Test
    void getOperation_normalMap_Ok() {
        OperationHandler actual = operationStrategy.getOperation(FruitTransaction
                .Operation.PURCHASE);
        OperationHandler expected = operationHandlers.get(FruitTransaction.Operation.PURCHASE);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void getOperation_emptyMap_notOk() {
        operationHandlers.clear();
        Assertions.assertThrows(RuntimeException.class, () -> operationStrategy
                .getOperation(FruitTransaction.Operation.PURCHASE));
    }
}
