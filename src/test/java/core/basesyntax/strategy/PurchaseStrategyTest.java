package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class PurchaseStrategyTest {
    private static final String DEFAULT_FRUIT_APPLE = "apple";
    private static final String DEFAULT_FRUIT_BANANA = "banana";
    private static final int DEFAULT_QUANTITY_50 = 50;
    private static final int DEFAULT_QUANTITY_40 = 40;
    private static final int DEFAULT_QUANTITY_15 = 15;
    private static final int DEFAULT_QUANTITY_10 = 10;
    private static final int DEFAULT_QUANTITY_35 = 35;
    private static final int DEFAULT_QUANTITY_45 = 45;
    private final PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
    private final CalculationStrategy strategy = new CalculationStrategy();

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void calculateAndStore_purchase_ok() {
        Storage.STORAGE.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_50);
        Storage.STORAGE.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_40);
        expected.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_15);
        FruitTransaction transactionApple
                = new FruitTransaction(PURCHASE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        FruitTransaction transactionBanana
                = new FruitTransaction(PURCHASE, DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_35);
        purchaseTransaction.calculateAndStore(transactionApple);
        purchaseTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test
    public void getCalculationServiceByLetter_purchase_ok() {
        FruitTransaction purchase
                = new FruitTransaction(PURCHASE, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_45);
        CalculationService service = strategy.getCalculationServiceByLetter(purchase);
        assertEquals("Test failed! Method must return "
                + PurchaseTransaction.class + ", but was "
                + service.getClass(), PurchaseTransaction.class, service.getClass());
    }
}
