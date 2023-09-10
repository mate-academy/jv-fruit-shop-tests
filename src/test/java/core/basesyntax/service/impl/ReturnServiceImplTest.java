package core.basesyntax.service.impl;

import core.basesyntax.db.Storage;
import core.basesyntax.model.FruitTransaction;
import java.util.Map;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReturnServiceImplTest {
    private static final String OPERATION_CODE = "r";
    private static final String APPLE = "apple";
    private static final String BANANA = "banana";
    private static final String PINEAPPLE = "pineapple";
    private static final String PEACH = "peach";
    private static final int APPLE_QUANTITY = 56;
    private static final int BANANA_QUANTITY = 43;
    private static final int PINEAPPLE_QUANTITY = 98;
    private static final int PEACH_QUANTITY = 12;
    private Storage storage;
    private ReturnServiceImpl returnService;

    @BeforeEach
    public void init() {
        storage = new Storage();
        returnService = new ReturnServiceImpl(storage);
        Map<String, Integer> data = storage.getData();
        data.put(APPLE, APPLE_QUANTITY);
        data.put(BANANA, BANANA_QUANTITY);
        data.put(PINEAPPLE, PINEAPPLE_QUANTITY);
        data.put(PEACH, PEACH_QUANTITY);
    }

    @Test
    public void performOperation_returnFruitTransactionToStorage_OK() {
        String fruit = BANANA;
        int quantity = 76;
        FruitTransaction transaction =
                new FruitTransaction(OPERATION_CODE, fruit, quantity);
        returnService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        int expected = BANANA_QUANTITY + quantity;
        int actual = storage.getData().get(fruit);
        Assertions.assertEquals(expected, actual);

        fruit = PINEAPPLE;
        quantity = 123;
        transaction = new FruitTransaction(OPERATION_CODE, fruit, quantity);
        returnService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        expected = PINEAPPLE_QUANTITY + quantity;
        actual = storage.getData().get(fruit);
        Assertions.assertEquals(expected, actual);
    }

    @Test
    public void performOperation_returnFruitThatWasntInStorage_OK() {
        String fruit = "Coconut";
        int quantity = 2;
        Assertions.assertFalse(storage.getData().containsKey(fruit));
        FruitTransaction transaction =
                new FruitTransaction(OPERATION_CODE, fruit, quantity);
        returnService.performOperation(transaction);
        Assertions.assertTrue(storage.getData().containsKey(fruit));
        Assertions.assertEquals(quantity, storage.getData().get(fruit));
    }
}
