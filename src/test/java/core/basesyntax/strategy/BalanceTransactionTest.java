package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

class BalanceTransactionTest {
    private static final String FRUIT_NAME = "testfruit";
    private static final int FRUIT_QUANTITY = 123;
    private final BalanceTransaction balanceTransaction = new BalanceTransaction();

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void process_inputCorrect_ok() {
        balanceTransaction.process(FRUIT_NAME, FRUIT_QUANTITY);
        List<Fruit> fruits = Storage.getFruits();
        Fruit fruit = fruits.stream()
                .filter(f -> f.getName().equals(FRUIT_NAME))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Fruit was not added to storage"));
        assertEquals(FRUIT_NAME, fruit.getName(), "Fruit not found in storage");
        assertEquals(FRUIT_QUANTITY, fruit.getQuantity(),
                "Fruit quantity expected: "
                        + FRUIT_QUANTITY
                        + " but received "
                        + fruit.getQuantity());
        assertEquals(1, fruits.size(), "Storage should contain exactly one fruit");
    }
}
