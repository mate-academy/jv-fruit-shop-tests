package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;

import core.basesyntax.service.ReportCreator;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportCreatorImplTest {
    private static final String TITLE = "fruit,quantity";
    private ReportCreator reportCreator;

    @BeforeEach
    void setUp() {
        reportCreator = new ReportCreatorImpl();
    }

    @Test
    void createReport_emptyReport_ok() {
        Map<String, Integer> emptyResume = new HashMap<>();
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TITLE).append(System.lineSeparator());
        String expectedReport = stringBuilder.toString();
        String actualReport = reportCreator.createReport(emptyResume);
        assertEquals(expectedReport, actualReport);
    }

    @Test
       void createReport_correctReport_ok() {
        Map<String, Integer> resume = new HashMap<>();
        resume.put("banana", 15);
        resume.put("apple", 20);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(TITLE).append(System.lineSeparator())
                .append("banana").append(",").append(15).append(System.lineSeparator())
                .append("apple").append(",").append(20).append(System.lineSeparator());
        String expectedReport = stringBuilder.toString();
        String actualReport = reportCreator.createReport(resume);
        assertEquals(expectedReport, actualReport);
    }
}
