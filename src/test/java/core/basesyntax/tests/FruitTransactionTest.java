package core.basesyntax.tests;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.FruitTransaction;
import org.junit.Before;
import org.junit.Test;

public class FruitTransactionTest {
    private FruitTransaction fruitTransaction;

    @Before
    public void setUp() throws Exception {
        fruitTransaction = new FruitTransaction();
    }

    @Test
    public void fruitTransaction_CorrectData_Ok() {
        fruitTransaction.setOperation("b");
        assertEquals(FruitTransaction.Operation.BALANCE, fruitTransaction.getOperation());
        fruitTransaction.setOperation("r");
        assertEquals(FruitTransaction.Operation.RETURN, fruitTransaction.getOperation());
        fruitTransaction.setOperation("s");
        assertEquals(FruitTransaction.Operation.SUPPLY, fruitTransaction.getOperation());
        fruitTransaction.setOperation("p");
        assertEquals(FruitTransaction.Operation.PURCHASE, fruitTransaction.getOperation());
    }

    @Test (expected = RuntimeException.class)
    public void fruitTransaction_IncorrectData_NotOk() {
        fruitTransaction.setOperation("inCorrect");
    }
}
