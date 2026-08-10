package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.Test;
import java.util.List;

class BalanceTransactionTest {
    private final static String fruitName = "testfruit";
    private final static int fruitQuantity = 123;
    BalanceTransaction balanceTransaction = new BalanceTransaction();

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