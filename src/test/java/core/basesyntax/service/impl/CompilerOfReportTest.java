package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import core.basesyntax.model.Fruit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompilerOfReportTest {
    private static final String REPORT_TOPIC = "fruit,quantity";
    private static final String SEPARATOR = ",";
    private static CompilerOfReport reporter;
    private Set<Map.Entry<Fruit, Integer>> request;

    @BeforeClass
    public static void setUp() {
        reporter = new CompilerOfReport();
    }

    @Test
    public void generateReport_EmptySet_Ok() {
        String expected = REPORT_TOPIC + System.lineSeparator();
        assertEquals(expected, reporter.generateReport(Collections.EMPTY_SET));
    }

    @Test
    public void generateReport_NullInput_Ok() {
        String expected = REPORT_TOPIC + System.lineSeparator();
        assertEquals(expected, reporter.generateReport(null));
    }

    @Test
    public void generateReport_OneEntry_Ok() {
        Map<Fruit, Integer> map = new HashMap<>();
        map.put(new Fruit("fruit"), 10);
        request = map.entrySet();
        String expected = REPORT_TOPIC + System.lineSeparator() + "fruit,10";
        assertEquals(expected, reporter.generateReport(request));
    }

    @Test
    public void generateReport_ManyFruits_Ok() {
        Map<Fruit, Integer> map = new HashMap<>();
        int numberOfCounts = 100;
        for (int i = 0; i < numberOfCounts; i++) {
            map.put(new Fruit(String.valueOf(i)), i);
        }
        StringBuilder builder = new StringBuilder();
        builder.append(REPORT_TOPIC).append(System.lineSeparator());
        for (int i = 0; i < numberOfCounts; i++) {
            builder.append(i).append(",").append(i).append(System.lineSeparator());
        }
        builder.delete(builder.lastIndexOf(System.lineSeparator()), builder.length());

        request = map.entrySet();
        String expected = builder.toString();
        assertTrue(expected.length() == reporter.generateReport(request).length());
    }
}
