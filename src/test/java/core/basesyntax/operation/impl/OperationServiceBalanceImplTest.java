package core.basesyntax.operation.impl;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.model.Fruit;
import core.basesyntax.model.FruitTransaction;
import core.basesyntax.operation.OperationService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationServiceBalanceImplTest {
    private static OperationService balanceService;
    private static final Fruit BANANA = new Fruit("banana");
    private static FruitTransaction input;
    private static final int COUNT_BANANA = 100;
    private static final int EXPECTED_COUNT_BANANA = 101;

    @BeforeClass
    public static void init() {
        balanceService = new OperationServiceBalanceImpl();

        input = new FruitTransaction();
        input.setFruit(BANANA);
        input.setOperation(FruitTransaction.Operation.BALANCE);
        input.setQuantity(COUNT_BANANA);
    }

    @Before
    public void setStorage() {
        FruitStorage.storage.clear();
    }

    @Test
    public void apply_correctFruitTransactionContainInStorage_ok() {
        FruitStorage.storage.put(BANANA,1);
        balanceService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(EXPECTED_COUNT_BANANA, actual);
    }

    @Test
    public void apply_correctFruitTransactionNotContainInStorage_ok() {
        balanceService.apply(input);
        int actual = FruitStorage.storage.get(BANANA);
        Assert.assertEquals(COUNT_BANANA, actual);
    }
}
