package core.basesyntax.service.implementations;

import static org.junit.Assert.assertEquals;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationStrategy;
import core.basesyntax.service.operationhandler.BalanceOperationHandler;
import core.basesyntax.service.operationhandler.OperationHandler;
import core.basesyntax.service.operationhandler.PurchaseOperationHandler;
import core.basesyntax.service.operationhandler.ReturnOperationHandler;
import core.basesyntax.service.operationhandler.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

public class TransactionProcessorImplTest {
    private static final String TEST_FRUIT = "banana";
    private StorageDao storageDao;
    private TransactionProcessorImpl transactionProcessor;
    private List<FruitTransaction> fruitTransactions;
    private FruitTransaction fruitTransaction1;
    private FruitTransaction fruitTransaction2;
    private FruitTransaction fruitTransaction3;
    private FruitTransaction fruitTransaction4;

    @Before
    public void setUp() {
        storageDao = new StorageDaoImpl();
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
        fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit(TEST_FRUIT);
        fruitTransaction1.setQuantity(100);
        fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction2.setFruit(TEST_FRUIT);
        fruitTransaction2.setQuantity(10);
        fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit(TEST_FRUIT);
        fruitTransaction3.setQuantity(60);
        fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction4.setFruit(TEST_FRUIT);
        fruitTransaction4.setQuantity(20);
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(fruitTransaction1);
        fruitTransactions.add(fruitTransaction2);
        fruitTransactions.add(fruitTransaction3);
        fruitTransactions.add(fruitTransaction4);
    }

    @Test
    public void processData_ok() {
        transactionProcessor.processData(fruitTransactions);
        assertEquals(70, (int) Storage.fruits.get(TEST_FRUIT));
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeBalanceQuantity_notOk() {
        fruitTransaction1.setQuantity(-5);
        fruitTransactions.add(fruitTransaction1);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeReturnQuantity_notOk() {
        fruitTransaction2.setQuantity(-5);
        fruitTransactions.add(fruitTransaction1);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void processData_toMuchPurchaseQuantity_notOk() {
        fruitTransaction3.setQuantity(1000);
        fruitTransactions.add(fruitTransaction1);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeSupplyQuantity_notOk() {
        fruitTransaction4.setQuantity(-5);
        fruitTransactions.add(fruitTransaction1);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = NullPointerException.class)
    public void processData_nullTransaction_notOk() {
        transactionProcessor.processData(null);
    }

    @AfterClass
    public static void afterClass() throws Exception {
        Storage.fruits.clear();
    }
}
