package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

public class BalanceOperationHandlerTest {
    private static final int BASE_TRANSACTION_QUANTITY = 42;
    private static final String FRUIT_NAME = "fruit";
    private static final Map<FruitTransaction.Operation, OperationHandler>
            operationHandlerMap = new HashMap<>();

    static {
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
    }

    private static final OperationStrategy strategy =
            new OperationStrategyImpl(operationHandlerMap);
    private static final TransactionExecutorImpl executor = new TransactionExecutorImpl(strategy);

    @Test
    public void transactionExecutor_balanceTransaction_Ok() {
        executor.transactionExecute(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, BASE_TRANSACTION_QUANTITY));

        assertEquals(BASE_TRANSACTION_QUANTITY, (int) Storage.reportData.get(FRUIT_NAME));
    }
}
