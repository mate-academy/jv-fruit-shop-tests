package core.basesyntax;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import db.Storage;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import model.Fruit;
import model.FruitTransaction;
import model.FruitTransaction.Operation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.ShopService;
import service.impl.ShopServiceImpl;
import strategy.BalanceImpl;
import strategy.PurchaseImpl;
import strategy.ReturnImpl;
import strategy.SupplyImpl;

public class OperationTest {
    private static Storage storage;
    private static ShopService service;
    private static Fruit fruit;
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
    void processTransactions_withNullOperation_throwsException() {
        fruit = setFruit("banana", 100);
        List<FruitTransaction> transactions = List.of(
            new FruitTransaction(null, fruit));
        service = new ShopServiceImpl(operationHandlers);
        assertThrows(RuntimeException.class, () -> service.processTransactions(transactions));
    }

    @Test
    void balanceOperation_withValidTransaction_updatesStorage() {
        fruit = setFruit("banana", 100);
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        balance.execute(fruit);
        assertEquals(100, storage.get(fruit.getName()));
    }

    @Test
    void balanceOperation_withNegativeQuantity_throwsException() {
        fruit = setFruit("banana", - 100);
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> balance.execute(fruit));
        assertEquals("Quantity cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void balanceOperation_withNullFruit_throwsException() {
        fruit = null;
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> balance.execute(fruit));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void balanceOperation_withNullFruitName_throwsException() {
        fruit = setFruit(null, 100);
        strategy.Operation balance = operationHandlers.get(Operation.BALANCE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> balance.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void supplyOperation_withValidTransaction_updatesStorage() {
        fruit = setFruit("banana", 40);
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        storage.put("banana", 100);
        supply.execute(fruit);
        supply.execute(fruit);
        assertEquals(180, storage.get(fruit.getName()));
    }

    @Test
    void supplyOperation_withNegativeQuantity_throwsException() {
        fruit = setFruit("apple", -100);
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> supply.execute(fruit));
        assertEquals("Supply quantity cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void supplyOperation_withNullFruit_throwsException() {
        fruit = null;
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> supply.execute(fruit));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void supplyOperation_withNullFruitName_throwsException() {
        fruit = setFruit(null, 30);
        strategy.Operation supply = operationHandlers.get(Operation.SUPPLY);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> supply.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void returnOperation_withValidTransaction_updatesStorage() {
        fruit = setFruit("apple", 50);
        strategy.Operation r = operationHandlers.get(Operation.RETURN);
        storage.put("apple", 50);
        r.execute(fruit);
        r.execute(fruit);
        assertEquals(150, storage.get(fruit.getName()));
    }

    @Test
    void returnOperation_withNegativeQuantity_throwsException() {
        fruit = setFruit("banana", - 20);
        strategy.Operation operationReturn = operationHandlers.get(Operation.RETURN);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> operationReturn.execute(fruit));
        assertEquals("Quantity cannot be negative", illegalArgumentException.getMessage());
    }

    @Test
    void returnOperation_withNullFruit_throwsException() {
        fruit = null;
        strategy.Operation operationReturn = operationHandlers.get(Operation.RETURN);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> operationReturn.execute(fruit));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void returnOperation_withNullFruitName_throwsException() {
        fruit = setFruit(null, 20);
        strategy.Operation operationReturn = operationHandlers.get(Operation.RETURN);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> operationReturn.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withValidTransaction_updatesStorage() {
        fruit = setFruit("banana", 20);
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 200);
        purchase.execute(fruit);
        purchase.execute(fruit);
        assertEquals(160, storage.get(fruit.getName()));
    }

    @Test
    void purchaseOperation_withInsufficientQuantity_throwsException() {
        fruit = setFruit("banana", 40);
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 30);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Cannot purchase more fruits than available for fruit: " + fruit.getName()
                + ". Available: " + fruit.getName(), illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withNegativeResultQuantity_throwsException() {
        fruit = setFruit("banana", 20);
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 10);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Cannot purchase more fruits than available "
                + "for fruit: " + fruit.getName()
                + ". Available: " + fruit.getName(),
                illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withZeroQuantity_throwsException() {
        fruit = setFruit("banana", 0);
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 10);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("You can buy zero of " + fruit.getName() + " fruits",
                illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withNegativeQuantity_throwsException() {
        fruit = setFruit("banana", -20);
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        storage.put("banana", 50);
        assertThrows(IllegalArgumentException.class, () -> purchase.execute(fruit));
    }

    @Test
    void purchaseOperation_withNullFruit_throwsException() {
        fruit = null;
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Fruit cannot be null", illegalArgumentException.getMessage());
    }

    @Test
    void purchaseOperation_withNullFruitName_throwsException() {
        fruit = setFruit(null, 20);
        strategy.Operation purchase = operationHandlers.get(Operation.PURCHASE);
        IllegalArgumentException illegalArgumentException = assertThrows(
                IllegalArgumentException.class, () -> purchase.execute(fruit));
        assertEquals("Fruit name cannot be null", illegalArgumentException.getMessage());
    }

    public Fruit setFruit(String fruit, int quantity) {
        return new Fruit(fruit, quantity);
    }
}
