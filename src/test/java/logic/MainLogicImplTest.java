package logic;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import org.junit.AfterClass;
import org.junit.Test;

public class MainLogicImplTest {

    private static final String TWO_FRUITS_FILE
            = "src/test/resources/IncomingReports/TwoFruits.csv";
    private static final String THREE_FRUITS_FILE
            = "src/test/resources/IncomingReports/ThreeFruits.csv";
    private static final String REPORT_FILE
            = "src/test/resources/OutGoingReports/NormReport.csv";
    private static final String BUILT_IN_REPORT_PATH
            = "src/main/resources/OutGoingReports/Report.csv";

    @AfterClass
    public static void clear() {
        try {
            Files.deleteIfExists(Path.of(REPORT_FILE));
            Files.deleteIfExists(Path.of(BUILT_IN_REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Unable to perform operations with " + REPORT_FILE, e);
        }
    }

    @Test
    public void endToEnd_twoFruits_ok() {
        List<String> expectedReportLines = new ArrayList<>();
        expectedReportLines.add("fruit,quantity");
        expectedReportLines.add("banana,152");
        expectedReportLines.add("apple,90");
        new MainLogicImpl(TWO_FRUITS_FILE, REPORT_FILE).generateReport();
        List<String> actualReportLines;
        try {
            actualReportLines = Files.readAllLines(Path.of(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Test failed with internal error, can't read file "
                    + REPORT_FILE + e);
        }
        for (int i = 0; i < actualReportLines.size(); i++) {
            assertEquals(expectedReportLines.get(i), actualReportLines.get(i));
        }
    }

    @Test
    public void endToEnd_threeFruits_ok() {
        List<String> expectedReportLines = new ArrayList<>();
        expectedReportLines.add("fruit,quantity");
        expectedReportLines.add("banana,152");
        expectedReportLines.add("orange,80");
        expectedReportLines.add("apple,90");
        new MainLogicImpl(THREE_FRUITS_FILE, REPORT_FILE).generateReport();
        List<String> actualReportLines;
        try {
            actualReportLines = Files.readAllLines(Path.of(REPORT_FILE));
        } catch (IOException e) {
            throw new RuntimeException("Test failed with internal error, can't read file "
                    + REPORT_FILE + e);
        }
        for (int i = 0; i < actualReportLines.size(); i++) {
            assertEquals(expectedReportLines.get(i), actualReportLines.get(i));
        }
    }

    @Test
    public void endToEnd_noReportPath_ok() {
        List<String> expectedReportLines = new ArrayList<>();
        expectedReportLines.add("fruit,quantity");
        expectedReportLines.add("banana,152");
        expectedReportLines.add("apple,90");
        new MainLogicImpl(TWO_FRUITS_FILE).generateReport();
        List<String> actualReportLines;
        try {
            actualReportLines = Files.readAllLines(Path.of(BUILT_IN_REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Test failed with internal error, can't read file "
                    + BUILT_IN_REPORT_PATH + e);
        }
        for (int i = 0; i < actualReportLines.size(); i++) {
            assertEquals(expectedReportLines.get(i), actualReportLines.get(i));
        }
    }
}
