package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceReturnImplTest {
    private static OperationService returnService;
    private static final Fruit BANANA = new Fruit("banana");
    private static FruitTransaction input;

    @BeforeClass
    public static void init() {
        returnService = new OperationServiceReturnImpl();

        input = new FruitTransaction();
        input.setFruit(BANANA);
        input.setOperation(FruitTransaction.Operation.RETURN);
        input.setQuantity(15);
    }

    @Before
    public void setStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_correctFruitTransactionContainInStorage_ok() {
        FruitStorage.storage.put(BANANA,10);
        returnService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(25, actual);
    }

    @Test
    public void apply_correctFruitTransactionNotContainInStorage_ok() {
        returnService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(15, actual);
    }
}
