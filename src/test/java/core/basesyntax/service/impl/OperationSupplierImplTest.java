package core.basesyntax.service.impl;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.OperationSupplier;
import core.basesyntax.strategy.BalanceFruitOperationHandler;
import core.basesyntax.strategy.PurchaseFruitOperationHandler;
import core.basesyntax.strategy.ReturnFruitOperationHandler;
import core.basesyntax.strategy.SupplyFruitOperationHandler;
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
    public void getBalanceOperationSupplierOk() {
        Class<?> expectedClass = BalanceFruitOperationHandler.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("b", "banana",10)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getPurchaseOperationSupplierOk() {
        Class<?> expectedClass = PurchaseFruitOperationHandler.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("p", "banana",10)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getReturnOperationSupplierOk() {
        Class<?> expectedClass = ReturnFruitOperationHandler.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("r", "banana",10)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }

    @Test
    public void getSupplyOperationSupplierOk() {
        Class<?> expectedClass = SupplyFruitOperationHandler.class;
        Class<?> actualClass = operationSupplier
                .getOperation(new Fruit("s", "banana",10)).getClass();
        Assert.assertEquals(expectedClass, actualClass);
    }
}
