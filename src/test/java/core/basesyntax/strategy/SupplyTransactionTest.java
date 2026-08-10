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

class SupplyTransactionTest {
    private final static String fruitName = "testfruit";
    private final static int fruitQuantity = 123;
    ReturnTransaction returnTransaction = new ReturnTransaction();

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
        int suppliedQuantity = 20;
        returnTransaction.process(fruitName, suppliedQuantity);
        List<Fruit> fruits = Storage.getFruits();
        Fruit fruit = fruits
                .stream()
                .filter(f -> f.getName().equals(fruitName))
                .findFirst()
                .get();
        assertEquals(fruitQuantity + suppliedQuantity, fruit.getQuantity());
    }

    @Test
    @Tag("SkipSetup")
    void process_fruitNotExist_notOk() {
        int suppliedQuantity = 20;
        assertThrows(IllegalArgumentException.class,
                () -> returnTransaction.process(fruitName, suppliedQuantity));
    }
}