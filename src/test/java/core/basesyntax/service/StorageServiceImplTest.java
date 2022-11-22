package core.basesyntax.service;

import static core.basesyntax.db.Storage.storage;
import static org.junit.Assert.assertEquals;

import core.basesyntax.service.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import core.basesyntax.strategy.OperationHandlerStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class StorageServiceImplTest {
    private static final FruitTransaction NULL_OPERATION =
            new FruitTransaction(null, "banana", 123);
    private static final FruitTransaction NULL_FRUIT =
            new FruitTransaction(FruitTransaction.TypeOperation.PURCHASE, null, 123);
    private static final FruitTransaction EMPTY_FRUIT =
            new FruitTransaction(FruitTransaction.TypeOperation.PURCHASE, "", 123);
    private static final FruitTransaction NULL_QUANTITY =
            new FruitTransaction(FruitTransaction.TypeOperation.SUPPLY, "apple", null);
    private static final FruitTransaction TO_BIG_QUANTITY =
            new FruitTransaction(FruitTransaction.TypeOperation.SUPPLY, "apple", Integer.MAX_VALUE);

    private static final FruitTransaction WRIGHT_BALANCE =
            new FruitTransaction(FruitTransaction.TypeOperation.BALANCE, "apple", 25);
    private static final FruitTransaction WRIGHT_SUPPLY =
            new FruitTransaction(FruitTransaction.TypeOperation.SUPPLY, "apple", 50);
    private static final FruitTransaction WRIGHT_PURCHASE =
            new FruitTransaction(FruitTransaction.TypeOperation.PURCHASE, "apple", 15);
    private static final FruitTransaction WRIGHT_RETURN =
            new FruitTransaction(FruitTransaction.TypeOperation.RETURN, "apple", 5);
    private static StorageServiceImpl storageService;

    public StorageServiceImplTest() {
        Map<FruitTransaction.TypeOperation, OperationHandler> listOperations = new HashMap<>();
        listOperations.put(FruitTransaction.TypeOperation.BALANCE, new BalanceOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.SUPPLY, new SupplyOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.PURCHASE, new PurchaseOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.RETURN, new ReturnOperationHandler());
        storageService = new StorageServiceImpl(new OperationHandlerStrategyImpl(listOperations));
    }

    @Test (expected = RuntimeException.class)
    public void operation_nullOperationItem_notOK() {
        storageService.operation(NULL_FRUIT);
    }

    @Test (expected = RuntimeException.class)
    public void nullFruitItem_NotOK() {
        storageService.operation(NULL_OPERATION);
    }

    @Test (expected = RuntimeException.class)
    public void emptyFruitItem_NotOK() {
        storageService.operation(EMPTY_FRUIT);
    }

    @Test (expected = RuntimeException.class)
    public void nullQuantityItem_NotOK() {
        storageService.operation(NULL_QUANTITY);
    }

    @Test (expected = RuntimeException.class)
    public void toBigQuantityItem_NotOK() {
        storageService.operation(TO_BIG_QUANTITY);
    }

    @Test
    public void storageAfterBalanceOperation_OK() {
        storageService.operation(WRIGHT_BALANCE);
        assertEquals(Integer.valueOf(25), storage.get("apple"));
    }

    @Test
    public void storageAfterSupplyOperation_OK() {
        storageService.operation(WRIGHT_SUPPLY);
        assertEquals(Integer.valueOf(50), storage.get("apple"));
    }

    @Test
    public void storageAfterPurchaseOperation_OK() {
        storage.put("apple", 25);
        storageService.operation(WRIGHT_PURCHASE);
        assertEquals(Integer.valueOf(10), storage.get("apple"));
    }

    @Test
    public void storageAfterPurchaseWithMore_OK() {
        storage.put("apple", 10);
        storageService.operation(WRIGHT_PURCHASE);
        assertEquals(Integer.valueOf(10), storage.get("apple"));
    }

    @Test
    public void storageAfterReturnOperation_OK() {
        storageService.operation(WRIGHT_RETURN);
        assertEquals(Integer.valueOf(5), storage.get("apple"));
    }

    @After
    public void clearStorage() {
        storage.clear();
    }
}
