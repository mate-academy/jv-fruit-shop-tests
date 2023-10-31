package core.basesyntax.model;

import core.basesyntax.Constants;
import org.junit.Assert;
import org.junit.Test;

public class FruitTransactionTest {
    private static final int BALANCE_QUANTITY = 10;
    private static final int SUPPLY_QUANTITY = 5;
    private static final int PURCHASE_QUANTITY = 8;
    private static final int RETURN_QUANTITY = 3;
    private static final int UPDATED_QUANTITY = 20;

    @Test
    public void getOperation_BalanceState_ReturnsBalanceOperation() {
        FruitTransaction transaction
                = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.APPLE, BALANCE_QUANTITY);
        Assert.assertEquals(FruitTransaction.Operation.BALANCE, transaction.getOperation());
    }

    @Test
    public void getFruit_SupplyState_ReturnsCorrectFruit() {
        FruitTransaction transaction
                = new FruitTransaction(
                FruitTransaction.Operation.SUPPLY,
                Constants.ORANGE, SUPPLY_QUANTITY);
        Assert.assertEquals(Constants.ORANGE, transaction.getFruit());
    }

    @Test
    public void getQuantity_PurchaseState_ReturnsCorrectQuantity() {
        FruitTransaction transaction
                = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                Constants.BANANA, PURCHASE_QUANTITY);
        Assert.assertEquals(PURCHASE_QUANTITY, transaction.getQuantity());
    }

    @Test
    public void setOperation_ReturnState_ReturnsUpdatedOperation() {
        FruitTransaction transaction
                = new FruitTransaction(
                FruitTransaction.Operation.RETURN,
                Constants.MANGO, RETURN_QUANTITY);
        transaction.setOperation(FruitTransaction.Operation.SUPPLY);
        Assert.assertEquals(FruitTransaction.Operation.SUPPLY, transaction.getOperation());
    }

    @Test
    public void setFruit_PurchaseState_ReturnsUpdatedFruit() {
        FruitTransaction transaction
                = new FruitTransaction(
                FruitTransaction.Operation.PURCHASE,
                Constants.GRAPES, PURCHASE_QUANTITY);
        transaction.setFruit(Constants.PINEAPPLE);
        Assert.assertEquals(Constants.PINEAPPLE, transaction.getFruit());
    }

    @Test
    public void setQuantity_BalanceState_ReturnsUpdatedQuantity() {
        FruitTransaction transaction
                = new FruitTransaction(
                FruitTransaction.Operation.BALANCE,
                Constants.CHERRY, BALANCE_QUANTITY);
        transaction.setQuantity(UPDATED_QUANTITY);
        Assert.assertEquals(UPDATED_QUANTITY, transaction.getQuantity());
    }

    @Test
    public void operationCode_ReturnsCorrectCodes() {
        Assert.assertEquals(Constants.BALANCE_SHORTHAND,
                FruitTransaction.Operation.BALANCE.getCode());

        Assert.assertEquals(Constants.SUPPLY_SHORTHAND,
                FruitTransaction.Operation.SUPPLY.getCode());

        Assert.assertEquals(Constants.PURCHASE_SHORTHAND,
                FruitTransaction.Operation.PURCHASE.getCode());

        Assert.assertEquals(Constants.RETURN_SHORTHAND,
                FruitTransaction.Operation.RETURN.getCode());
    }
}
