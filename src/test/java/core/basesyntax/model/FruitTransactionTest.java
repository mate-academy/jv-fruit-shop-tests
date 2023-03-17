package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class FruitTransactionTest {
    private static final String KEY = "banana";
    private static final Integer VALUE = 100;
    private static final String WRONG_OPERATION = "l";
    private static final FruitTransaction FRUIT_TRANSACTION = new FruitTransaction(
            FruitTransaction.Operation.SUPPLY, KEY, VALUE);

    @Test
    void get_Operation_ok() {
        assertEquals(FRUIT_TRANSACTION.getOperation(),
                FruitTransaction.Operation.SUPPLY);
    }

    @Test
    void get_Operation_notOk() {
        assertThrows(RuntimeException.class, () ->
                FruitTransaction.Operation.getOperation(WRONG_OPERATION));
    }

    @Test
    void objects_similar_ok() {
        FruitTransaction fruitExcepted = new FruitTransaction();
        fruitExcepted.setFruit(KEY);
        fruitExcepted.setQuantity(VALUE);
        fruitExcepted.setOperation(FruitTransaction.Operation.SUPPLY);
        assertEquals(FRUIT_TRANSACTION, fruitExcepted);
    }
}
