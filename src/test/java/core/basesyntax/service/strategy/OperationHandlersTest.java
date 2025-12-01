package core.basesyntax.service.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.Test;

class OperationHandlersTest {

    @Test
    void balanceOperation_setsFruit_ok() {
        BalanceOperation operation = new BalanceOperation();
        Map<String, Integer> fruits = new HashMap<>();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );
        operation.apply(fruits, transaction);
        assertEquals(100, fruits.get("apple"));
    }

    @Test
    void balanceOperation_overwritesExisting_ok() {
        BalanceOperation operation = new BalanceOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.BALANCE, "apple", 100
        );
        operation.apply(fruits, transaction);
        assertEquals(100, fruits.get("apple"));
    }

    @Test
    void supplyOperation_addsToExisting_ok() {
        SupplyOperation operation = new SupplyOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "apple", 30
        );
        operation.apply(fruits, transaction);
        assertEquals(80, fruits.get("apple"));
    }

    @Test
    void supplyOperation_newFruit_ok() {
        SupplyOperation operation = new SupplyOperation();
        Map<String, Integer> fruits = new HashMap<>();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY, "banana", 50
        );
        operation.apply(fruits, transaction);
        assertEquals(50, fruits.get("banana"));
    }

    @Test
    void purchaseOperation_subtractsFromExisting_ok() {
        PurchaseOperation operation = new PurchaseOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30
        );
        operation.apply(fruits, transaction);
        assertEquals(70, fruits.get("apple"));
    }

    @Test
    void purchaseOperation_notEnoughStock_throwsException() {
        PurchaseOperation operation = new PurchaseOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 10);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 30
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operation.apply(fruits, transaction));
        assertTrue(exception.getMessage().contains("Not enough"));
    }

    @Test
    void purchaseOperation_nullTransaction_throwsException() {
        PurchaseOperation operation = new PurchaseOperation();
        Map<String, Integer> fruits = new HashMap<>();
        assertThrows(IllegalArgumentException.class,
                () -> operation.apply(fruits, null));
    }

    @Test
    void purchaseOperation_negativeQuantity_throwsException() {
        PurchaseOperation operation = new PurchaseOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 100);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", -10
        );
        assertThrows(IllegalArgumentException.class,
                () -> operation.apply(fruits, transaction));
    }

    @Test
    void purchaseOperation_exactAmount_ok() {
        PurchaseOperation operation = new PurchaseOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 50
        );
        operation.apply(fruits, transaction);
        assertEquals(0, fruits.get("apple"));
    }

    @Test
    void returnOperation_addsToExisting_ok() {
        ReturnOperation operation = new ReturnOperation();
        Map<String, Integer> fruits = new HashMap<>();
        fruits.put("apple", 50);
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "apple", 20
        );
        operation.apply(fruits, transaction);
        assertEquals(70, fruits.get("apple"));
    }

    @Test
    void returnOperation_newFruit_ok() {
        ReturnOperation operation = new ReturnOperation();
        Map<String, Integer> fruits = new HashMap<>();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.RETURN, "banana", 30
        );
        operation.apply(fruits, transaction);
        assertEquals(30, fruits.get("banana"));
    }

    @Test
    void purchaseOperation_nonExistentFruit_throwsException() {
        PurchaseOperation operation = new PurchaseOperation();
        Map<String, Integer> fruits = new HashMap<>();
        FruitTransaction transaction = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE, "apple", 10
        );
        RuntimeException exception = assertThrows(RuntimeException.class,
                () -> operation.apply(fruits, transaction));
        assertTrue(exception.getMessage().contains("Not enough"));
    }
}
