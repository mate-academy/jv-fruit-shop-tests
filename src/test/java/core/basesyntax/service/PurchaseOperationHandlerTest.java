package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.PurchaseException;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PurchaseOperationHandlerTest {
    private static final int BASE_TRANSACTION_QUANTITY = 42;
    private static final int QUANTITY_TO_CHANGE = 10;
    private static final int ZERO_QUANTITY = 0;
    private static final String FRUIT_NAME = "fruit";
    private static final String NO_FRUITS_LEFT_TO_PURCHASE_MESSAGE =
            String.format("Can't sale " + QUANTITY_TO_CHANGE + " of fruit, there are only "
                    + ZERO_QUANTITY + " left");
    private static final String NO_SUCH_FRUIT_TO_PURCHASE =
            String.format("Can't sale \"" + FRUIT_NAME
                    + "\" because there are no such fruit in storage");
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

    @BeforeEach
    public void clearStorage() {
        Storage.reportData.clear();
    }

    @Test
    public void transactionExecutor_purchaseTransaction_Ok() {
        Storage.reportData.put(FRUIT_NAME, BASE_TRANSACTION_QUANTITY);

        executor.transactionExecute(new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, FRUIT_NAME, QUANTITY_TO_CHANGE));

        assertEquals(BASE_TRANSACTION_QUANTITY - QUANTITY_TO_CHANGE,
                (int) Storage.reportData.get(FRUIT_NAME));
    }

    @Test
    public void transactionExecutor_purchaseWithNoFruitsLeft_notOk() {
        executor.transactionExecute(new FruitTransaction(
                FruitTransaction.Operation.BALANCE, FRUIT_NAME, ZERO_QUANTITY));

        Throwable exception = assertThrows(PurchaseException.class,
                () -> executor.transactionExecute(new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, FRUIT_NAME, QUANTITY_TO_CHANGE)));

        assertEquals(NO_FRUITS_LEFT_TO_PURCHASE_MESSAGE, exception.getMessage());
    }

    @Test
    public void transactionExecutor_purchaseWithNoSuchFruit_notOk() {
        Throwable exception = assertThrows(PurchaseException.class,
                () -> executor.transactionExecute(new FruitTransaction(
                        FruitTransaction.Operation.PURCHASE, FRUIT_NAME, QUANTITY_TO_CHANGE)));

        assertEquals(NO_SUCH_FRUIT_TO_PURCHASE,
                exception.getMessage());
    }
}
