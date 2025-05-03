package core.basesyntax.service;

import core.basesyntax.dao.FruitTransactionDao;
import core.basesyntax.dao.FruitTransactionDaoImpl;
import core.basesyntax.database.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.BalanceOperationHandler;
import core.basesyntax.operation.OperationHandler;
import core.basesyntax.operation.OperationStrategy;
import core.basesyntax.operation.OperationStrategyImpl;
import core.basesyntax.operation.PurchaseOperationHandler;
import core.basesyntax.operation.ReturnOperationHandler;
import core.basesyntax.operation.SupplyOperationHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransactionServiceImplTest {
    private static OperationStrategy operationStrategy;
    private static TransactionService transactionService;
    private static FruitTransactionDao fruitTransactionDao;
    private static Map<FruitTransaction.Operation, OperationHandler> operationHandlerMap;
    private static Map<String, Integer> REQUIRED_STORAGE_DATA;
    private static List<FruitTransaction> DATA;

    @BeforeClass
    public static void setUp() {
        fruitTransactionDao = new FruitTransactionDaoImpl();
        operationHandlerMap = new HashMap<>();
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(fruitTransactionDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(fruitTransactionDao));
        operationStrategy = new OperationStrategyImpl(operationHandlerMap);
        transactionService = new TransactionServiceImpl(operationStrategy);
    }

    @Before
    public void beforeEach() {
        REQUIRED_STORAGE_DATA = new HashMap<>();
        REQUIRED_STORAGE_DATA.put("banana",152);
        REQUIRED_STORAGE_DATA.put("apple",90);
        DATA = new ArrayList<>();
        addDataToList("b","banana",20);
        addDataToList("b","apple",100);
        addDataToList("s","banana",100);
        addDataToList("p","banana",13);
        addDataToList("r","apple",10);
        addDataToList("p","apple",20);
        addDataToList("p","banana",5);
        addDataToList("s","banana",50);
    }

    @Test
    public void countsFruitsAfterWorkDay_checkValidList_Ok() {
        Map<String, Integer> actual = transactionService.countsFruitsAfterWorkDay(DATA);
        Assert.assertEquals("Data in the storage is not correct.",
                REQUIRED_STORAGE_DATA, actual);
    }

    @Test(expected = RuntimeException.class)
    public void countsFruitsAfterWorkDay_nullList_NotOk() {
        transactionService.countsFruitsAfterWorkDay(null);
    }

    @After
    public void afterEach() {
        DATA.clear();
        Storage.fruitTransactionStorage.clear();
    }

    private void addDataToList(String operation, String fruitName, Integer fruitQuantity) {
        FruitTransaction fruitTransaction = new FruitTransaction();
        fruitTransaction.setOperation(operation);
        fruitTransaction.setFruitName(fruitName);
        fruitTransaction.setQuantity(fruitQuantity);
        DATA.add(fruitTransaction);
    }
}
