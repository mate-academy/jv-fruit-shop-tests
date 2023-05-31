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
    private static final int COUNT_BANANA_TEST = 10;
    private static final int COUNT_SUPPLY_BANANA = 15;
    private static final int EXPECTED_COUNT_BANANA_FIRST_TEST = 25;
    private static final int EXPECTED_COUNT_BANANA_SECOND_TEST = 15;

    @BeforeClass
    public static void init() {
        supplyService = new OperationServiceSupplyImpl();

        input = new FruitTransaction();
        input.setFruit(BANANA);
        input.setOperation(FruitTransaction.Operation.SUPPLY);
        input.setQuantity(COUNT_SUPPLY_BANANA);
    }

    @Before
    public void setStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_correctFruitTransactionContainInStorage_ok() {
        FruitStorage.storage.put(BANANA, COUNT_BANANA_TEST);
        supplyService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(EXPECTED_COUNT_BANANA_FIRST_TEST, actual);
    }

    @Test
    public void apply_correctFruitTransactionNotContainInStorage_ok() {
        supplyService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(EXPECTED_COUNT_BANANA_SECOND_TEST, actual);
    }
}
