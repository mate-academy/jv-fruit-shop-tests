package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void findOperationByLetter_Ok() {
        FruitTransaction.Operation actualPurchase = FruitTransaction.Operation
                .findOperationByLetter("p");
        FruitTransaction.Operation expectedPurchase = FruitTransaction.Operation.PURCHASE;
        assertEquals(expectedPurchase, actualPurchase);
        FruitTransaction.Operation actualBalance = FruitTransaction.Operation
                .findOperationByLetter("b");
        FruitTransaction.Operation expectedBalance = FruitTransaction.Operation.BALANCE;
        assertEquals(expectedBalance, actualBalance);
        FruitTransaction.Operation actualSupply = FruitTransaction.Operation
                .findOperationByLetter("s");
        FruitTransaction.Operation expectedSupply = FruitTransaction.Operation.SUPPLY;
        assertEquals(expectedSupply, actualSupply);
        FruitTransaction.Operation actualReturn = FruitTransaction.Operation
                .findOperationByLetter("r");
        FruitTransaction.Operation expectedReturn = FruitTransaction.Operation.RETURN;
        assertEquals(expectedReturn, actualReturn);
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_nonExistentOperation_NotOk() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.findOperationByLetter("w");
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_nullOperation_NotOk() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.findOperationByLetter(null);
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_Character_NotOk() {
        FruitTransaction.Operation character = FruitTransaction.Operation
                .findOperationByLetter("*");
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_Number_NotOk() {
        FruitTransaction.Operation number = FruitTransaction.Operation
                .findOperationByLetter("6");
    }
}
