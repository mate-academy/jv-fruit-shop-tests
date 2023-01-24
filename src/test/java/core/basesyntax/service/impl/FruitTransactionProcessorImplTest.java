package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.FruitTransactionProcessor;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceOperationHandler;
import core.basesyntax.strategy.impl.PurchaseOperationHandler;
import core.basesyntax.strategy.impl.ReturnsOperationHandler;
import core.basesyntax.strategy.impl.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionProcessorImplTest {
    private FruitTransactionProcessor fruitTransactionProcessor;
    private List<FruitTransaction> fruitTransactionData;

    @Before
    public void setUp() {
        Map<Operation, OperationHandler> operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnsOperationHandler());
        fruitTransactionProcessor = new FruitTransactionProcessorImpl(operationHandlerMap);
        fruitTransactionData = new ArrayList<>();
    }

    @Test
    public void get_successfulProcess_ok() {
        fruitTransactionData.add(new FruitTransaction(Operation.BALANCE, "banana", 20));
        fruitTransactionData.add(new FruitTransaction(Operation.SUPPLY, "banana", 100));
        fruitTransactionData.add(new FruitTransaction(Operation.PURCHASE, "banana", 13));
        fruitTransactionData.add(new FruitTransaction(Operation.RETURN, "banana", 10));
        fruitTransactionData.forEach(f -> fruitTransactionProcessor.get(f.getOperation())
                .handle(f));
        Map<String, Integer> expected = Map.of("banana", 117);
        Map<String, Integer> actual = Storage.fruits;
        assertEquals(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void get_nullData_notOk() {
        fruitTransactionData.add(null);
        fruitTransactionData.forEach(f -> fruitTransactionProcessor.get(f.getOperation())
                .handle(f));
    }

    @AfterClass
    public static void afterClass() {
        Storage.fruits.clear();
    }
}
