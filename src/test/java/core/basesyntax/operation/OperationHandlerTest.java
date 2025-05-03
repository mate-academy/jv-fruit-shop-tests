package core.basesyntax.operation;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static final int INVALID_AMOUNT = -5;
    private static final int VALID_AMOUNT = 20;
    private static final String FRUIT = "raspberry";
    private static final FruitTransaction.Operation OPERATION_BALANCE =
            FruitTransaction.Operation.BALANCE;
    private Map<String, Integer> map;
    private OperationHandler operationHandler;
    private FruitTransaction fruitTransaction;

    @BeforeEach
    void setUp() {
        map = new HashMap<>();
        fruitTransaction = new FruitTransaction(FRUIT, OPERATION_BALANCE, VALID_AMOUNT);
        Storage.fruits.put(FRUIT, VALID_AMOUNT);
    }

    @AfterEach
    void tearDown() {
        Storage.fruits.clear();
    }

    @Test
    void applyTransactionToStorageWithBalanceHandler_checkStorage_ok() {
        map.put(FRUIT, VALID_AMOUNT);
        operationHandler = new BalanceOperationHandler();
        operationHandler.applyTransactionToStorage(fruitTransaction);
        Assert.assertEquals(map, Storage.fruits);
    }

    @Test
    void applyTransactionToStorageWithReturnHandler_checkStorage_ok() {
        map.put(FRUIT, VALID_AMOUNT * 2);
        fruitTransaction.setOperation(FruitTransaction.Operation.RETURN);
        operationHandler = new ReturnOperationHandler();
        operationHandler.applyTransactionToStorage(fruitTransaction);
        Assert.assertEquals(map, Storage.fruits);
    }

    @Test
    void applyTransactionToStorageWithSupplyHandler_checkStorage_ok() {
        map.put(FRUIT, VALID_AMOUNT * 2);
        fruitTransaction.setOperation(FruitTransaction.Operation.SUPPLY);
        operationHandler = new SupplyOperationHandler();
        operationHandler.applyTransactionToStorage(fruitTransaction);
        Assert.assertEquals(map, Storage.fruits);
    }

    @Test
    void applyTransactionToStorageWithPurchaseHandler_checkStorage_ok() {
        map.put(FRUIT, VALID_AMOUNT - VALID_AMOUNT);
        fruitTransaction.setOperation(FruitTransaction.Operation.PURCHASE);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.applyTransactionToStorage(fruitTransaction);
        Assert.assertEquals(map, Storage.fruits);
    }

    @Test
    void applyTransactionToStorageWithBalanceHandler_negativeAmount_notOk() {
        fruitTransaction.setAmount(INVALID_AMOUNT);
        operationHandler = new BalanceOperationHandler();
        Assert.assertThrows(RuntimeException.class, () ->
                operationHandler.applyTransactionToStorage(fruitTransaction));
    }

    @Test
    void applyTransactionToStorageWithPurchaseHandler_negativeAmount_notOk() {
        fruitTransaction.setAmount(INVALID_AMOUNT);
        operationHandler = new PurchaseOperationHandler();
        Assert.assertThrows(RuntimeException.class, () ->
                operationHandler.applyTransactionToStorage(fruitTransaction));
    }

    @Test
    void applyTransactionToStorageWithSupplyHandler_negativeAmount_notOk() {
        fruitTransaction.setAmount(INVALID_AMOUNT);
        operationHandler = new SupplyOperationHandler();
        Assert.assertThrows(RuntimeException.class, () ->
                operationHandler.applyTransactionToStorage(fruitTransaction));
    }

    @Test
    void applyTransactionToStorageWithReturnHandler_negativeAmount_notOk() {
        fruitTransaction.setAmount(INVALID_AMOUNT);
        operationHandler = new ReturnOperationHandler();
        Assert.assertThrows(RuntimeException.class, () ->
                operationHandler.applyTransactionToStorage(fruitTransaction));
    }
}
