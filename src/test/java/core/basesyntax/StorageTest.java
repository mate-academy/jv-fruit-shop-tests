package core.basesyntax;

import core.basesyntax.storage.Storage;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StorageTest {
    @BeforeEach
    public void setup() {
        Storage.fruits.clear();
    }

    @Test
    public void testAddFruit_Ok() {
        Storage.addFruit("banana",10);
        Storage.addFruit("apple", 20);

        Assert.assertEquals(10, Storage.getFruitQuantity("banana"));
        Assert.assertEquals(20, Storage.getFruitQuantity("apple"));

        Storage.addFruit("apple", 10);

        Assert.assertEquals(30, Storage.getFruitQuantity("apple"));
    }

    @Test
    public void testAddFruit_notOk() {
        IllegalArgumentException exception =
                Assert.assertThrows(IllegalArgumentException.class, () -> {
                    Storage.addFruit("apple", -5);
                });

        Assert.assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    @Test
    public void testGetFruitQuantity_ok() {
        Storage.addFruit("banana",10);
        Storage.addFruit("apple", 20);

        Assert.assertEquals(20, Storage.getFruitQuantity("apple"));
        Assert.assertEquals(10, Storage.getFruitQuantity("banana"));
        Assert.assertEquals(0, Storage.getFruitQuantity("orange"));
    }

    @Test
    public void testRemoveFruit_ok() {
        Storage.addFruit("banana",10);
        Storage.addFruit("apple",20);
        Storage.addFruit("orange",29);

        Storage.removeFruit("banana",10);
        Storage.removeFruit("apple",9);
        Storage.removeFruit("orange",5);

        Assert.assertEquals(0,Storage.getFruitQuantity("banana"));
        Assert.assertEquals(11, Storage.getFruitQuantity("apple"));
        Assert.assertEquals(24, Storage.getFruitQuantity("orange"));
    }
}

