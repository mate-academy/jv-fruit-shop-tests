package core.basesyntax.fruitscontent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void transation_GetCorrectEnum_Ok() {
        String operation = "b";
        String expected = String.valueOf(FruitTransaction.Operation.BALANCE);
        String actual = String.valueOf(FruitTransaction.Operation.getByCode(operation));
        assertEquals(expected, actual);
    }

    @Test
    public void transation_GetIncorrectEnum_NotOk() {
        String operation = "n";
        try {
            FruitTransaction.Operation.getByCode(operation);
        } catch (RuntimeException e) {
            return;
        }
        fail("Something went wrong with your value...");
    }
}
