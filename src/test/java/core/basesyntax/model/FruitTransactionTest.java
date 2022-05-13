package core.basesyntax.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void findOperationByLetter_Ok() {
        FruitTransaction.Operation actual1 = FruitTransaction.Operation.findOperationByLetter("p");
        FruitTransaction.Operation expected1 = FruitTransaction.Operation.PURCHASE;
        assertEquals(expected1, actual1);
        FruitTransaction.Operation actual2 = FruitTransaction.Operation.findOperationByLetter("b");
        FruitTransaction.Operation expected2 = FruitTransaction.Operation.BALANCE;
        assertEquals(expected2, actual2);
        FruitTransaction.Operation actual3 = FruitTransaction.Operation.findOperationByLetter("s");
        FruitTransaction.Operation expected3 = FruitTransaction.Operation.SUPPLY;
        assertEquals(expected3, actual3);
        FruitTransaction.Operation actual4 = FruitTransaction.Operation.findOperationByLetter("r");
        FruitTransaction.Operation expected4 = FruitTransaction.Operation.RETURN;
        assertEquals(expected4, actual4);
    }

    @Test
    public void findOperationByLetter_OperationDoesntExist_NotOk() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.findOperationByLetter("w");
        assertNull(actual);
    }

    @Test(expected = RuntimeException.class)
    public void findOperationByLetter_Null_NotOk() {
        FruitTransaction.Operation actual = FruitTransaction.Operation.findOperationByLetter(null);
    }

    @Test(expected = RuntimeException.class)
    public void findOperationByLetter_CharacterOrNumber_NotOk() {
        FruitTransaction.Operation character = FruitTransaction.Operation
                .findOperationByLetter("*");
        FruitTransaction.Operation number = FruitTransaction.Operation
                .findOperationByLetter("6");
    }
}
