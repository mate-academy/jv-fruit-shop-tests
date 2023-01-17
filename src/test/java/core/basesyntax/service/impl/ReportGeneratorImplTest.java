package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.db.FruitStorage;
import core.basesyntax.service.ReportGenerator;
import java.util.Map;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;

    @BeforeClass
    public static void setUp() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Before
    public void clearStorage() {
        FruitStorage.fruits.clear();
    }

    @Test
    public void generate_storageIsEmpty_ok() {
        String expected = ReportGeneratorImpl.FIELDS_NAMES;
        String actual = reportGenerator.generate();
        assertEquals(expected, actual);
    }

    @Test
    public void generate_storageIsFull_ok() {
        FruitStorage.fruits.put("banana", 12);
        FruitStorage.fruits.put("apple", 8);
        FruitStorage.fruits.put("orange", 14);
        StringBuilder stringBuilder = new StringBuilder(ReportGeneratorImpl.FIELDS_NAMES);
        for (Map.Entry<String, Integer> fruitBalance : FruitStorage.fruits.entrySet()) {
            stringBuilder.append(System.lineSeparator())
                    .append(fruitBalance.getKey())
                    .append(",")
                    .append(fruitBalance.getValue().toString());
        }
        String expected = stringBuilder.toString();
        String actual = reportGenerator.generate();
        assertEquals(expected, actual);
    }
}
