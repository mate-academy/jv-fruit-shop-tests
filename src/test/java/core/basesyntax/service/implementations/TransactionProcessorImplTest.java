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
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TransactionProcessorImplTest {
    private Map<FruitTransaction.Operation, OperationHandler> operationHandlersMap = new HashMap<>();
    private OperationStrategy operationStrategy = new OperationStrategyImpl(operationHandlersMap);
    private TransactionProcessorImpl transactionProcessor = new TransactionProcessorImpl(operationStrategy);
    private StorageDao storageDao = new StorageDaoImpl();
    private List<FruitTransaction> fruitTransactions;
    private FruitTransaction fruitTransaction1;
    private FruitTransaction fruitTransaction2;
    private FruitTransaction fruitTransaction3;
    private FruitTransaction fruitTransaction4;

    @BeforeEach
    void setUp() {
        operationHandlersMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.RETURN,
                new ReturnOperationHandler(storageDao));
        operationHandlersMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyOperationHandler(storageDao));
        fruitTransaction1 = new FruitTransaction();
        fruitTransaction1.setOperation(FruitTransaction.Operation.BALANCE);
        fruitTransaction1.setFruit("banana");
        fruitTransaction1.setQuantity(100);
        fruitTransaction2 = new FruitTransaction();
        fruitTransaction2.setOperation(FruitTransaction.Operation.RETURN);
        fruitTransaction2.setFruit("banana");
        fruitTransaction2.setQuantity(10);
        fruitTransaction3 = new FruitTransaction();
        fruitTransaction3.setOperation(FruitTransaction.Operation.PURCHASE);
        fruitTransaction3.setFruit("banana");
        fruitTransaction3.setQuantity(60);
        fruitTransaction4 = new FruitTransaction();
        fruitTransaction4.setOperation(FruitTransaction.Operation.SUPPLY);
        fruitTransaction4.setFruit("banana");
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
        Assert.assertTrue(storageDao.get("banana") == 70);
    }

    @Test
    public void processData_nullTransaction_notOk() {
        Assert.assertThrows(NullPointerException.class, () -> transactionProcessor.processData(null));
    }
}