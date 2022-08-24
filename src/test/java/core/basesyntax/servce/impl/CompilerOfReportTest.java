package core.basesyntax.servce.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.model.Fruit;
import core.basesyntax.servce.ReportCreator;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import org.junit.BeforeClass;
import org.junit.Test;

public class CompilerOfReportTest {
    private static final String REPORT_TOPIC = "fruit,quantity";
    private static ReportCreator reporter;

    @BeforeClass
    public static void setUp() {
        reporter = new CompilerOfReport();
    }

    @Test
    public void createReport_NullInput_Ok() {
        assertEquals("For the null and empty input you should return topper 'fruit,quantity'.",
                REPORT_TOPIC, reporter.createReport(null));
    }

    @Test
    public void crateReport_EmptyMapInput_Ok() {
        assertEquals("For the null and empty input you should return topper 'fruit,quantity'.",
                REPORT_TOPIC, reporter.createReport(Collections.emptyMap()));
    }

    @Test
    public void createReport_OneKey_Ok() {
        Map<Fruit, Integer> input = new HashMap<>();
        input.put(new Fruit("fruit"), 50);
        String expected = REPORT_TOPIC + System.lineSeparator() + "fruit,50";
        assertEquals(expected, reporter.createReport(input));
    }

    @Test
    public void createReport_ManyKeys_Ok() {
        Map<Fruit, Integer> input = new HashMap<>();
        Fruit[] fruits = new Fruit[50];
        StringBuilder builder = new StringBuilder();
        builder.append(REPORT_TOPIC);
        for (int i = 0; i < fruits.length; i++) {
            fruits[i] = new Fruit(String.valueOf(i));
            input.put(fruits[i], i);
            builder.append(System.lineSeparator()).append(i).append(",").append(i);
        }
        String expected = builder.toString();
        String actual = reporter.createReport(input);
        assertEquals(expected.length(), actual.length());
    }
}