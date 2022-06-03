package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.NoSuchElementException;
import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void findOperationByLetter_Ok() {
        FruitTransaction.Operation actualBalance =
                FruitTransaction.Operation.findByLetter("b");
        FruitTransaction.Operation expectedBalance =
                FruitTransaction.Operation.BALANCE;
        assertEquals(expectedBalance, actualBalance);

        FruitTransaction.Operation actualSupply
                = FruitTransaction.Operation.findByLetter("s");
        FruitTransaction.Operation expectedSupply
                = FruitTransaction.Operation.SUPPLY;
        assertEquals(expectedSupply, actualSupply);

        FruitTransaction.Operation actualPurchase
                = FruitTransaction.Operation.findByLetter("p");
        FruitTransaction.Operation expectedPurchase
                = FruitTransaction.Operation.PURCHASE;
        assertEquals(expectedPurchase, actualPurchase);

        FruitTransaction.Operation actualReturn
                = FruitTransaction.Operation.findByLetter("r");
        FruitTransaction.Operation expectedReturn
                = FruitTransaction.Operation.RETURN;
        assertEquals(expectedReturn, actualReturn);
    }

    @Test
    public void findOperation_NonValidLetter_NotOK() {
        try {
            FruitTransaction.Operation.findByLetter("v");
        } catch (Exception e) {
            assertSame(NoSuchElementException.class, e.getClass());
        }
    }

    @Test
    public void findOperation_NullValue_NotOk() {
        try {
            FruitTransaction.Operation.findByLetter(null);
        } catch (Exception e) {
            assertSame(NoSuchElementException.class, e.getClass());
        }
    }

    @Test
    public void findOperation_NonValidCharacter_NotOk() {
        try {
            FruitTransaction.Operation.findByLetter("-");
        } catch (Exception e) {
            assertSame(NoSuchElementException.class, e.getClass());
        }
    }

    @Test
    public void findOperation_NumberValue_NotOk() {
        try {
            FruitTransaction.Operation.findByLetter("10");
        } catch (Exception e) {
            assertSame(NoSuchElementException.class, e.getClass());
        }
    }
}
