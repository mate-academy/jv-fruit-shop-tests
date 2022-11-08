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
    private static Map<Fruit, Integer> fruitIntegerMap;
    private static String report;

    @BeforeClass
    public static void setUp() {
        fruitIntegerMap = new HashMap<>();
        contentGenerator = new ContentGeneratorImpl();
        report = "fruit,quantity"
                + System.lineSeparator();
    }

    @Test
    public void generateContent_validData_ok() {
        fruitIntegerMap.put(new Fruit("banana"), 152);
        fruitIntegerMap.put(new Fruit("apple"), 90);
        String actual = contentGenerator.generateContent(fruitIntegerMap);
        String expected = report
                + "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        Assert.assertEquals(actual, expected);
    }

    @Test
    public void generateContent_emptyData_ok() {
        String actual = contentGenerator.generateContent(Collections.emptyMap());
        Assert.assertEquals(actual, report);
    }
}
