package core.basesyntax.filework.impl;

import core.basesyntax.filework.ReportWriteToFile;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriteToFileImplTest {
    private static ReportWriteToFile reportWriteToFile;
    private String path;
    private String expected;

    @BeforeClass
    public static void beforeClass() {
        reportWriteToFile = new ReportWriteToFileImpl();

    }

    @Test
    public void writeExistedReport_ok() {
        expected = "Expected report, "
                + System.lineSeparator() + "write!";
        path = "src/main/resources/testFile.csv";
        reportWriteToFile.writeToFile(expected, path);
    }

    @Test
            (expected = RuntimeException.class)
    public void writeNonExistedReport_notOk() {
        path = "src/main/resources/testFile.csv";
        reportWriteToFile.writeToFile(expected, path);
    }

    @Test
            (expected = RuntimeException.class)
    public void writeReportToNullPath_notOk() {
        expected = "Expected report222, "
                + System.lineSeparator() + "write!";
        reportWriteToFile.writeToFile(expected, path);
    }
}
