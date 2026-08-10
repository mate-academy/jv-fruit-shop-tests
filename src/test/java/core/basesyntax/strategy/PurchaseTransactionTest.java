package core.basesyntax.strategy;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.model.Fruit;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import java.util.List;

class PurchaseTransactionTest {
    private final static String fruitName = "testfruit";
    private final static int fruitQuantity = 123;
    private int fruitPurchased;
    PurchaseTransaction purchaseTransaction = new PurchaseTransaction();

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
    void cleanUp() {
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
    void process_fruitNotExist_ok() {
        fruitPurchased = 1;
        assertThrows(IllegalArgumentException.class,
                () -> purchaseTransaction.process(fruitName, fruitPurchased));
    }
}