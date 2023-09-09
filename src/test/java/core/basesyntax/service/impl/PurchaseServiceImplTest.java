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

class PurchaseServiceImplTest {
    private static final String OPERATION_CODE = "p";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String PINEAPPLE = "pineapple";
    private static final String PEACH = "peach";
    private static final int APPLE_NUMBER = 56;
    private static final int BANANA_NUMBER = 43;
    private static final int PINEAPPLE_NUMBER = 98;
    private static final int PEACH_NUMBER = 12;
    private Storage storage;
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    public void init() {
        storage = new Storage();
        purchaseService = new PurchaseServiceImpl(storage);
        Map<String, Integer> data = storage.getData();
        data.put(APPLE, APPLE_NUMBER);
        data.put(BANANA, BANANA_NUMBER);
        data.put(PINEAPPLE, PINEAPPLE_NUMBER);
        data.put(PEACH, PEACH_NUMBER);
    }

    @Test
    public void implementedCorrectInterface_OK() {
        List<Class<?>> interfaces =
                Arrays.asList(purchaseService.getClass().getInterfaces());
        Assertions.assertEquals(1, interfaces.size());
        Assertions.assertTrue(interfaces.contains(OperationService.class));
    }

    @Test
    public void performOperation_purchaseAvailableFruits_OK() {
        int purchased = BANANA_NUMBER - 20;
        FruitTransaction transaction =
                new FruitTransaction(OPERATION_CODE, BANANA, purchased);
        purchaseService.performOperation(transaction);
        int expected = BANANA_NUMBER - purchased;
        int actual = storage.getData().get(BANANA);
        Assertions.assertEquals(expected, actual);

        purchased = PEACH_NUMBER - 11;
        transaction = new FruitTransaction(OPERATION_CODE, PEACH, purchased);
        purchaseService.performOperation(transaction);
        expected = PEACH_NUMBER - purchased;
        actual = storage.getData().get(PEACH);
        Assertions.assertEquals(expected, actual);
    }
}
