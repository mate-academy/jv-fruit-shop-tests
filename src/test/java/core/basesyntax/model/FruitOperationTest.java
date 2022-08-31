package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitOperationTest {
    @Test
    public void defineOperationByLetter_Ok() {
        FruitOperation.Operation actualPurchase = FruitOperation.Operation
                .defineOperationByLetter("p");
        FruitOperation.Operation expectedPurchase = FruitOperation.Operation.PURCHASE;
        assertEquals(expectedPurchase, actualPurchase);

        FruitOperation.Operation actualBalance = FruitOperation.Operation
                .defineOperationByLetter("b");
        FruitOperation.Operation expectedBalance = FruitOperation.Operation.BALANCE;
        assertEquals(expectedBalance, actualBalance);

        FruitOperation.Operation actualSupply = FruitOperation.Operation
                .defineOperationByLetter("s");
        FruitOperation.Operation expectedSupply = FruitOperation.Operation.SUPPLY;
        assertEquals(expectedSupply, actualSupply);

        FruitOperation.Operation actualReturn = FruitOperation.Operation
                .defineOperationByLetter("r");
        FruitOperation.Operation expectedReturn = FruitOperation.Operation.RETURN;
        assertEquals(expectedReturn, actualReturn);
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_nonExistentOperation_NotOk() {
        FruitOperation.Operation actual = FruitOperation.Operation.defineOperationByLetter("o");
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_nullOperation_NotOk() {
        FruitOperation.Operation actual = FruitOperation.Operation.defineOperationByLetter(null);
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_Character_NotOk() {
        FruitOperation.Operation character = FruitOperation.Operation.defineOperationByLetter("-");
    }

    @Test(expected = RuntimeException.class)
    public void findOperation_Number_NotOk() {
        FruitOperation.Operation number = FruitOperation.Operation.defineOperationByLetter("1");
    }
}
