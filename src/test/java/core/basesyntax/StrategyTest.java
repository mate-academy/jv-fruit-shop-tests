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
    private static final FruitTransaction BALANCE_TRANSACTION =
            new FruitTransaction(
                    FruitTransaction.Operation.BALANCE, "peach", 50);

    private static final FruitTransaction PURCHASE_TRANSACTION =
            new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, "peach", 37);

    private static final FruitTransaction PURCHASE_TRANSACTION_MORE_THAN_WE_HAVE =
            new FruitTransaction(
                    FruitTransaction.Operation.PURCHASE, "peach", 66);

    private static final FruitTransaction SUPPLY_TRANSACTION =
            new FruitTransaction(
                    FruitTransaction.Operation.SUPPLY, "strawberry", 7);

    private static final FruitTransaction RETURN_TRANSACTION =
            new FruitTransaction(
                    FruitTransaction.Operation.RETURN, "orange", 19);

    private BalanceStrategy balanceStrategy = new BalanceStrategy();
    private PurchaseStrategy purchaseStrategy = new PurchaseStrategy();
    private ReturnStrategy returnStrategy = new ReturnStrategy();
    private SupplyStrategy supplyStrategy = new SupplyStrategy();

    @Test
    public void applyBalanceStrategyOk() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("peach", 50);
        balanceStrategy.apply(fruitStorage, BALANCE_TRANSACTION);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }

    @Test
    public void applyPurchaseStrategyOk() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("peach", 13);
        balanceStrategy.apply(fruitStorage, BALANCE_TRANSACTION);
        purchaseStrategy.apply(fruitStorage, PURCHASE_TRANSACTION);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }

    @Test
    public void applyPurchaseStrategyWrongThrowException() {
        FruitStorage fruitStorage = new FruitStorage();
        balanceStrategy.apply(fruitStorage, BALANCE_TRANSACTION);
        Assert.assertThrows(InsufficientInventoryException.class, () ->
                purchaseStrategy.apply(fruitStorage, PURCHASE_TRANSACTION_MORE_THAN_WE_HAVE));
    }

    @Test
    public void applySupplyStrategyOk() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("strawberry", 7);
        supplyStrategy.apply(fruitStorage, SUPPLY_TRANSACTION);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }

    @Test
    public void applyReturnStrategyOk() {
        FruitStorage fruitStorageExpected = new FruitStorage();
        FruitStorage fruitStorage = new FruitStorage();
        fruitStorageExpected.addQuantity("orange", 19);
        returnStrategy.apply(fruitStorage, RETURN_TRANSACTION);
        Assert.assertEquals(fruitStorage, fruitStorageExpected);
    }
}
