package basesyntax;

import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.model.FruitTransaction;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {

    @Test
    void setFruit_null_shouldThrowException() {
        FruitTransaction t = new FruitTransaction();
        assertThrows(RuntimeException.class, () -> t.setFruit(null));
    }

    @Test
    void setFruit_blank_shouldThrowException() {
        FruitTransaction t = new FruitTransaction();
        assertThrows(RuntimeException.class, () -> t.setFruit(" "));
    }

    @Test
    void setQuantity_negative_shouldThrowException() {
        FruitTransaction t = new FruitTransaction();
        assertThrows(RuntimeException.class, () -> t.setQuantity(-1));
    }

    @Test
    void setOperation_null_shouldThrowException() {
        FruitTransaction t = new FruitTransaction();
        assertThrows(RuntimeException.class, () -> t.setOperation(null));
    }
}
