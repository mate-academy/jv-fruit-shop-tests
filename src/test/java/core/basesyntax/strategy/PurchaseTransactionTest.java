package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

class PurchaseTransactionTest {
    private static final String FRUIT_NAME = "testfruit";
    private static final int FRUIT_QUANTITY = 123;
    private final PurchaseTransaction purchaseTransaction = new PurchaseTransaction();

    @BeforeEach
    void setUp(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipSetup")) {
            return;
        }
        Fruit fruit = new Fruit();
        fruit.setName(FRUIT_NAME);
        fruit.setQuantity(FRUIT_QUANTITY);
        Storage.addFruit(fruit);
    }

    @AfterEach
    void cleanUp() {
        Storage.getFruits().clear();
    }

    @Test
    void process_inputCorrect_ok() {
        int fruitPurchased = 122;
        int fruitAfterTransaction = FRUIT_QUANTITY - fruitPurchased;
        purchaseTransaction.process(FRUIT_NAME, fruitPurchased);
        List<Fruit> fruits = Storage.getFruits();
        Fruit fruit = fruits.stream()
                .filter(f -> f.getName().equals(FRUIT_NAME))
                .findFirst()
                .orElseThrow(() -> new AssertionError("Fruit was not added to storage"));
        assertEquals(FRUIT_NAME, fruit.getName(), "Fruit not found in storage");
        assertEquals(fruitAfterTransaction, fruit.getQuantity(),
                "Fruit quantity expected: "
                        + fruitAfterTransaction
                        + " but received "
                        + fruit.getQuantity());
        assertEquals(1, fruits.size(), "Storage should contain exactly one fruit");
    }

    @Test
    void process_purchaseMoreThanAvailable_notOk() {
        int fruitPurchased = 124;
        assertThrows(RuntimeException.class,
                () -> purchaseTransaction.process(FRUIT_NAME, fruitPurchased),
                "Purchasing more than available in storage should not be allowed");
    }

    @Test
    @Tag("SkipSetup")
    void process_fruitNotExist_notOk() {
        int fruitPurchased = 1;
        assertThrows(IllegalArgumentException.class,
                () -> purchaseTransaction.process(FRUIT_NAME, fruitPurchased),
                "Purchasing not existing fruit should not be allowed");
    }
}
