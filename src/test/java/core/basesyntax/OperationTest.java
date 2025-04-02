package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.impl.WriterServiceImpl;
import strategy.BalanceImpl;
import strategy.Operation;
import strategy.PurchaseImpl;
import strategy.ReturnImpl;
import strategy.SupplyImpl;

public class OperationTest {
    private static WriterServiceImpl writerService;
    private static Storage storage;
    private Map<String, Operation> strategy;

    @BeforeEach
    void create() {
        storage = new Storage();
        strategy = new HashMap<>();
        strategy.put("b", new BalanceImpl());
        strategy.put("s", new SupplyImpl());
        strategy.put("p", new PurchaseImpl());
        strategy.put("r", new ReturnImpl());
    }

    @Test
    void balanceOperation_withValidTransaction_updatesStorage() {
        Operation balance = strategy.get("b");
        balance.execute("banana", 100);
        assertEquals(100, storage.get("banana"));
    }

    @Test
    void balanceOperation_withNegativeQuantity_throwsException() {
        Operation balance = strategy.get("b");
        assertThrows(IllegalArgumentException.class, () -> balance.execute("banana", -100));
    }

    @Test
    void supplyOperation_withValidTransaction_updatesStorage() {
        Operation supply = strategy.get("s");
        supply.execute("apple", 50);
        supply.execute("apple", 30);
        assertEquals(130, storage.get("apple"));
    }

    @Test
    void supplyOperation_withNegativeQuantity_throwsException() {
        Operation supply = strategy.get("s");
        assertThrows(IllegalArgumentException.class, () -> supply.execute("apple", -100));
    }

    @Test
    void returnOperation_withValidTransaction_updatesStorage() {
        Operation r = strategy.get("r");
        storage.put("apple", 90);
        r.execute("apple", 50);
        r.execute("apple", 40);
        assertEquals(180, storage.get("apple"));
    }

    @Test
    void returnOperation_withNegativeQuantity_throwsException() {
        Operation r = strategy.get("r");
        assertThrows(IllegalArgumentException.class, () -> r.execute("apple", -20));
    }

    @Test
    void purchaseOperation_withValidTransaction_updatesStorage() {
        Operation purchase = strategy.get("p");
        storage.put("apple", 100);
        purchase.execute("apple", 30);
        purchase.execute("apple", 20);
        assertEquals(50, storage.get("apple"));
    }

    @Test
    void purchaseOperation_withInsufficientQuantity_throwsException() {
        Operation purchase = strategy.get("p");
        storage.put("banana", 30);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute("banana", 40));
    }

    @Test
    void purchaseOperation_withZeroQuantity_throwsException() {
        Operation purchase = strategy.get("p");
        storage.put("banana", 30);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute("banana", 0));
    }

    @Test
    void purchaseOperation_withNegativeQuantity_throwsException() {
        Operation purchase = strategy.get("p");
        storage.put("banana", 30);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute("banana", -20));
    }

    @Test
    void balanceOperation_withZeroQuantity_throwsException() {
        Operation balance = strategy.get("b");
        assertThrows(IllegalArgumentException.class, () -> balance.execute("banana", 0));
    }

    @Test
    void supplyOperation_withZeroQuantity_throwsException() {
        Operation supply = strategy.get("s");
        assertThrows(IllegalArgumentException.class, () -> supply.execute("banana", 0));
    }

    @Test
    void returnOperation_withZeroQuantity_throwsException() {
        Operation operationReturn = strategy.get("r");
        assertThrows(IllegalArgumentException.class, () -> operationReturn.execute("banana", 0));
    }

    @Test
    void balanceOperation_withNullFruit_throwsException() {
        Operation balance = strategy.get("b");
        assertThrows(IllegalArgumentException.class, () -> balance.execute(null, 100));
    }

    @Test
    void supplyOperation_withNullFruit_throwsException() {
        Operation supply = strategy.get("s");
        assertThrows(IllegalArgumentException.class, () -> supply.execute(null, 30));
    }

    @Test
    void returnOperation_withNullFruit_throwsException() {
        Operation operationReturn = strategy.get("r");
        assertThrows(IllegalArgumentException.class, () -> operationReturn.execute(null, 40));
    }

    @Test
    void purchaseOperation_withNullFruit_throwsException() {
        Operation purchase = strategy.get("p");
        assertThrows(IllegalArgumentException.class, () -> purchase.execute(null, 40));
    }
}
