package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.service.OperationService;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class SupplyServiceImplTest {
    private static final String OPERATION_CODE = "s";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String PINEAPPLE = "pineapple";
    private static final String PEACH = "peach";
    private static final int APPLE_NUMBER = 56;
    private static final int BANANA_NUMBER = 43;
    private static final int PINEAPPLE_NUMBER = 98;
    private static final int PEACH_NUMBER = 12;
    private Storage storage;
    private SupplyServiceImpl supplyService;

    @BeforeEach
    public void init() {
        storage = new Storage();
        supplyService = new SupplyServiceImpl(storage);
        Map<String, Integer> data = storage.getData();
        data.put(APPLE, APPLE_NUMBER);
        data.put(BANANA, BANANA_NUMBER);
        data.put(PINEAPPLE, PINEAPPLE_NUMBER);
        data.put(PEACH, PEACH_NUMBER);
    }

    @Test
    public void implementsCorrectInterface_OK() {
        List<Class<?>> interfaces =
                Arrays.asList(supplyService.getClass().getInterfaces());
        Assertions.assertEquals(1, interfaces.size());
        Assertions.assertTrue(interfaces.contains(OperationService.class));
    }

    @Test
    public void performOperation_supplyFruitToStorage_OK() {
        String fruit = BANANA;
        int quantity = 76;
        FruitTransaction transaction =
                new FruitTransaction(OPERATION_CODE, fruit, quantity);
        supplyService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        int expected = BANANA_NUMBER + quantity;
        int actual = storage.getData().get(fruit);
        Assertions.assertEquals(expected, actual);

        fruit = PINEAPPLE;
        quantity = 123;
        transaction = new FruitTransaction(OPERATION_CODE, fruit, quantity);
        supplyService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        expected = PINEAPPLE_NUMBER + quantity;
        actual = storage.getData().get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void performOperation_supplyNewFruit_OK() {
        String fruit = "Coconut";
        int quantity = 2;
        Assertions.assertFalse(storage.getData().containsKey(fruit));
        FruitTransaction transaction =
                new FruitTransaction(OPERATION_CODE, fruit, quantity);
        supplyService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        Assertions.assertEquals(quantity, storage.getData().get(fruit));
    }
}
