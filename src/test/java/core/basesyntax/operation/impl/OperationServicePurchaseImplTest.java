package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.exception.FruitLessThanNeedException;
import core.basesyntax.exception.NoSuchFruitInStorageException;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServicePurchaseImplTest {
    private static OperationService purchaseService;
    private static final Fruit BANANA = new Fruit("banana");
    private static FruitTransaction input;
    private static final int COUNT_BANANA_FIRST_TEST = 100;
    private static final int COUNT_BANANA_SECOND_TEST = 10;
    private static final int COUNT_PURCHASE_BANANA = 15;
    private static final int EXPECTED_COUNT_BANANA = 85;

    @BeforeClass
    public static void init() {
        purchaseService = new OperationServicePurchaseImpl();

        input = new FruitTransaction();
        input.setFruit(BANANA);
        input.setOperation(FruitTransaction.Operation.PURCHASE);
        input.setQuantity(COUNT_PURCHASE_BANANA);
    }

    @Before
    public void setStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_correctFruitTransaction_ok() {
        FruitStorage.storage.put(BANANA, COUNT_BANANA_FIRST_TEST);
        purchaseService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(EXPECTED_COUNT_BANANA, actual);
    }

    @Test(expected = NoSuchFruitInStorageException.class)
    public void apply_fruitTransactionWithNoInfoAboutFruit_notOk() {
        purchaseService.apply(input);
    }

    @Test(expected = FruitLessThanNeedException.class)
    public void apply_lessFruitThanInStorage_notOk() {
        FruitStorage.storage.put(BANANA, COUNT_BANANA_SECOND_TEST);
        purchaseService.apply(input);
    }
}
