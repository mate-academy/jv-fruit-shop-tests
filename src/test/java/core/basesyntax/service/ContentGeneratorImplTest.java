package core.basesyntax.service;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.impl.ContentGeneratorImpl;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class ContentGeneratorImplTest {
    private static ContentGenerator contentGenerator;
    private static final String HEADER = "fruit,quantity"
            + System.lineSeparator();

    @BeforeClass
    public static void setUp() {
        contentGenerator = new ContentGeneratorImpl();
    }

    @Test
    public void generateContent_validData_ok() {
        Map<Fruit, Integer> fruitIntegerMap = new HashMap<>();
        fruitIntegerMap.put(new Fruit("banana"), 152);
        fruitIntegerMap.put(new Fruit("apple"), 90);
        String actual = contentGenerator.generateContent(fruitIntegerMap);
        String expected = HEADER
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void generateContent_emptyData_ok() {
        String actual = contentGenerator.generateContent(Collections.emptyMap());
        Assert.assertEquals(actual, HEADER);
    }
}
