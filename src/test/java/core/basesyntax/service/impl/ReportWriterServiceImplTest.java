package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriterServiceImplTest {
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private static final String DATA_FOR_TESTS = "src/test/resources/reportForTests.csv";
    private static ReportWriterService reportWriterService;
    private static String report;

    @BeforeClass
    public static void beforeClass() {
        reportWriterService = new ReportWriterServiceImpl();
        report = "fruit,quantity" + LINE_SEPARATOR
                + "banana,100" + LINE_SEPARATOR;
    }

    @Test
    public void writeReportToFile_validReportFile_ok() {
        reportWriterService.writeReportToFile(DATA_FOR_TESTS, report);
        String actualString = actualString();
        assertEquals(report, actualString);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToFile_invalidPath_notOk() {
        reportWriterService.writeReportToFile("", report);
    }

    private String actualString() {
        List<String> actualList;
        try {
            actualList = Files.readAllLines(Path.of(DATA_FOR_TESTS));
        } catch (IOException e) {
            throw new RuntimeException("Can't read report", e);
        }
        StringBuilder actual = new StringBuilder();
        for (String line : actualList) {
            actual.append(line).append(LINE_SEPARATOR);
        }
        return actual.toString();
    }

}

