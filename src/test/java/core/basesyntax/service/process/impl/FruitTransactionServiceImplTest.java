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
    private static final Integer START_QUANTITY = 20;
    private static final String FRUIT_NAME = "banana";
    private static final StorageDao storageDao = new StorageDaoImpl();
    private static Map<FruitTransaction.Operation,
            OperationHandler> operationHandlerMap = new HashMap<>();
    private static OperationStrategy operationStrategy =
            new OperationStrategyImpl(operationHandlerMap);
    private static final FruitTransactionService fruitTransactionService =
            new FruitTransactionServiceImpl(operationStrategy);

    @BeforeClass
    public static void beforeClass() {
        operationHandlerMap.put(FruitTransaction.Operation.PURCHASE,
                new PurchaseHandler(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.RETURN,
                new ReturnHandler(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.BALANCE,
                new BalanceHandler(storageDao));
        operationHandlerMap.put(FruitTransaction.Operation.SUPPLY,
                new SupplyHandler(storageDao));
    }

    @Test
    public void processTransactions_balance_ok() {
        FruitTransaction.Operation balanceOperation = FruitTransaction.Operation.BALANCE;
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(balanceOperation, FRUIT_NAME, START_QUANTITY));
        fruitTransactionService.processTransactions(testData);
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(START_QUANTITY, actual);
    }

    @Test
    public void processTransactions_purchase_ok() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        Integer purchaseQuantity = 13;
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(purchaseOperation, FRUIT_NAME, purchaseQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer expected = START_QUANTITY - purchaseQuantity;
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void processTransactions_purchaseMore_notOk() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        Integer purchaseQuantity = 100;
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(purchaseOperation, FRUIT_NAME, purchaseQuantity));
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService.processTransactions(testData));
    }

    @Test
    public void processTransactions_purchaseNonExistent_notOk() {
        FruitTransaction.Operation purchaseOperation = FruitTransaction.Operation.PURCHASE;
        Integer purchaseQuantity = 100;
        String notValidFruitName = "plane";
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(purchaseOperation,
                        notValidFruitName, purchaseQuantity));
        assertThrows(RuntimeException.class,
                () -> fruitTransactionService.processTransactions(testData));
    }

    @Test
    public void processTransactions_return_ok() {
        FruitTransaction.Operation returnOperation = FruitTransaction.Operation.RETURN;
        Integer returnQuantity = 30;
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(returnOperation, FRUIT_NAME, returnQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer expected = START_QUANTITY + returnQuantity;
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @Test
    public void processTransactions_supply_ok() {
        FruitTransaction.Operation supplyOperation = FruitTransaction.Operation.SUPPLY;
        Integer supplyQuantity = 40;
        Storage.storage.put(FRUIT_NAME, START_QUANTITY);
        List<FruitTransaction> testData =
                List.of(new FruitTransaction(supplyOperation, FRUIT_NAME, supplyQuantity));
        fruitTransactionService.processTransactions(testData);
        Integer expected = START_QUANTITY + supplyQuantity;
        Integer actual = Storage.storage.get(FRUIT_NAME);
        assertEquals(expected, actual);
    }

    @After
    public void tearDown() {
        Storage.storage.clear();
    }
}
