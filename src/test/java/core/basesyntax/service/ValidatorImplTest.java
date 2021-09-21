package core.basesyntax.service;

import core.basesyntax.dto.Transaction;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ValidatorImplTest {
    private static Validator validator;

    @BeforeClass
    public static void beforeClass() {
        validator = new ValidatorImpl();
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkInput_sizeData_not_ok() {
        int size = 10;
        String[] data = new String[size];
        validator.checkInputData(data);
    }

    @Test
    public void validator_BalanceOperation_PositiveQuality_OK() {
        String[] data = new String[] {"b", "apple", "13"};
        boolean actual = validator.checkInputData(data);
        Assert.assertTrue(actual);
    }

    @Test
    public void validator_PurchaseOperation_PositiveQuality_OK() {
        String[] data = new String[] {"p", "apple", "13"};
        boolean actual = validator.checkInputData(data);
        Assert.assertTrue(actual);
    }

    @Test
    public void validator_SupplyOperation_PositiveQuality_OK() {
        String[] data = new String[] {"s", "apple", "13"};
        boolean actual = validator.checkInputData(data);
        Assert.assertTrue(actual);
    }

    @Test
    public void validator_findOperationBalance_CorrectOperation_OK() {
        String data = "b";
        Transaction.Operation expected = Transaction.Operation.BALANCE;
        Transaction.Operation actual = ValidatorImpl.findOperation(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validator_findOperationSupply_CorrectOperation_OK() {
        String data = "s";
        Transaction.Operation expected = Transaction.Operation.SUPPLY;
        Transaction.Operation actual = ValidatorImpl.findOperation(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validator_findOperationPurchase_CorrectOperation_OK() {
        String data = "p";
        Transaction.Operation expected = Transaction.Operation.PURCHASE;
        Transaction.Operation actual = ValidatorImpl.findOperation(data);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void validator_findOperationReturn_CorrectOperation_OK() {
        String data = "r";
        Transaction.Operation expected = Transaction.Operation.RETURN;
        Transaction.Operation actual = ValidatorImpl.findOperation(data);
        Assert.assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void validator_findOperation_IncorrectOperation_not_ok() {
        String data = "i";
        ValidatorImpl.findOperation(data);
    }

    @Test
    public void validator_checkOperation_CorrectOperation() {
        int newQuantity = 20;
        Assert.assertTrue(validator.checkOperation(newQuantity));
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkOperation_IncorrectOperation() {
        int newQuantity = -20;
        Assert.assertTrue(validator.checkOperation(newQuantity));
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkOperation_IncorrectOperation_not_ok() {
        String[] data = new String[]{"i", "apple", "13"};
        validator.checkInputData(data);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkInput_NegativeQuantity_not_ok() {
        String[] data = new String[]{"s", "apple", "-13"};
        validator.checkInputData(data);
    }

    @Test(expected = RuntimeException.class)
    public void validator_checkInput_NoFruit_not_ok() {
        String[] data = new String[]{"s", "", "13"};
        validator.checkInputData(data);
    }
}
