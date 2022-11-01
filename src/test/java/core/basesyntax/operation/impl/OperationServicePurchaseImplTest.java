package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
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

    @BeforeClass
    public static void init() {
        purchaseService = new OperationServicePurchaseImpl();

        input = new FruitTransaction();
        input.setFruit(new Fruit("banana"));
        input.setOperation(FruitTransaction.Operation.PURCHASE);
        input.setQuantity(15);
    }

    @Before
    public void setStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_correctFruitTransaction_ok() {
        FruitStorage.storage.put(BANANA, 100);
        purchaseService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(85, actual);
    }

    @Test(expected = RuntimeException.class)
    public void apply_fruitTransactionWithNoInfoAboutFruit_notOk() {
        purchaseService.apply(input);
    }

    @Test(expected = RuntimeException.class)
    public void apply_lessFruitThanInStorage_notOk() {
        FruitStorage.storage.put(BANANA, 10);
        purchaseService.apply(input);
    }
}
