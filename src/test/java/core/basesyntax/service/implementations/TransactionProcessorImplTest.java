package core.basesyntax.service.implementations;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
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
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TransactionProcessorImplTest {
    private static final String TEST_FRUIT = "banana";
    private StorageDao storageDao;
    private TransactionProcessorImpl transactionProcessor;
    private List<FruitTransaction> fruitTransactions;

    @BeforeEach
    void setUp() {
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
        FruitTransaction fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit(TEST_FRUIT);
        fruitTransaction1.setQuantity(100);
        FruitTransaction fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction2.setFruit(TEST_FRUIT);
        fruitTransaction2.setQuantity(10);
        FruitTransaction fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit(TEST_FRUIT);
        fruitTransaction3.setQuantity(60);
        FruitTransaction fruitTransaction4 = new FruitTransaction();
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
        Assertions.assertEquals(70, (int) storageDao.get(TEST_FRUIT));
    }

    @Test
    public void processData_nullTransaction_notOk() {
        Assertions.assertThrows(NullPointerException.class,
                () -> transactionProcessor.processData(null));
    }
}
