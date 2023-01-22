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
    private StorageDao storageDao = new StorageDaoImpl();
    private TransactionProcessorImpl transactionProcessor;
    private List<FruitTransaction> fruitTransactions;
    private FruitTransaction transactionOne;
    private FruitTransaction transactionTwo;
    private FruitTransaction transactionThree;
    private FruitTransaction transactionFour;
    private OperationStrategy operationStrategy;

    @Before
    public void setUp() {
        Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        operationStrategy = new OperationStrategyImpl(operationHandlersMap);
        transactionProcessor = new TransactionProcessorImpl(operationStrategy);
        transactionOne = new FruitTransaction();
        transactionOne.setOperation(FruitTransaction.Operation.BALANCE);
        transactionOne.setFruit(TEST_FRUIT);
        transactionOne.setQuantity(100);
        transactionTwo = new FruitTransaction();
        transactionTwo.setOperation(FruitTransaction.Operation.RETURN);
        transactionTwo.setFruit(TEST_FRUIT);
        transactionTwo.setQuantity(10);
        transactionThree = new FruitTransaction();
        transactionThree.setOperation(FruitTransaction.Operation.PURCHASE);
        transactionThree.setFruit(TEST_FRUIT);
        transactionThree.setQuantity(60);
        transactionFour = new FruitTransaction();
        transactionFour.setOperation(FruitTransaction.Operation.SUPPLY);
        transactionFour.setFruit(TEST_FRUIT);
        transactionFour.setQuantity(20);
        fruitTransactions = new ArrayList<>();
        fruitTransactions.add(transactionOne);
        fruitTransactions.add(transactionTwo);
        fruitTransactions.add(transactionThree);
        fruitTransactions.add(transactionFour);
    }

    @Test
    public void processData_ok() {
        transactionProcessor.processData(fruitTransactions);
        assertEquals(70, (int) Storage.fruits.get(TEST_FRUIT));
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeBalanceQuantity_notOk() {
        transactionOne.setQuantity(-5);
        fruitTransactions.add(transactionOne);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeReturnQuantity_notOk() {
        transactionTwo.setQuantity(-5);
        fruitTransactions.add(transactionOne);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void processData_toMuchPurchaseQuantity_notOk() {
        transactionThree.setQuantity(1000);
        fruitTransactions.add(transactionOne);
        transactionProcessor.processData(fruitTransactions);
    }

    @Test (expected = RuntimeException.class)
    public void processData_negativeSupplyQuantity_notOk() {
        transactionFour.setQuantity(-5);
        fruitTransactions.add(transactionOne);
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
