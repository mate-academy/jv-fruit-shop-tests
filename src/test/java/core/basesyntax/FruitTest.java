package core.basesyntax;

import core.basesyntax.database.Storage;
import core.basesyntax.model.Fruit;
import java.util.Optional;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FruitTest {
    private static Fruit banana;
    private static Fruit apple;
    private static Fruit melon;

    @Before
    public void setUp() {
        banana = new Fruit("banana");
        apple = new Fruit("apple");
        melon = new Fruit("melon");
    }

    @Test
    public void storeFruits_isOk() {
        Storage.fruitStorage.put(banana, 20);
        Storage.fruitStorage.put(apple, 10);

        Optional<Integer> optionalBanana = Optional.ofNullable(Storage.fruitStorage.get(banana));
        int actual = optionalBanana.get();
        Assert.assertEquals("apple", apple.getName());
        Assert.assertTrue(Optional.ofNullable(Storage.fruitStorage.get(apple)).isPresent());
        Assert.assertEquals(20, actual);
    }

    @Test
    public void storeFruits_NotOk() {
        Storage.fruitStorage.put(apple, 10);
        Storage.fruitStorage.put(banana, 10);
        Storage.fruitStorage.remove(apple, 10);
        Assert.assertFalse(Optional.ofNullable(Storage.fruitStorage.get(melon)).isPresent());
        Assert.assertFalse(Storage.fruitStorage.isEmpty());
    }

    @After
    public void tearDown() {
        Storage.fruitStorage.clear();
    }
}
