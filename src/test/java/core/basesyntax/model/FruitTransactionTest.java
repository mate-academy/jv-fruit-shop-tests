package core.basesyntax.model;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

public class FruitTransactionTest {

    @Test
    public void createFruitTransaction_Ok() {
        String fruitName = "coconut";
        int fruitQuantity = 15;

        FruitTransaction fruitTransactionExpected = new FruitTransaction();
        fruitTransactionExpected.setOperation(Operation.BALANCE);
        fruitTransactionExpected.setFruit(fruitName);
        fruitTransactionExpected.setQuantity(fruitQuantity);

        assertNotNull(fruitTransactionExpected.getOperation());
        assertNotNull(fruitTransactionExpected.getFruit());
        assertNotNull((Integer) (fruitTransactionExpected.getQuantity()));
    }
}
