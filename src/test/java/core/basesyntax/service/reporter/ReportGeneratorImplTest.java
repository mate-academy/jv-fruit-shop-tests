package core.basesyntax.service.reporter;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import core.basesyntax.model.Fruit;
import java.util.HashMap;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static Map<Fruit, Integer> fruitStorageMap;
    private static String expected;

    @BeforeClass
    public static void beforeClass() {
        reportGenerator = new ReportGeneratorImpl();
        fruitStorageMap = new HashMap<>();
    }

    @Before
    public void setUp() {
        fruitStorageMap.put(new Fruit("apple"), 33);
        fruitStorageMap.put(new Fruit("banana"), 2);
        expected = "fruit,quantity" + System.lineSeparator()
                + "banana,2" + System.lineSeparator()
                + "apple,33" + System.lineSeparator();
    }

    @Test
    public void reportGenerator_equals_Ok() {
        String actual = reportGenerator.createReport(fruitStorageMap);
        assertEquals(actual, expected);
    }

    @Test
    public void reportGenerator_not_equals_Ok() {
        String actual = reportGenerator.createReport(fruitStorageMap);
        expected = "777";
        assertNotEquals(actual, expected);
    }
}
