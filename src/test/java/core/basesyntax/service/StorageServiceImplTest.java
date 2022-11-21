package core.basesyntax.service;

import core.basesyntax.service.model.FruitTransaction;
import core.basesyntax.service.operations.BalanceOperationHandler;
import core.basesyntax.service.operations.OperationHandler;
import core.basesyntax.service.operations.PurchaseOperationHandler;
import core.basesyntax.service.operations.ReturnOperationHandler;
import core.basesyntax.service.operations.SupplyOperationHandler;
import core.basesyntax.strategy.OperatioHandlerStrategyImpl;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class StorageServiceImplTest {
    private static final FruitTransaction NULL_OPERATION =
            new FruitTransaction(null, "banana", 1235);
    private static final FruitTransaction NULL_FRUIT =
            new FruitTransaction(FruitTransaction.TypeOperation.PURCHASE, null, 1235);
    private static final FruitTransaction EMPTY_FRUIT =
            new FruitTransaction(FruitTransaction.TypeOperation.PURCHASE, "", 1235);
    private static final FruitTransaction NULL_QUANTITY =
            new FruitTransaction(FruitTransaction.TypeOperation.SUPPLY, "apple", null);
    private static final FruitTransaction TO_BIG_QUANTITY =
            new FruitTransaction(FruitTransaction.TypeOperation.SUPPLY, "apple", Integer.MAX_VALUE);

    private static StorageServiceImpl storageService;

    public StorageServiceImplTest() {
        Map<FruitTransaction.TypeOperation, OperationHandler> listOperations = new HashMap<>();
        listOperations.put(FruitTransaction.TypeOperation.BALANCE, new BalanceOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.SUPPLY, new SupplyOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.PURCHASE, new PurchaseOperationHandler());
        listOperations.put(FruitTransaction.TypeOperation.RETURN, new ReturnOperationHandler());
        storageService = new StorageServiceImpl(new OperatioHandlerStrategyImpl(listOperations));
    }

    @Test (expected = RuntimeException.class)
    public void checkNullOperationItem_NotOK() {
        storageService.operation(NULL_FRUIT);
    }

    @Test (expected = RuntimeException.class)
    public void checkNullFruitItem_NotOK() {
        storageService.operation(NULL_OPERATION);
    }

    @Test (expected = RuntimeException.class)
    public void checkEmptyFruitItem_NotOK() {
        storageService.operation(EMPTY_FRUIT);
    }

    @Test (expected = RuntimeException.class)
    public void checkNullQuanityItem_NotOK() {
        storageService.operation(NULL_QUANTITY);
    }

    @Test (expected = RuntimeException.class)
    public void checkToBigQuantityItem_NotOK() {
        storageService.operation(TO_BIG_QUANTITY);
    }

}
