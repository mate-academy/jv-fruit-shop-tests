package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.exceptions.InvalidOperationException;
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

public class TransactionExecutorTest {
    private static final int BASE_TRANSACTION_QUANTITY = 42;
    private static final String FRUIT_NAME = "fruit";
    private static final String NULL_FRUIT_NAME_MESSAGE = "Fruit name can't be null";
    private static final String NULL_OPERATION_MESSAGE = "Operation can't be null";
    private static final String NULL_TRANSACTION_MESSAGE = "Can't execute null!";
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
    public void transactionExecutor_nullTransaction_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> executor.transactionExecute(null));

        assertEquals(NULL_TRANSACTION_MESSAGE, exception.getMessage());
    }

    @Test
    public void transactionExecutor_NullOperation_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> executor.transactionExecute(new FruitTransaction(
                        null, FRUIT_NAME, BASE_TRANSACTION_QUANTITY)));

        assertEquals(NULL_OPERATION_MESSAGE, exception.getMessage());
    }

    @Test
    public void transactionExecutor_NullFruitName_notOk() {
        Throwable exception = assertThrows(InvalidOperationException.class,
                () -> executor.transactionExecute(new FruitTransaction(
                        FruitTransaction.Operation.BALANCE, null, BASE_TRANSACTION_QUANTITY)));

        assertEquals(NULL_FRUIT_NAME_MESSAGE, exception.getMessage());
    }
}
