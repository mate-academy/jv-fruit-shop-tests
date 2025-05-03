package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertNull;

import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.impl.BalanceTransactionHandler;
import core.basesyntax.service.impl.TransactionHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationStrategyImplTest {
    private OperationStrategyImpl operationStrategy;

    @BeforeEach
    void setUp() {
        Map<FruitTransaction.Operation, TransactionHandler> transactionHandlerMap = new HashMap<>();
        transactionHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceTransactionHandler());
        operationStrategy = new OperationStrategyImpl(transactionHandlerMap);
    }

    @Test
    void existingOperation_Null_Ok() {
        FruitTransaction.Operation operation = FruitTransaction.Operation.PURCHASE;
        TransactionHandler actualHandler = operationStrategy.get(operation);
        assertNull(actualHandler);
    }
}
