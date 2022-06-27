package core.basesyntax.strategy;

import core.basesyntax.model.FruitTransaction;
import org.junit.Assert;
import org.junit.Test;

public class OperationStrategyImplTest {
    @Test
    public void get_inputIsNull_notOk() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction balanceTransaction = new FruitTransaction(null, "apple", 20);
        FruitTransaction.Operation actual = balanceTransaction.getOperation();
        Assert.assertNotEquals(expected, actual);
    }

    @Test
    public void get_operationBalance_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.BALANCE;
        FruitTransaction balanceTransaction = new FruitTransaction("b", "apple", 20);
        FruitTransaction.Operation actual = balanceTransaction.getOperation();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_operationReturn_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.RETURN;
        FruitTransaction balanceTransaction = new FruitTransaction("r", "apple", 20);
        FruitTransaction.Operation actual = balanceTransaction.getOperation();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_operationSupply_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        FruitTransaction balanceTransaction = new FruitTransaction("s", "apple", 20);
        FruitTransaction.Operation actual = balanceTransaction.getOperation();
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void get_operationPurchase_ok() {
        FruitTransaction.Operation expected = FruitTransaction.Operation.PURCHASE;
        FruitTransaction balanceTransaction = new FruitTransaction("p", "apple", 20);
        FruitTransaction.Operation actual = balanceTransaction.getOperation();
        Assert.assertEquals(expected, actual);
    }
}
