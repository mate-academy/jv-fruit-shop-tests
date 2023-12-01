package core.basesyntax;

import db.FruitStorage;
import model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;
import strategy.BalanceStrategy;
import strategy.PurchaseStrategy;
import strategy.ReturnStrategy;
import strategy.SupplyStrategy;
import util.InsufficientInventoryException;

public class StrategyTest {
    private static final BalanceStrategy balanceStrategy = new BalanceStrategy();
    private static final PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
    private static final ReturnStrategy returnStrategy = new ReturnStrategy();
    private static final SupplyStrategy supplyStrategy = new SupplyStrategy();

    private static final FruitTransaction balanceTransaction =
            new FruitTransaction(
                    FruitTransaction.Operation.BALANCE, "peach", 50);

    private static final FruitTransaction purchaseTransaction =
            new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, "peach", 37);

    private static final FruitTransaction purchaseTransactionMoreThanWeHave =
            new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, "peach", 66);

    private static final FruitTransaction supplyTransaction =
            new FruitTransaction(
                    FruitTransaction.Operation.SUPPLY, "strawberry", 7);

    private static final FruitTransaction returnTransaction =
            new FruitTransaction(
                    FruitTransaction.Operation.RETURN, "orange", 19);

    @Test
    public void testBalanceStrategy() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("peach", 50);
        balanceStrategy.apply(fruitStorage, balanceTransaction);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }

    @Test
    public void testPurchaseStrategy() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("peach", 13);
        balanceStrategy.apply(fruitStorage, balanceTransaction);
        purchaseStrategy.apply(fruitStorage, purchaseTransaction);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }

    @Test
    public void testPurchaseStrategyMoreThanHave() {
        FruitStorage fruitStorage = new FruitStorage();
        balanceStrategy.apply(fruitStorage, balanceTransaction);
        Assert.assertThrows(InsufficientInventoryException.class, () ->
                purchaseStrategy.apply(fruitStorage, purchaseTransactionMoreThanWeHave));
    }

    @Test
    public void testSupplyStrategy() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("strawberry", 7);
        supplyStrategy.apply(fruitStorage, supplyTransaction);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }

    @Test
    public void testReturnStrategy() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("orange", 19);
        returnStrategy.apply(fruitStorage, returnTransaction);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }
}
