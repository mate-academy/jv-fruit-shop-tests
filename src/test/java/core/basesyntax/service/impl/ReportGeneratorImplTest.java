package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.service.operations.report.ReportGenerator;
import core.basesyntax.service.operations.report.impl.ReportGeneratorImpl;
import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ReportGeneratorImplTest {
    private static ReportGenerator reportGenerator;
    private static final String SPLITTER_NEW_LINE = System.lineSeparator();
    private String expected = "fruit,quantity" + SPLITTER_NEW_LINE
            + "apple,50" + SPLITTER_NEW_LINE + "banana,20" + SPLITTER_NEW_LINE;

    @BeforeAll
    static void beforeAll() {
        reportGenerator = new ReportGeneratorImpl();
    }

    @Test
    void createReport_ok() {
        Map<Fruit, Integer> storage = new TreeMap<>(new FruitComparator());
        storage.put(new Fruit("apple"), 50);
        storage.put(new Fruit("banana"), 20);

        String actual = reportGenerator.generateReportContent(storage);
        assertEquals(expected, actual);
    }

    static class FruitComparator implements Comparator<Fruit> {
        @Override
        public int compare(Fruit fruit1, Fruit fruit2) {
            return fruit1.name().compareTo(fruit2.name());
        }
    }
}
