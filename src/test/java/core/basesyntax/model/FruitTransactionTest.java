package core.basesyntax.model;

import static org.junit.jupiter.api.Assertions.fail;

import core.basesyntax.service.impl.ErrorDataException;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void fruitTransaction_negativeQuantity_NotOk() {
        try {
            FruitTransaction fruitTransaction = new FruitTransaction(Operation.SUPPLY,
                    "any fruit", -3);
        } catch (ErrorDataException e) {
            return;
        }
        fail("If quantity is negative ErrorDataException should be thrown");

    }
}
