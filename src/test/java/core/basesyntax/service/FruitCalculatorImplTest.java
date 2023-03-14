package core.basesyntax.service;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.strategy.BalanceTransaction;
import core.basesyntax.strategy.PurchaseTransaction;
import core.basesyntax.strategy.SupplyTransaction;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitCalculatorImplTest {
    private static final String DEFAULT_FRUIT_APPLE = "apple";
    private static final String DEFAULT_FRUIT_BANANA = "banana";
    private static final int DEFAULT_QUANTITY_10 = 10;
    private static final int DEFAULT_QUANTITY_15 = 15;
    private static final int DEFAULT_QUANTITY_25 = 25;
    private static final int DEFAULT_QUANTITY_35 = 35;
    private static final int DEFAULT_QUANTITY_40 = 40;
    private static final int DEFAULT_QUANTITY_45 = 45;
    private static final int DEFAULT_QUANTITY_50 = 50;
    private static final String FILE_NAME = "src/test/resources/input.csv";
    private static File testFile;

    @BeforeClass
    public static void setUpBeforeClass() {
        testFile = new File(FILE_NAME);
        try {
            testFile.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void calculateAndStore_multipleItems_ok() {
        FruitTransaction balanceTransaction
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_40);
        FruitTransaction suppleTransaction
                = new FruitTransaction(SUPPLY, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_50);
        new BalanceTransaction().calculateAndStore(balanceTransaction);
        new SupplyTransaction().calculateAndStore(suppleTransaction);
        assertEquals("Test failed! Storage must contain "
                + expected + ", but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test
    public void calculateAndStore_theSameItems_ok() {
        FruitTransaction transactionApple
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        new BalanceTransaction().calculateAndStore(transactionApple);
        assertEquals(expected, Storage.STORAGE);
    }

    @Test
    public void calculate_differentFruits_ok() {
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_25);
        expected.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_45);
        FruitTransaction balanceTransactionApple
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_40);
        FruitTransaction balanceTransactionBanana
                = new FruitTransaction(BALANCE, DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_10);
        FruitTransaction supplyTransactionBanana
                = new FruitTransaction(SUPPLY, DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_35);
        FruitTransaction purchaseTransactionBanana
                = new FruitTransaction(PURCHASE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_15);
        new BalanceTransaction().calculateAndStore(balanceTransactionApple);
        new BalanceTransaction().calculateAndStore(balanceTransactionBanana);
        new SupplyTransaction().calculateAndStore(supplyTransactionBanana);
        new PurchaseTransaction().calculateAndStore(purchaseTransactionBanana);
        assertEquals(expected, Storage.STORAGE);
    }
}
