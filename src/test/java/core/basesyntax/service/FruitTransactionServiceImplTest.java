package core.basesyntax.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.model.Operation;
import core.basesyntax.service.operation.BalanceOperationHandler;
import core.basesyntax.service.operation.OperationHandler;
import core.basesyntax.service.operation.PurchaseOperationHandler;
import core.basesyntax.service.operation.ReturnOperationHandler;
import core.basesyntax.service.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private Map<Operation, OperationHandler> operationHandlerMap;
    private OperationStrategy operationStrategy;
    private FruitTransactionService fruitTransactionService;

    @Before
    public void setUp() {
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(Operation.BALANCE, new BalanceOperationHandler());
        operationHandlerMap.put(Operation.SUPPLY, new SupplyOperationHandler());
        operationHandlerMap.put(Operation.PURCHASE, new PurchaseOperationHandler());
        operationHandlerMap.put(Operation.RETURN, new ReturnOperationHandler());
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        fruitTransactionService = new FruitTransactionServiceImpl(operationStrategy);
    }

    @Test
    public void checkBalanceOperationHandler_Ok() {
        String fruitName = "peach";
        int fruitQuantity = 5;

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.BALANCE);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        assertTrue(Storage.reportMap.containsKey(fruitName));
        assertEquals(fruitQuantity, (int) Storage.reportMap.get(fruitName));
    }

    @Test
    public void checkPurchaseOperationHandler_Ok() {
        String fruitName = "banana";
        int fruitQuantity = 7;

        Storage.reportMap.put(fruitName, fruitQuantity);

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.PURCHASE);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        int fruitQuantityExpected = fruitQuantity - fruitQuantity;

        assertEquals(fruitQuantityExpected, (int) Storage.reportMap.get(fruitName));
    }

    @Test
    public void checkReturnOperationHandler_Ok() {
        String fruitName = "orange";
        int fruitQuantity = 12;

        Storage.reportMap.put(fruitName, fruitQuantity);

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.RETURN);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        int fruitQuantityExpected = fruitQuantity + fruitQuantity;

        assertEquals(fruitQuantityExpected, (int) Storage.reportMap.get(fruitName));
    }

    @Test
    public void checkSupplyOperationHandler_Ok() {
        String fruitName = "watermelon";
        int fruitQuantity = 8;

        Storage.reportMap.put(fruitName, fruitQuantity);

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.SUPPLY);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        List<FruitTransaction> transactions = new ArrayList<>();
        transactions.add(fruitTransactionExpected);

        fruitTransactionService.processTransactions(transactions);

        int fruitQuantityExpected = fruitQuantity + fruitQuantity;

        assertEquals(fruitQuantityExpected, (int) Storage.reportMap.get(fruitName));
    }

    @After
    public void afterEachTest() {
        Storage.reportMap.clear();
    }
}
