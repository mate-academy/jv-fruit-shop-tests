package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.Test;

class BalanceTransactionTest {
    private static final String fruitName = "testfruit";
    private static final int fruitQuantity = 123;
    private final BalanceTransaction balanceTransaction = new BalanceTransaction();

    @Test
    void process_inputCorrect_ok() {
        balanceTransaction.process(fruitName, fruitQuantity);
        List<Fruit> fruits = Storage.getFruits();
        Fruit fruit = fruits.stream()
                .filter(f -> f.getName().equals(fruitName))
                .findFirst()
                .get();
        assertEquals(fruitName, fruit.getName());
        assertEquals(fruitQuantity, fruit.getQuantity());
    }
}
