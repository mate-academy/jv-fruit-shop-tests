package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.TransactionExecutor;
import core.basesyntax.strategy.BalanceOperationHandler;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.OperationStrategy;
import core.basesyntax.strategy.OperationStrategyImpl;
import core.basesyntax.strategy.PurchaseOperationHandler;
import core.basesyntax.strategy.ReturnOperationHandler;
import core.basesyntax.strategy.SupplyOperationHandler;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TransactionExecutorImplTest {
    public static final int OPENING_BALANCE = 15;
    public static final int QUANTITY_OF_FRUIT = 10;
    private OperationStrategy operationStrategy;
    private TransactionExecutor transactionExecutor;
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() {
        operationStrategy = new OperationStrategyImpl(getOperationHandlerMap());
        transactionExecutor = new TransactionExecutorImpl(operationStrategy);
        fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setFruit("fruit");
        fruitTransaction.setQuantity(OPENING_BALANCE);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        transactionExecutor.execute(fruitTransactionList);
    }

    @Test
    public void execute_balanceOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        transactionExecutor.execute(fruitTransactionList);
        Integer actual = Storage.fruitStorage.get("fruit");
        Integer expected = QUANTITY_OF_FRUIT;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_supplyOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        transactionExecutor.execute(fruitTransactionList);
        Integer actual = Storage.fruitStorage.get("fruit");
        Integer expected = OPENING_BALANCE + QUANTITY_OF_FRUIT;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_returnOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        transactionExecutor.execute(fruitTransactionList);
        Integer actual = Storage.fruitStorage.get("fruit");
        Integer expected = OPENING_BALANCE + QUANTITY_OF_FRUIT;
        assertEquals(expected, actual);
    }

    @Test
    public void execute_purchaseOperation_ok() {
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction.setQuantity(QUANTITY_OF_FRUIT);
        List<FruitTransaction> fruitTransactionList = List.of(fruitTransaction);
        transactionExecutor.execute(fruitTransactionList);
        Integer actual = Storage.fruitStorage.get("fruit");
        Integer expected = OPENING_BALANCE - QUANTITY_OF_FRUIT;
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {

        Storage.fruitStorage.clear();
    }

    private Map<FruitTransaction.Operation, OperationHandler> getOperationHandlerMap() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler());
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler());
        return operationHandlerMap;
    }
}
