package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.FruitShopService;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class FruitShopServiceImplTest {
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String ORANGE = "orange";
    private static final String PEAR = "pear";

    private static FruitShopService fruitShopService;

    @BeforeClass
    public static void beforeAll() {
        fruitShopService = new FruitShopServiceImpl();
        Storage.storage.clear();
    }

    @Before
    public void beforeEach() {
        Storage.storage.put(APPLE, 10);
        Storage.storage.put(BANANA, 20);
        Storage.storage.put(ORANGE, 30);
    }

    @After
    public void afterAll() {
        Storage.storage.clear();
    }

    @Test
    public void test_ProcessData_Update_Storage_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE, 100),
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 5),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 10),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, PEAR, 15)
        );
        fruitShopService.processData(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(APPLE, 5);
        expectedStorage.put(BANANA, 30);
        expectedStorage.put(ORANGE, 130);
        expectedStorage.put(PEAR, 15);
        assertEquals(expectedStorage, Storage.storage);
    }

    @Test
    public void test_ProcessData_Not_Update_Storage_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, 0),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, 0),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, PEAR, 0),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE, 0)
        );
        fruitShopService.processData(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(APPLE, 10);
        expectedStorage.put(BANANA, 20);
        expectedStorage.put(ORANGE, 30);
        expectedStorage.put(PEAR, 0);
        assertEquals(expectedStorage, Storage.storage);
    }

    @Test
    public void test_Negative_ProcessData_Not_Update_Storage_ok() {
        List<FruitTransaction> transactions = Arrays.asList(
                new FruitTransaction(FruitTransaction.Operation.PURCHASE, APPLE, -1),
                new FruitTransaction(FruitTransaction.Operation.RETURN, BANANA, -1),
                new FruitTransaction(FruitTransaction.Operation.SUPPLY, PEAR, -1),
                new FruitTransaction(FruitTransaction.Operation.BALANCE, ORANGE, -1)
        );
        fruitShopService.processData(transactions);
        Map<String, Integer> expectedStorage = new HashMap<>();
        expectedStorage.put(APPLE, 10);
        expectedStorage.put(BANANA, 20);
        expectedStorage.put(ORANGE, 30);
        assertEquals(expectedStorage, Storage.storage);
    }
}
