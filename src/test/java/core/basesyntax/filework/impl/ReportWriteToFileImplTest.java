package core.basesyntax.filework.impl;

import core.basesyntax.filework.ReportWriteToFile;
import org.junit.BeforeClass;
import org.junit.Test;

public class ReportWriteToFileImplTest {
    private static ReportWriteToFile reportWriteToFile;
    private static final String path = "src/main/resources/testFile.csv";
    private String expected;

    @BeforeClass
    public static void beforeClass() {
        reportWriteToFile = new ReportWriteToFileImpl();
    }

    @Test
    public void writeExistedReport_ok() {
        expected = "Expected report, "
                + System.lineSeparator() + "write!";
        reportWriteToFile.writeToFile(expected, path);
    }

    @Test(expected = RuntimeException.class)
    public void writeNonExistedReport_notOk() {
        reportWriteToFile.writeToFile(expected, path);
    }

    @Test(expected = RuntimeException.class)
    public void writeReportToNullPath_notOk() {
        String invalid = "src/main/resources/noFIle.csv";
        expected = "qwer";
        reportWriteToFile.writeToFile(expected, invalid);
    }
}
