package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.Test;

public class ReportWriterImplTest {
    private static final String NON_EXISTENT_FOLDER = "src/main/report/report.csv";
    private static final String NORMAL_FILE_PATH = "src/main/resources/report.csv";
    private static final String LINE_SEPARATOR = System.lineSeparator();
    private final ReportWriter reportWriter = new ReportWriterImpl();

    @Test(expected = RuntimeException.class)
    public void writeReport_nonExistentFolder_notOk() {
        String report = "report";
        reportWriter.writeReport(report, NON_EXISTENT_FOLDER);
    }

    @Test
    public void writeReport_normalData_ok() {
        String report = "fruit,quantity" + LINE_SEPARATOR
                + "banana,115" + LINE_SEPARATOR
                + "apple,110" + LINE_SEPARATOR;

        reportWriter.writeReport(report, NORMAL_FILE_PATH);
        String actual = readFile(NORMAL_FILE_PATH);
        assertEquals(report, actual);
    }

    private String readFile(String path) {
        try {
            return Files.readString(Path.of(path));
        } catch (IOException e) {
            throw new RuntimeException("Can't read file: " + path, e);
        }
    }
}
