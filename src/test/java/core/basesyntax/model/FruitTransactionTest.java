package core.basesyntax.model;

import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static core.basesyntax.model.FruitTransaction.getOperationByLetter;
import static org.junit.Assert.assertEquals;

import java.util.NoSuchElementException;
import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void getOperationByLetter_Ok() {
        assertEquals(BALANCE, getOperationByLetter("b"));
        assertEquals(SUPPLY, getOperationByLetter("s"));
        assertEquals(PURCHASE, getOperationByLetter("p"));
        assertEquals(RETURN, getOperationByLetter("r"));
    }

    @Test (expected = NoSuchElementException.class)
    public void getOperationByLetter_invalidLetter_NotOk() {
        getOperationByLetter("u");
    }

    @Test (expected = NoSuchElementException.class)
    public void getOperationByLetter_nullLetter_NotOk() {
        getOperationByLetter(null);
    }
}
