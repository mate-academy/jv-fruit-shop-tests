package core.basesyntax.service.process.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.impl.StorageDaoImpl;
import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseHandler;
import core.basesyntax.service.operations.ReturnHandler;
import core.basesyntax.service.operations.SupplyHandler;
import core.basesyntax.service.process.FruitTransactionService;
import core.basesyntax.service.strategy.OperationStrategy;
import core.basesyntax.service.strategy.impl.OperationStrategyImpl;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitTransactionServiceImplTest {
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static Map<FruitTransaction.Operation,
            OperationHandler> operationOperationsHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationOperationsHandlerMap);
    private static final FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl(operationStrategy);

    @BeforeClass
    public static void beforeClass() {
        operationOperationsHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(storageDao));
        operationOperationsHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(storageDao));
        operationOperationsHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(storageDao));
        operationOperationsHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(storageDao));
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }

    @Test
    public void processTransactions_balance_ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.BALANCE;
        String fruitName = "banana";
        Integer fruitQuantity = 20;
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(balanceOperation, fruitName, fruitQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer actualQuantity = Storage.storage.get(fruitName);
        assertEquals(fruitQuantity, actualQuantity);
    }

    @Test
    public void processTransactions_purchase_ok() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        String fruitName = "banana";
        Integer startQuantity = 20;
        Integer purchaseQuantity = 13;
        Storage.storage.put(fruitName, startQuantity);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(purchaseOperation, fruitName, purchaseQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer expectedQuantity = startQuantity - purchaseQuantity;
        Integer actualQuantity = Storage.storage.get(fruitName);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void processTransactions_purchaseMore_notOk() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        String fruitName = "banana";
        Integer startQuantity = 20;
        Integer purchaseQuantity = 100;
        Storage.storage.put(fruitName, startQuantity);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(purchaseOperation, fruitName, purchaseQuantity));
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService.processTransactions(testData));
    }

    @Test
    public void processTransactions_purchaseNonExistent_notOk() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        String fruitName = "banana";
        Integer startQuantity = 20;
        Integer purchaseQuantity = 100;
        String notValidFruitName = "peach";
        Storage.storage.put(fruitName, startQuantity);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(purchaseOperation,
                        notValidFruitName, purchaseQuantity));
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService.processTransactions(testData));
    }

    @Test
    public void processTransactions_return_ok() {
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.RETURN;
        String fruitName = "banana";
        Integer startQuantity = 10;
        Integer returnQuantity = 20;
        Storage.storage.put(fruitName, startQuantity);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(returnOperation, fruitName, returnQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer expectedQuantity = startQuantity + returnQuantity;
        Integer actualQuantity = Storage.storage.get(fruitName);
        assertEquals(expectedQuantity, actualQuantity);
    }

    @Test
    public void processTransactions_supply_ok() {
        FruitTransaction.Operation supplyOperation = FruitTransaction.Operation.SUPPLY;
        String fruitName = "banana";
        Integer startQuantity = 10;
        Integer supplyQuantity = 20;
        Storage.storage.put(fruitName, startQuantity);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(supplyOperation, fruitName, supplyQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer expectedQuantity = startQuantity + supplyQuantity;
        Integer actualQuantity = Storage.storage.get(fruitName);
        assertEquals(expectedQuantity, actualQuantity);
    }
}
