package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
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
    private static final int APPLE_QUANTITY = 56;
    private static final int BANANA_QUANTITY = 43;
    private static final int PINEAPPLE_QUANTITY = 98;
    private static final int PEACH_QUANTITY = 12;
    private Storage storage;
    private PurchaseServiceImpl purchaseService;

    @BeforeEach
    public void init() {
        storage = new Storage();
        purchaseService = new PurchaseServiceImpl(storage);
        Map<String, Integer> data = storage.getData();
        data.put(APPLE, APPLE_QUANTITY);
        data.put(BANANA, BANANA_QUANTITY);
        data.put(PINEAPPLE, PINEAPPLE_QUANTITY);
        data.put(PEACH, PEACH_QUANTITY);
    }

    @Test
    public void performOperation_purchaseAvailableFruits_OK() {
        int purchased = BANANA_QUANTITY - 20;
        FruitTransaction transaction =
                new FruitTransaction(OPERATION_CODE, BANANA, purchased);
        purchaseService.performOperation(transaction);
        int expected = BANANA_QUANTITY - purchased;
        int actual = storage.getData().get(BANANA);
        Assertions.assertEquals(expected, actual);

        purchased = PEACH_QUANTITY - 11;
        transaction = new FruitTransaction(OPERATION_CODE, PEACH, purchased);
        purchaseService.performOperation(transaction);
        expected = PEACH_QUANTITY - purchased;
        actual = storage.getData().get(PEACH);
        Assertions.assertEquals(expected, actual);
    }
}
