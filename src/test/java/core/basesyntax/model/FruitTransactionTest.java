package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void check_returnOperation_Exception() {
        Assertions.assertThrows(IllegalArgumentException.class, ()
                -> FruitTransaction.Operation.returnOperation("l"));
    }

    @Test
    void check_returnOperation_Ok() {
        Assertions.assertNotNull(FruitTransaction.Operation.returnOperation("b"));
    }
}
