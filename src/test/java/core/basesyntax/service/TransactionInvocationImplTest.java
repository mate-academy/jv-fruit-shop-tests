package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.operations.BalanceTransactionExecutor;
import core.basesyntax.operations.PurchasingTransactionExecutor;
import core.basesyntax.operations.ReturningTransactionExecutor;
import core.basesyntax.operations.SupplingTransactionExecutor;
import core.basesyntax.operations.TransactionExecutor;
import core.basesyntax.storage.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransactionInvocationImplTest {
    @Rule
    public ExpectedException thrownRule = ExpectedException.none();

    @After
    public void clearStorage() {
        Storage.FRUIT_MAP.clear();
    }

    @Test
    public void involveBalanceTransaction_ok() {
        TransactionInvocation transactionInvocation = new TransactionInvocationImpl();
        Map<String, Integer> expectedMap = Map.of("apple", 100);
        Map<FruitTransaction.Operation, TransactionExecutor> operationsMap = createOperationMap();
        List<FruitTransaction> fruitTransactionList = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 100)
        );
        transactionInvocation.invokeTransaction(fruitTransactionList, operationsMap);
        assertEquals(expectedMap, Storage.FRUIT_MAP);
    }

    @Test
    public void involveSupplyTransaction_ok() {
        TransactionInvocation transactionInvocation = new TransactionInvocationImpl();
        Map<String, Integer> expectedMap = Map.of("apple", 10);
        Map<FruitTransaction.Operation, TransactionExecutor> operationsMap = createOperationMap();
        List<FruitTransaction> fruitTransactionList = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5),
                createFruitTransaction(FruitTransaction.Operation.SUPPLY, "apple", 5)
        );
        transactionInvocation.invokeTransaction(fruitTransactionList, operationsMap);
        assertEquals(expectedMap, Storage.FRUIT_MAP);
    }

    @Test
    public void involvePurchaseTransaction_ok() {
        TransactionInvocation transactionInvocation = new TransactionInvocationImpl();
        Map<String, Integer> expectedMap = Map.of("apple", 5);
        Map<FruitTransaction.Operation, TransactionExecutor> operationsMap = createOperationMap();
        List<FruitTransaction> fruitTransactionList = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 10),
                createFruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 5)
        );
        transactionInvocation.invokeTransaction(fruitTransactionList, operationsMap);
        assertEquals(expectedMap, Storage.FRUIT_MAP);
    }

    @Test
    public void involveReturnTransaction_ok() {
        TransactionInvocation transactionInvocation = new TransactionInvocationImpl();
        Map<String, Integer> expectedMap = Map.of("apple", 20);
        Map<FruitTransaction.Operation, TransactionExecutor> operationsMap = createOperationMap();
        List<FruitTransaction> fruitTransactionList = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5),
                createFruitTransaction(FruitTransaction.Operation.RETURN, "apple", 15)
        );
        transactionInvocation.invokeTransaction(fruitTransactionList, operationsMap);
        assertEquals(expectedMap, Storage.FRUIT_MAP);
    }

    @Test
    public void involvePurchaseTransaction_notOk() {
        TransactionInvocation transactionInvocation = new TransactionInvocationImpl();
        Map<FruitTransaction.Operation, TransactionExecutor> operationsMap = createOperationMap();
        List<FruitTransaction> fruitTransactionList = List.of(
                createFruitTransaction(FruitTransaction.Operation.BALANCE, "apple", 5),
                createFruitTransaction(FruitTransaction.Operation.PURCHASE, "apple", 15)
        );
        thrownRule.expect(RuntimeException.class);
        thrownRule.expectMessage("apple, 15 - are not enough to by in store");
        transactionInvocation.invokeTransaction(fruitTransactionList, operationsMap);
    }

    private Map<FruitTransaction.Operation, TransactionExecutor> createOperationMap() {
        Map<FruitTransaction.Operation, TransactionExecutor> operationsMap = new HashMap<>();
        operationsMap.put(FruitTransaction.Operation.BALANCE, new BalanceTransactionExecutor());
        operationsMap.put(FruitTransaction.Operation.PURCHASE, new PurchasingTransactionExecutor());
        operationsMap.put(FruitTransaction.Operation.RETURN, new ReturningTransactionExecutor());
        operationsMap.put(FruitTransaction.Operation.SUPPLY, new SupplingTransactionExecutor());
        return operationsMap;
    }

    private FruitTransaction createFruitTransaction(FruitTransaction.Operation operation,
                                                    String fruit, Integer quantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruit(fruit);
        fruitTransaction.setQuantity(quantity);
        return fruitTransaction;
    }
}
