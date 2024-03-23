package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private final Map<String, Integer> inputTestDataMap = new HashMap<>();
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
        inputTestDataMap.put("banana", 152);
        inputTestDataMap.put("apple", 90);
    }

    @Test
    void reportCreator_CreateCorrectReportData_ok() {
        String expected = "\tfruit,quantity"
                + System.lineSeparator()
                + "\tbanana,152"
                + System.lineSeparator()
                + "\tapple,90"
                + System.lineSeparator();
        String actual = reportCreator.reportCreator(inputTestDataMap);
        assertEquals(expected, actual,
                "Can not create report. Check report structure");
    }
}
