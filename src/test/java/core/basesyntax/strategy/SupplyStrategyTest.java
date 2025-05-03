package core.basesyntax.strategy;

import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.HashMap;
import java.util.Map;
import org.junit.After;
import org.junit.Test;

public class SupplyStrategyTest {
    private static final String DEFAULT_FRUIT_APPLE = "apple";
    private static final String DEFAULT_FRUIT_BANANA = "banana";
    private static final int DEFAULT_QUANTITY_50 = 50;
    private static final int DEFAULT_QUANTITY_60 = 60;
    private static final int DEFAULT_QUANTITY_85 = 85;
    private static final int DEFAULT_QUANTITY_10 = 10;
    private static final int DEFAULT_QUANTITY_35 = 35;
    private static final int DEFAULT_QUANTITY_45 = 45;
    private final SupplyTransaction supplyTransaction = new SupplyTransaction();
    private final CalculationStrategy strategy = new CalculationStrategy();

    @After
    public void cleanStorageAfterEach() {
        Storage.STORAGE.clear();
    }

    @Test
    public void calculateAndStore_supply_ok() {
        Storage.STORAGE.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_50);
        Storage.STORAGE.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_50);
        Map<String, Integer> expected = new HashMap<>();
        expected.put(DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_60);
        expected.put(DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_85);
        FruitTransaction transactionApple
                = new FruitTransaction(RETURN, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_10);
        FruitTransaction transactionBanana
                = new FruitTransaction(RETURN, DEFAULT_FRUIT_BANANA, DEFAULT_QUANTITY_35);
        supplyTransaction.calculateAndStore(transactionApple);
        supplyTransaction.calculateAndStore(transactionBanana);
        assertEquals("Test failed! Storage must contains "
                + expected + " but was " + Storage.STORAGE, expected, Storage.STORAGE);
    }

    @Test
    public void getCalculationServiceByLetter_supply_ok() {
        FruitTransaction supply
                = new FruitTransaction(SUPPLY, DEFAULT_FRUIT_APPLE, DEFAULT_QUANTITY_45);
        CalculationService service = strategy.getCalculationServiceByLetter(supply);
        assertEquals("Test failed! Method must return "
                + SupplyTransaction.class + ", but was "
                + service.getClass(), SupplyTransaction.class, service.getClass());
    }
}
