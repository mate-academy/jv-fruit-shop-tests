package core.basesyntax.strategies;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceTransaction;
import core.basesyntax.strategy.CalculationService;
import core.basesyntax.strategy.CalculationStrategy;
import core.basesyntax.strategy.PurchaseTransaction;
import core.basesyntax.strategy.ReturnTransaction;
import core.basesyntax.strategy.SupplyTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class StrategiesTest {
    private final BalanceTransaction balanceTransaction = new BalanceTransaction();
    private final PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
    private final ReturnTransaction returnTransaction = new ReturnTransaction();
    private final SupplyTransaction supplyTransaction = new SupplyTransaction();
    private final CalculationStrategy strategy = new CalculationStrategy();

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void calculateAndStore_Balance_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 10);
        expected.put("banana", 35);
        FruitTransaction transactionApple = new FruitTransaction(BALANCE, "apple", 10);
        FruitTransaction transactionBanana = new FruitTransaction(BALANCE, "banana", 35);
        balanceTransaction.calculateAndStore(transactionApple);
        balanceTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test(expected = RuntimeException.class)
    public void calculateAndStore_balance_negativeQuantity_notOk() {
        FruitTransaction transactionApple = new FruitTransaction(BALANCE, "apple", -10);
        balanceTransaction.calculateAndStore(transactionApple);
    }

    @Test
    public void calculateAndStore_purchase_ok() {
        Storage.STORAGE.put("apple", 50);
        Storage.STORAGE.put("banana", 50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 40);
        expected.put("banana", 15);
        FruitTransaction transactionApple = new FruitTransaction(PURCHASE, "apple", 10);
        FruitTransaction transactionBanana = new FruitTransaction(PURCHASE, "banana", 35);
        purchaseTransaction.calculateAndStore(transactionApple);
        purchaseTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test
    public void calculateAndStore_return_ok() {
        Storage.STORAGE.put("apple", 50);
        Storage.STORAGE.put("banana", 50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 60);
        expected.put("banana", 85);
        FruitTransaction transactionApple = new FruitTransaction(RETURN, "apple", 10);
        FruitTransaction transactionBanana = new FruitTransaction(RETURN, "banana", 35);
        returnTransaction.calculateAndStore(transactionApple);
        returnTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test
    public void calculateAndStore_supply_ok() {
        Storage.STORAGE.put("apple", 50);
        Storage.STORAGE.put("banana", 50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 60);
        expected.put("banana", 85);
        FruitTransaction transactionApple = new FruitTransaction(RETURN, "apple", 10);
        FruitTransaction transactionBanana = new FruitTransaction(RETURN, "banana", 35);
        supplyTransaction.calculateAndStore(transactionApple);
        supplyTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test
    public void getCalculationServiceByLetter_balance_ok() {
        FruitTransaction balance = new FruitTransaction(BALANCE, "apple", 45);
        CalculationService service = strategy.getCalculationServiceByLetter(balance);
        assertEquals("Test failed! Method must return "
                + BalanceTransaction.class + ", but was "
                + service.getClass(), BalanceTransaction.class, service.getClass());
    }

    @Test
    public void getCalculationServiceByLetter_purchase_ok() {
        FruitTransaction purchase = new FruitTransaction(PURCHASE, "apple", 45);
        CalculationService service = strategy.getCalculationServiceByLetter(purchase);
        assertEquals("Test failed! Method must return "
                + PurchaseTransaction.class + ", but was "
                + service.getClass(), PurchaseTransaction.class, service.getClass());
    }

    @Test
    public void getCalculationServiceByLetter_return_ok() {
        FruitTransaction returnOperation = new FruitTransaction(RETURN, "apple", 45);
        CalculationService service = strategy.getCalculationServiceByLetter(returnOperation);
        assertEquals("Test failed! Method must return "
                + ReturnTransaction.class + ", but was "
                + service.getClass(), ReturnTransaction.class, service.getClass());
    }

    @Test
    public void getCalculationServiceByLetter_supply_ok() {
        FruitTransaction supply = new FruitTransaction(SUPPLY, "apple", 45);
        CalculationService service = strategy.getCalculationServiceByLetter(supply);
        assertEquals("Test failed! Method must return "
                + SupplyTransaction.class + ", but was "
                + service.getClass(), SupplyTransaction.class, service.getClass());
    }
}
