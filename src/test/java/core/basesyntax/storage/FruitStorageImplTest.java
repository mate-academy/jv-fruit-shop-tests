package core.basesyntax.storage;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class FruitStorageImplTest {
    private static FruitStorage fruitStorage = new FruitStorageImpl();

    @BeforeEach
    public void setUp() {
        fruitStorage = new FruitStorageImpl();
        fruitStorage.addFruit("Banana", 10);
    }

    @Test
    void addFruit_ok() {
        fruitStorage.addFruit("Mango", 100);
        int expected = 100;
        int actual = fruitStorage.getFruitQuantity("Mango");
        assertEquals(expected, actual);
    }

    @Test
    void setFruitQuantity_ok() {
        int expected = 50;
        fruitStorage.setFruitBalance("Banana", expected);
        int actual = fruitStorage.getFruitQuantity("Banana");
        assertEquals(expected, actual);
    }

    @Test
    void removeNotEnoughFruitBalance_notOk() {
        assertThrows(IllegalArgumentException.class,
                () -> fruitStorage.removeFruit("Banana", 25));
    }

    @Test
    void removeFruitDoesntExist_notOk() {
        fruitStorage.removeFruit("Mango", 5);
    }
}
