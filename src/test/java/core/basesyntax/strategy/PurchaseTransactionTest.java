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
    private static final String fruitName = "testfruit";
    private static final int fruitQuantity = 123;
    private final PurchaseTransaction purchaseTransaction = new PurchaseTransaction();
    private int fruitPurchased;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipSetup")) {
            return;
        }
        Fruit fruit = new Fruit();
        fruit.setName(fruitName);
        fruit.setQuantity(fruitQuantity);
        Storage.addFruit(fruit);
    }

    @AfterEach
    void cleanUp(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        Storage.removeFruit(fruitName);
    }

    @Test
    void process_inputCorrect_ok() {
        fruitPurchased = 122;
        purchaseTransaction.process(fruitName, fruitPurchased);
        List<Fruit> fruits = Storage.getFruits();
        Fruit fruit = fruits.stream()
                .filter(f -> f.getName().equals(fruitName))
                .findFirst()
                .get();
        assertEquals(fruitName, fruit.getName());
        assertEquals(fruitQuantity - fruitPurchased, fruit.getQuantity());
    }

    @Test
    void process_purchaseMoreThanAvailable_ok() {
        fruitPurchased = 124;
        assertThrows(RuntimeException.class,
                () -> purchaseTransaction.process(fruitName, fruitPurchased));
    }

    @Test
    @Tag("SkipSetup")
    @Tag("SkipCleanup")
    void process_fruitNotExist_ok() {
        fruitPurchased = 1;
        assertThrows(IllegalArgumentException.class,
                () -> purchaseTransaction.process(fruitName, fruitPurchased));
    }
}
