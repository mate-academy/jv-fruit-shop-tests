package core.basesyntax.model;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void getOperationType_Ok() {
        String actualOperationType = "s";
        FruitTransaction.Operation actual = FruitTransaction.Operation.SUPPLY;
        FruitTransaction expectedFruitTransaction =
                new FruitTransaction(actualOperationType, new Fruit("pie"),10);
        FruitTransaction.Operation expected = expectedFruitTransaction.getOperationType();
        assertEquals(expected, actual);
    }
}
