package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static final Map<String, Integer> STORAGE = FruitStorage.fruits;
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    public void generate_storageIsEmpty_ok() {
        String expected = ReportGeneratorImpl.FIELD_NAMES;
        String actual = reportGenerator.generate();
        assertEquals(expected, actual);
    }

    @Test
    public void generate_storageIsFull_ok() {
        STORAGE.put("banana", 12);
        STORAGE.put("apple", 8);
        STORAGE.put("orange", 14);
        StringBuilder stringBuilder = new StringBuilder(ReportGeneratorImpl.FIELD_NAMES);
        for (Map.Entry<String, Integer> fruitBalance : STORAGE.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(fruitBalance.getKey())
                    .append(",")
                    .append(fruitBalance.getValue().toString());
        }
        String expected = stringBuilder.toString();
        String actual = reportGenerator.generate();
        assertEquals(expected, actual);
    }

    @After
    public void clearStorage() {
        STORAGE.clear();
    }
}