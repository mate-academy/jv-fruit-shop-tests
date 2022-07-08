package core.basesyntax.service.impl;

import static org.junit.Assert.*;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationSupplier;
import core.basesyntax.strategy.BalanceFruitOperation;
import core.basesyntax.strategy.PurchaseFruitOperation;
import core.basesyntax.strategy.ReturnFruitOperation;
import core.basesyntax.strategy.SupplyFruitOperation;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class OperationSupplierImplTest {
    private static OperationSupplier operationSupplier;

    @BeforeClass
    public static void setUp() {
        operationSupplier = new OperationSupplierImpl();
    }

    @Test
    public void getBalanceFruitOperation_validDate_ok() {
        Class<?> expectedClass = BalanceFruitOperation.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("b", "banana", 10))
                .getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getPurchaseFruitOperation_validDate_ok() {
        Class<?> expectedClass = PurchaseFruitOperation.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("p", "banana", 10))
                .getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getReturnFruitOperation_validDate_ok() {
        Class<?> expectedClass = ReturnFruitOperation.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("r", "banana", 10))
                .getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getSupplyFruitOperation_validDate_ok() {
        Class<?> expectedClass = SupplyFruitOperation.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("s", "banana", 10))
                .getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }
}
