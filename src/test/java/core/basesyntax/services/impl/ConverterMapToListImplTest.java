package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import java.util.List;
import java.util.Map;
import org.junit.Test;

public class ConverterMapToListImplTest {
    private final Fruit apple = new Fruit("apple");
    private final Fruit kiwi = new Fruit("kiwi");

    @Test
    public void convertMethodTest_Ok() {
        List<String> expectedList = List.of("apple,10", "kiwi,10");
        Map<Fruit, Integer> storage = Map.of(apple, 10, kiwi, 10);
        List<String> actualList = new ConverterMapToListImpl().convert(storage);
        assertEquals(expectedList, actualList);
    }
}
