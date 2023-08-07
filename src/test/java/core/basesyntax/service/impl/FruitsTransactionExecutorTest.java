package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.model.Transaction;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.handler.BalanceOperationHandler;
import core.basesyntax.strategy.handler.OperationHandler;
import core.basesyntax.strategy.handler.PurchaseOperationHandler;
import core.basesyntax.strategy.handler.ReturnOperationHandler;
import core.basesyntax.strategy.handler.SupplyOperationHandler;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitsTransactionExecutorTest {
    public static final Operation BALANCE_OPERATION = Operation.BALANCE;
    public static final Operation SUPPLY_OPERATION = Operation.SUPPLY;
    public static final Operation PURCHASE_OPERATION = Operation.PURCHASE;
    public static final Operation RETURN_OPERATION = Operation.RETURN;
    public static final String APPLE = "apple";
    public static final String BANANA = "banana";
    public static final Integer QUANTITY_100 = 100;
    public static final Integer QUANTITY_50 = 50;
    public static final Integer QUANTITY_30 = 30;
    public static final Integer QUANTITY_130 = 130;
    private static final Map<Operation, OperationHandler> operation = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler());
    private final TransactionExecutor transactionExecutor
            = new FruitsTransactionExecutor(new OperationStrategyImpl(operation));

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void completeTransaction_ValidData_ok() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(PURCHASE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(SUPPLY_OPERATION, APPLE, QUANTITY_130);
        createAndCompleteTransactions(RETURN_OPERATION, APPLE, QUANTITY_30);
        createAndCompleteTransactions(BALANCE_OPERATION, BANANA, QUANTITY_50);
        createAndCompleteTransactions(SUPPLY_OPERATION, BANANA, QUANTITY_50);
        createAndCompleteTransactions(PURCHASE_OPERATION, BANANA,QUANTITY_50);

        Integer actualQuantityApplesInStorage = Storage.getStorage().get(APPLE);
        Integer actualQuantityBananasInStorage = Storage.getStorage().get(BANANA);

        assertTrue(actualQuantityApplesInStorage.equals(160)
                && actualQuantityBananasInStorage.equals(50));
    }

    private void createAndCompleteTransactions(Operation operation,
                                               String fruitName, Integer quantity) {
        Transaction transaction = new FruitTransaction(operation, fruitName, quantity);
        transactionExecutor.executeTransaction(transaction);
    }
}
