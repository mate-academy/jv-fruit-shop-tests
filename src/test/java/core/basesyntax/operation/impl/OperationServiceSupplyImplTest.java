package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceSupplyImplTest {
    private static OperationService supplyService;
    private static final Fruit BANANA = new Fruit("banana");
    private static FruitTransaction input;

    @BeforeClass
    public static void init() {
        supplyService = new OperationServiceSupplyImpl();

        input = new FruitTransaction();
        input.setFruit(new Fruit("banana"));
        input.setOperation(FruitTransaction.Operation.RETURN);
        input.setQuantity(15);
    }

    @Before
    public void setStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_correctFruitTransactionContainInStorage_ok() {
        FruitStorage.storage.put(BANANA, 10);
        supplyService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(25, actual);
    }

    @Test
    public void apply_correctFruitTransactionNotContainInStorage_ok() {
        supplyService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(15, actual);
    }
}
