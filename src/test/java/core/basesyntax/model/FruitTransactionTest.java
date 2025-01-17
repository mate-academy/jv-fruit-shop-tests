package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitTransactionTest {
    private static final int BANANA_QUANTITY = 152;
    private static final int APPLE_QUANTITY = 90;
    private static final String BANANA = "banana";
    private static final String APPLE = "apple";

    private FruitTransaction fruitTransactionFirst;
    private FruitTransaction fruitTransactionSecond;

    @BeforeEach
    void setUp() {
        fruitTransactionFirst = new FruitTransaction();
        fruitTransactionSecond = new FruitTransaction();

        fruitTransactionFirst.setQuantity(152);
        fruitTransactionFirst.setFruit("banana");

        fruitTransactionSecond.setQuantity(90);
        fruitTransactionSecond.setFruit("apple");
    }

    @Test
    void get_validFruitType_ok() {
        Assertions.assertEquals(BANANA_QUANTITY, fruitTransactionFirst.getQuantity());
        Assertions.assertEquals(APPLE_QUANTITY, fruitTransactionSecond.getQuantity());
    }

    @Test
    void getQuantity() {
        Assertions.assertEquals(BANANA, fruitTransactionFirst.getFruit());
        Assertions.assertEquals(APPLE, fruitTransactionSecond.getFruit());
    }
}