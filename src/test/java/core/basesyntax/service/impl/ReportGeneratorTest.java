package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportGenerator;
import java.util.LinkedHashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportGeneratorTest {
    private static final String DEFAULT_STRING = "fruit,quantity";
    private ReportGenerator generator;

    @BeforeEach
    void setUp() {
        Map<String, Integer> map = new LinkedHashMap<>();
        map.put("kiwi", 20);
        map.put("banana", 100);
        map.put("orange", 0);
        generator = new ReportGeneratorImpl(new Storage(map));
    }

    @Test
    public void getReport_Ok() {
        String expected = DEFAULT_STRING + System.lineSeparator()
                + "kiwi,20" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "orange,0" + System.lineSeparator();
        String actual = generator.getReport();
        assertEquals(expected, actual);
    }
}
