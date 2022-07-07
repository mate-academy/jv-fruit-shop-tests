package core.basesyntax.service;

import static org.junit.Assert.assertTrue;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Operation;
import core.basesyntax.service.impl.FileReaderServiceImpl;
import core.basesyntax.service.impl.TransactionProcessorImpl;
import core.basesyntax.strategy.OperationHandler;
import core.basesyntax.strategy.impl.BalanceTransactionImpl;
import core.basesyntax.strategy.impl.PurchaseTransactionImpl;
import core.basesyntax.strategy.impl.ReturnTransactionImpl;
import core.basesyntax.strategy.impl.SupplyTransactionImpl;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.Test;

public class TransactionProcessorTest {
    private static final String testTransactionFilePath = "src/main/resources/transaction.csv";
    private static final int FRUIT_OPERATION_INDEX = 0;
    private static final String TEST_FILE_HEADER = "fruit,quantity";
    private final Map<String, OperationHandler> operationHandlerMap = new HashMap<>();

    @Before
    public void setUp() {
        operationHandlerMap.put(Operation.BALANCE.getOperation(), new BalanceTransactionImpl());
        operationHandlerMap.put(Operation.PURCHASE.getOperation(), new PurchaseTransactionImpl());
        operationHandlerMap.put(Operation.SUPPLY.getOperation(), new SupplyTransactionImpl());
        operationHandlerMap.put(Operation.RETURN.getOperation(), new ReturnTransactionImpl());
    }

    @Test
    public void allOperationElementsInListValid_OK() {
        boolean isValid = true;
        FileReaderService fileReaderService = new FileReaderServiceImpl();
        List<String> testListOfTransaction = fileReaderService
                .readFromFile(testTransactionFilePath);
        for (int i = 1; i < testListOfTransaction.size(); i++) {
            String[] testArrayFromList = testListOfTransaction.get(i).split(",");
            if (!operationHandlerMap
                    .containsKey(testArrayFromList[FRUIT_OPERATION_INDEX])) {
                isValid = false;
            }
        }
        assertTrue(isValid);
    }

    @Test
    public void process_BalanceOperation_OK() {
        List<String> testListBalance = new ArrayList<>();
        testListBalance.add(TEST_FILE_HEADER);
        testListBalance.add("b,banana,60");
        Storage storage = new Storage();
        TransactionProcessor transactionProcessor
                = new TransactionProcessorImpl(operationHandlerMap);
        transactionProcessor.process(testListBalance);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(60));
    }

    @Test
    public void process_PurchaseOperation_OK() {
        List<String> testListBalance = new ArrayList<>();
        testListBalance.add(TEST_FILE_HEADER);
        testListBalance.add("p,banana,10");
        Map<String, Integer> testStorageMap = new HashMap<>();
        testStorageMap.put("banana", 30);
        Storage storage = new Storage();
        Storage.setFruitStore(testStorageMap);
        TransactionProcessor transactionProcessor
                = new TransactionProcessorImpl(operationHandlerMap);
        transactionProcessor.process(testListBalance);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(20));
    }

    @Test
    public void process_ReturnOperation_OK() {
        List<String> testListBalance = new ArrayList<>();
        testListBalance.add(TEST_FILE_HEADER);
        testListBalance.add("r,banana,10");
        Map<String, Integer> testStorageMap = new HashMap<>();
        testStorageMap.put("banana", 30);
        Storage storage = new Storage();
        Storage.setFruitStore(testStorageMap);
        TransactionProcessor transactionProcessor
                = new TransactionProcessorImpl(operationHandlerMap);
        transactionProcessor.process(testListBalance);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(40));
    }

    @Test
    public void process_SupplyOperation_OK() {
        List<String> testListBalance = new ArrayList<>();
        testListBalance.add(TEST_FILE_HEADER);
        testListBalance.add("s,banana,10");
        Map<String, Integer> testStorageMap = new HashMap<>();
        testStorageMap.put("banana", 30);
        Storage storage = new Storage();
        Storage.setFruitStore(testStorageMap);
        TransactionProcessor transactionProcessor
                = new TransactionProcessorImpl(operationHandlerMap);
        transactionProcessor.process(testListBalance);
        assertTrue(Storage.getFruitStore().containsKey("banana")
                && Storage.getFruitStore().containsValue(40));
    }
}
