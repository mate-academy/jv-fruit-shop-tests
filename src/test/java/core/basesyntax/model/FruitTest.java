package core.basesyntax.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class FruitTest {
    @Test
    void correctFruitNameSetter_Ok() {
        Fruit cherry = new Fruit("invalidText");
        cherry.setName("cherry");
        Assertions.assertEquals("cherry", cherry.getName());
    }
}
