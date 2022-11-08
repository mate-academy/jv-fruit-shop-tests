package core.basesyntax.services.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.services.ConverterMapToList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ConverterMapToListImplTest {
    private static ConverterMapToList converterMapToList;
    private Fruit apple;
    private Fruit kiwi;
    private Map<Fruit, Integer> testStorage;

    @BeforeClass
    public static void globalSetUp() {
        converterMapToList = new ConverterMapToListImpl();
    }

    @Before
    public void setUp() {
        apple = new Fruit("apple");
        kiwi = new Fruit("kiwi");
        testStorage = Map.of(apple, 10, kiwi, 10);
    }

    @Test
    public void convertMethodTest_Ok() {
        List<String> expectedList = List.of("apple,10", "kiwi,10");
        List<String> actualList = converterMapToList.convert(testStorage);
        assertEquals(expectedList, actualList);
    }

    @Test
    public void convertWithEmpty_Ok() {
        List<String> expectedList = Collections.EMPTY_LIST;
        Map<Fruit, Integer> testStorage = Collections.EMPTY_MAP;
        List<String> actualList = converterMapToList.convert(testStorage);
        assertEquals(expectedList, actualList);
    }
}
