package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {
    private static final String TEST_OPERATION = "s";

    @Test
    public void getValidOperationByLetter_Ok() {
        FruitTransaction.Operation actual =
                FruitTransaction.Operation.BALANCE.getOperationByLetter(TEST_OPERATION);
        FruitTransaction.Operation expected = FruitTransaction.Operation.SUPPLY;
        assertEquals(expected,actual);
    }
}
