package service.impl;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceImplTest {
    private static final String REPORT_FILE
            = "src/test/resources/OutGoingReports/NormReport.csv";
    private static final String NULL_FILE
            = null;
    private static final String INVALID_REPORT_FILE
            = "///^_^|||";
    private static final List<String> expectedReportLines = new ArrayList<>();
    private static String preparedReport;

    @BeforeClass
    public static void set() {
        expectedReportLines.add("fruit,quantity");
        expectedReportLines.add("banana,152");
        expectedReportLines.add("apple,90");
        preparedReport = "fruit,quantity" + System.lineSeparator()
                + "banana,152" + System.lineSeparator()
                + "apple,90" + System.lineSeparator();
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_nullFile_notOk() {
        new WriterServiceImpl().writeToFile(preparedReport, NULL_FILE);
    }

    @Test(expected = RuntimeException.class)
    public void writeToFile_invalidPath_notOk() {
        new WriterServiceImpl().writeToFile(preparedReport, INVALID_REPORT_FILE);
    }

    @Test
    public void writeToFile_ok() {
        new WriterServiceImpl().writeToFile(preparedReport, REPORT_FILE);
        List<String> actualReportLines;
        try {
            actualReportLines = Files.readAllLines(Path.of(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Test failed with internal error, can't read file "
                    + REPORT_FILE + e);
        }
        for (int i = 0; i < actualReportLines.size(); i++) {
            assertEquals("Report has not been written correctly!",
                    expectedReportLines.get(i), actualReportLines.get(i));
        }
    }
}
