package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionTest {
    private static final String FRUIT_NAME = "testfruit";
    private static final int FRUIT_QUANTITTY = 123;
    private final BalanceTransaction balanceTransaction = new BalanceTransaction();

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void process_inputCorrect_ok() {
        balanceTransaction.process(FRUIT_NAME, FRUIT_QUANTITTY);
        List<Fruit> fruits = Storage.getFruits();
        Fruit fruit = fruits.stream()
                .filter(f -> f.getName().equals(FRUIT_NAME))
                .findFirst()
                .get();
        assertEquals(FRUIT_NAME, fruit.getName());
        assertEquals(FRUIT_QUANTITTY, fruit.getQuantity());
    }
}
