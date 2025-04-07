package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import model.FruitTransaction.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import strategy.BalanceImpl;
import strategy.PurchaseImpl;
import strategy.ReturnImpl;
import strategy.SupplyImpl;

public class OperationTest {
    private static Storage storage;
    private Map<Operation, strategy.Operation> operationHandlers;

    @BeforeEach
    void create() {
        storage = new Storage();
        operationHandlers = new HashMap<>();
        operationHandlers.put(Operation.BALANCE, new BalanceImpl());
        operationHandlers.put(Operation.SUPPLY, new SupplyImpl());
        operationHandlers.put(Operation.PURCHASE, new PurchaseImpl());
        operationHandlers.put(Operation.RETURN, new ReturnImpl());
    }

    @Test
    void balanceOperation_withValidTransaction_updatesStorage() {
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        balance.execute("banana", 100);
        assertEquals(100, storage.get("banana"));
    }

    @Test
    void balanceOperation_withNegativeQuantity_throwsException() {
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        assertThrows(IllegalArgumentException.class, () -> balance.execute("banana", -100));
    }

    @Test
    void supplyOperation_withValidTransaction_updatesStorage() {
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        supply.execute("apple", 50);
        supply.execute("apple", 30);
        assertEquals(130, storage.get("apple"));
    }

    @Test
    void supplyOperation_withNegativeQuantity_throwsException() {
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        assertThrows(IllegalArgumentException.class, () -> supply.execute("apple", -100));
    }

    @Test
    void returnOperation_withValidTransaction_updatesStorage() {
        strategy.Operation r = operationHandlers.get(Operation.RETURN);
        storage.put("apple", 90);
        r.execute("apple", 50);
        r.execute("apple", 40);
        assertEquals(180, storage.get("apple"));
    }

    @Test
    void returnOperation_withNegativeQuantity_throwsException() {
        strategy.Operation r = operationHandlers.get(Operation.RETURN);
        assertThrows(IllegalArgumentException.class, () -> r.execute("apple", -20));
    }

    @Test
    void purchaseOperation_withValidTransaction_updatesStorage() {
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("apple", 100);
        purchase.execute("apple", 30);
        purchase.execute("apple", 20);
        assertEquals(50, storage.get("apple"));
    }

    @Test
    void purchaseOperation_withInsufficientQuantity_throwsException() {
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 30);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute("banana", 40));
    }

    @Test
    void purchaseOperation_withZeroQuantity_throwsException() {
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 30);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute("banana", 0));
    }

    @Test
    void purchaseOperation_withNegativeQuantity_throwsException() {
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 30);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute("banana", -20));
    }

    @Test
    void balanceOperation_withZeroQuantity_throwsException() {
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        assertThrows(IllegalArgumentException.class, () -> balance.execute("banana", 0));
    }

    @Test
    void supplyOperation_withZeroQuantity_throwsException() {
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        assertThrows(IllegalArgumentException.class, () -> supply.execute("banana", 0));
    }

    @Test
    void returnOperation_withZeroQuantity_throwsException() {
        strategy.Operation operationReturn = operationHandlers.get(Operation.RETURN);
        assertThrows(IllegalArgumentException.class, () -> operationReturn.execute("banana", 0));
    }

    @Test
    void balanceOperation_withNullFruit_throwsException() {
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        assertThrows(IllegalArgumentException.class, () -> balance.execute(null, 100));
    }

    @Test
    void supplyOperation_withNullFruit_throwsException() {
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        assertThrows(IllegalArgumentException.class, () -> supply.execute(null, 30));
    }

    @Test
    void returnOperation_withNullFruit_throwsException() {
        strategy.Operation operationReturn = operationHandlers.get(Operation.RETURN);
        assertThrows(IllegalArgumentException.class, () -> operationReturn.execute(null, 40));
    }

    @Test
    void purchaseOperation_withNullFruit_throwsException() {
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute(null, 40));
    }
}
