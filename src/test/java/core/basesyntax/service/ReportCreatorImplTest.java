package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportCreatorImplTest {
    private static ReportCreator reportCreator;

    @BeforeClass
    public static void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    public void reportCreator_Ok() {
        Map<String, Integer> map = new HashMap<>();
        map.put("banana",152);
        map.put("apple",90);
        String actual = reportCreator.create(map);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,152"
                + System.lineSeparator()
                + "apple,90";
        assertEquals(expected,actual);
    }
}
