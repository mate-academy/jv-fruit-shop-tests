package core.basesyntax;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceTransaction;
import core.basesyntax.strategy.PurchaseTransaction;
import core.basesyntax.strategy.SupplyTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.Test;

public class CalculationTest extends FruitShopTest {
    @Test
    public void calculate_multipleItems_ok() {
        FruitTransaction balanceTransaction = new FruitTransaction(BALANCE, "apple", 40);
        FruitTransaction suppleTransaction = new FruitTransaction(SUPPLY, "apple", 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 50);
        new BalanceTransaction().calculateAndStore(balanceTransaction);
        new SupplyTransaction().calculateAndStore(suppleTransaction);
        assertEquals(expected, Storage.STORAGE);
    }

    @Test
    public void calculate_theSameItems_ok() {
        FruitTransaction transactionApple = new FruitTransaction(BALANCE, "apple", 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 10);
        new BalanceTransaction().calculateAndStore(transactionApple);
        assertEquals(expected, Storage.STORAGE);
    }

    @Test
    public void calculate_differentFruits_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put("apple", 25);
        expected.put("banana", 45);
        FruitTransaction balanceTransactionApple = new FruitTransaction(BALANCE, "apple", 40);
        FruitTransaction balanceTransactionBanana = new FruitTransaction(BALANCE, "banana", 10);
        FruitTransaction supplyTransactionBanana = new FruitTransaction(SUPPLY, "banana", 35);
        FruitTransaction purchaseTransactionBanana = new FruitTransaction(PURCHASE, "apple", 15);
        new BalanceTransaction().calculateAndStore(balanceTransactionApple);
        new BalanceTransaction().calculateAndStore(balanceTransactionBanana);
        new SupplyTransaction().calculateAndStore(supplyTransactionBanana);
        new PurchaseTransaction().calculateAndStore(purchaseTransactionBanana);
        assertEquals(expected, Storage.STORAGE);
    }
}
