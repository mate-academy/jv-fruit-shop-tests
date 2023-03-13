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
        FruitTransaction balanceTransaction
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, 40);
        FruitTransaction suppleTransaction
                = new FruitTransaction(SUPPLY, DEFAULT_FRUIT_APPLE, 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, 50);
        new BalanceTransaction().calculateAndStore(balanceTransaction);
        new SupplyTransaction().calculateAndStore(suppleTransaction);
        assertEquals(expected, Storage.STORAGE);
    }

    @Test
    public void calculate_theSameItems_ok() {
        FruitTransaction transactionApple = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, 10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, 10);
        new BalanceTransaction().calculateAndStore(transactionApple);
        assertEquals(expected, Storage.STORAGE);
    }

    @Test
    public void calculate_differentFruits_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, 25);
        expected.put(DEFAULT_FRUIT_BANANA, 45);
        FruitTransaction balanceTransactionApple
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, 40);
        FruitTransaction balanceTransactionBanana
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_BANANA, 10);
        FruitTransaction supplyTransactionBanana
                = new FruitTransaction(SUPPLY, DEFAULT_FRUIT_BANANA, 35);
        FruitTransaction purchaseTransactionBanana
                = new FruitTransaction(PURCHASE, DEFAULT_FRUIT_APPLE, 15);
        new BalanceTransaction().calculateAndStore(balanceTransactionApple);
        new BalanceTransaction().calculateAndStore(balanceTransactionBanana);
        new SupplyTransaction().calculateAndStore(supplyTransactionBanana);
        new PurchaseTransaction().calculateAndStore(purchaseTransactionBanana);
        assertEquals(expected, Storage.STORAGE);
    }
}
