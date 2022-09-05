package core.basesyntax.model;

import org.junit.Test;
import java.util.NoSuchElementException;
import static core.basesyntax.model.FruitTransaction.Operation.BALANCE;
import static core.basesyntax.model.FruitTransaction.Operation.PURCHASE;
import static core.basesyntax.model.FruitTransaction.Operation.RETURN;
import static core.basesyntax.model.FruitTransaction.Operation.SUPPLY;
import static core.basesyntax.model.FruitTransaction.getOperationByLetter;
import static org.junit.Assert.assertEquals;

public class FruitTransactionTest {

    @Test
    public void getExistingOperation_Ok() {
        assertEquals(BALANCE, getOperationByLetter("b"));
        assertEquals(SUPPLY, getOperationByLetter("s"));
        assertEquals(PURCHASE, getOperationByLetter("p"));
        assertEquals(RETURN, getOperationByLetter("r"));
    }

    @Test (expected = NoSuchElementException.class)
    public void getByInvalidLetter_NotOk() {
        getOperationByLetter("u");
    }

    @Test (expected = NoSuchElementException.class)
    public void getByNull_NotOk() {
        getOperationByLetter(null);
    }
}
