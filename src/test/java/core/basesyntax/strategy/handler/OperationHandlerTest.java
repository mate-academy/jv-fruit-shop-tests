package core.basesyntax.strategy.handler;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.CustomException;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class OperationHandlerTest {
    private static OperationHandler operationHandler;
    private static Map<String, Integer> testStorage;
    private static FruitTransaction BalanceBananaTransaction;
    private static FruitTransaction SupplyBananaTransaction;
    private static FruitTransaction ReturnBananaTransaction;
    private static FruitTransaction PurchaseBananaTransaction;
    private static FruitTransaction InvalidBananaPurchaseTransaction;
    private static FruitTransaction BalanceAppleTransaction;
    private static FruitTransaction SupplyAppleTransaction;
    private static FruitTransaction ReturnAppleTransaction;
    private static FruitTransaction PurchaseAppleTransaction;
    private static FruitTransaction InvalidApplePurchaseTransaction;
    private static final int CONSTANT_ONE = 1;
    private static final int DEFAULT_QUANTITY = 5;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    @BeforeAll
    static void beforeAll() {
        testStorage = new HashMap<>();
        BalanceBananaTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, BANANA, 100);
        SupplyBananaTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, BANANA, 50);
        ReturnBananaTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 10);
        PurchaseBananaTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA, 5);
        BalanceAppleTransaction =
                new FruitTransaction(FruitTransaction.Operation.BALANCE, APPLE, 80);
        SupplyAppleTransaction =
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, APPLE, 40);
        ReturnAppleTransaction =
                new FruitTransaction(FruitTransaction.Operation.RETURN, APPLE, 15);
        PurchaseAppleTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 5);
    }

    @BeforeEach
    void setUp() {
        Storage.fruits.put(BANANA, DEFAULT_QUANTITY);
        Storage.fruits.put(APPLE, DEFAULT_QUANTITY);
    }

    @AfterEach
    void tearDown() {
        testStorage.clear();
        Storage.fruits.clear();
    }

    @Test
    void operate_BalanceOperation_IsOk() {
        testStorage.put(BANANA, 100);
        testStorage.put(APPLE, 80);
        operationHandler = new BalanceOperationHandler();
        operationHandler.operate(BalanceBananaTransaction);
        operationHandler.operate(BalanceAppleTransaction);
        assertEquals(testStorage, Storage.fruits);
    }

    @Test
    void operate_ReturnOperation_IsOk() {
        testStorage.put(BANANA, 15);
        testStorage.put(APPLE, 20);
        operationHandler = new ReturnOperationHandler();
        operationHandler.operate(ReturnBananaTransaction);
        operationHandler.operate(ReturnAppleTransaction);
        assertEquals(testStorage, Storage.fruits);
    }

    @Test
    void operate_SupplyOperation_IsOk() {
        testStorage.put(BANANA, 55);
        testStorage.put(APPLE, 45);
        operationHandler = new SupplyOperationHandler();
        operationHandler.operate(SupplyAppleTransaction);
        operationHandler.operate(SupplyBananaTransaction);
        assertEquals(testStorage, Storage.fruits);
    }

    @Test
    void operate_PurchaseOperation_IsOk() {
        testStorage.put(BANANA, 0);
        testStorage.put(APPLE, 0);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.operate(PurchaseBananaTransaction);
        operationHandler.operate(PurchaseAppleTransaction);
        assertEquals(testStorage, Storage.fruits);
    }

    @Test
    void operate_MultiplyOperations_IsOk() {
        testStorage.put(BANANA, 155);
        testStorage.put(APPLE, 130);
        operationHandler = new BalanceOperationHandler();
        operationHandler.operate(BalanceAppleTransaction);
        operationHandler.operate(BalanceBananaTransaction);
        operationHandler = new PurchaseOperationHandler();
        operationHandler.operate(PurchaseAppleTransaction);
        operationHandler.operate(PurchaseBananaTransaction);
        operationHandler = new SupplyOperationHandler();
        operationHandler.operate(SupplyBananaTransaction);
        operationHandler.operate(SupplyAppleTransaction);
        operationHandler = new ReturnOperationHandler();
        operationHandler.operate(ReturnAppleTransaction);
        operationHandler.operate(ReturnBananaTransaction);
        assertEquals(testStorage, Storage.fruits);
    }

    @Test
    void operate_InvalidBananaPurchaseOperation_NotOk() {
        operationHandler = new PurchaseOperationHandler();
        InvalidBananaPurchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, BANANA,
                        Storage.fruits.get(BANANA) + CONSTANT_ONE);
        assertThrows(CustomException.class, ()
                -> operationHandler.operate(InvalidBananaPurchaseTransaction));
    }

    @Test
    void operate_InvalidApplePurchaseOperation_NotOk() {
        operationHandler = new PurchaseOperationHandler();
        InvalidApplePurchaseTransaction =
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE,
                        Storage.fruits.get(APPLE) + CONSTANT_ONE);
        assertThrows(CustomException.class, ()
                -> operationHandler.operate(InvalidApplePurchaseTransaction));
    }
}
