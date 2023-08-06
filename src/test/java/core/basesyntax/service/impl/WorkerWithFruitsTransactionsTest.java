package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.exceptions.CantGetFruitException;
import core.basesyntax.exceptions.CantPutFruitException;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class WorkerWithFruitsTransactionsTest {
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
    private final Map<Operation, OperationHandler> operation = Map.of(
            Operation.BALANCE, new BalanceOperationHandler(),
            Operation.RETURN, new ReturnOperationHandler(),
            Operation.PURCHASE, new PurchaseOperationHandler(),
            Operation.SUPPLY, new SupplyOperationHandler());
    private final TransactionExecutor workerWithTransactions
            = new FruitsTransactionExecutor(new OperationStrategyImpl(operation));

    @BeforeEach
    void setUp() {
        Storage.getStorage().clear();
    }

    @Test
    void completeTransaction_setBalance_ok() {
        Transaction transaction = new FruitTransaction(BALANCE_OPERATION, APPLE, QUANTITY_100);
        workerWithTransactions.executeTransaction(transaction);
        Integer actual = Storage.getStorage().get(APPLE);
        Assertions.assertEquals(Integer.valueOf(100), actual);
    }

    @Test
    void completeTransaction_setBalanceDifferentFruit_ok() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(BALANCE_OPERATION, BANANA, QUANTITY_50);
        Assertions.assertTrue(Storage.getStorage().containsKey(APPLE)
                && Storage.getStorage().containsKey(BANANA));
    }

    @Test
    void completeTransaction_setBalanceSecondTime_notOk() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        CantPutFruitException exception = null;
        try {
            createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_50);
        } catch (CantPutFruitException e) {
            exception = e;
        }
        String actual = exception.getMessage();
        String expected = "apple already exist in Storage";
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void completeTransaction_returnOperation_ok() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(RETURN_OPERATION, APPLE, QUANTITY_30);
        Integer actual = Storage.getStorage().get(APPLE);
        Assertions.assertEquals(Integer.valueOf(130), actual);
    }

    @Test
    void completeTransaction_supplyOperation_ok() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(SUPPLY_OPERATION, APPLE, QUANTITY_30);
        Integer actual = Storage.getStorage().get(APPLE);
        Assertions.assertEquals(Integer.valueOf(130), actual);
    }

    @Test
    void completeTransaction_purchaseOperation_ok() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(PURCHASE_OPERATION, APPLE, QUANTITY_30);
        Integer actual = Storage.getStorage().get(APPLE);
        Assertions.assertEquals(Integer.valueOf(70), actual);
    }

    @Test
    void completeTransaction_purchaseOperationInvalidValue_notOk() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        CantGetFruitException exception = null;
        try {
            createAndCompleteTransactions(PURCHASE_OPERATION, APPLE, QUANTITY_130);
        } catch (CantGetFruitException e) {
            exception = e;
        }
        String expected = "Can't get 130 apple from storage, available only 100 apple";
        String actual = exception.getMessage();
        Assertions.assertEquals(expected, actual);
    }

    @Test
    void completeTransaction_differentOperation_ok() {
        createAndCompleteTransactions(BALANCE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(PURCHASE_OPERATION, APPLE, QUANTITY_100);
        createAndCompleteTransactions(SUPPLY_OPERATION, APPLE, QUANTITY_130);
        createAndCompleteTransactions(RETURN_OPERATION, APPLE, QUANTITY_30);
        createAndCompleteTransactions(BALANCE_OPERATION, BANANA, QUANTITY_50);
        createAndCompleteTransactions(SUPPLY_OPERATION, BANANA, QUANTITY_50);
        createAndCompleteTransactions(PURCHASE_OPERATION, BANANA,QUANTITY_50);
        Integer actualQuantityApplesInStorage = Storage.getStorage().get(APPLE);
        Integer actualQuantityBananasInStorage = Storage.getStorage().get(BANANA);
        Assertions.assertTrue(actualQuantityApplesInStorage.equals(160)
                && actualQuantityBananasInStorage.equals(50));
    }

    private void createAndCompleteTransactions(Operation operation,
                                               String fruitName, Integer quantity) {
        Transaction transaction = new FruitTransaction(operation, fruitName, quantity);
        workerWithTransactions.executeTransaction(transaction);
    }
}
