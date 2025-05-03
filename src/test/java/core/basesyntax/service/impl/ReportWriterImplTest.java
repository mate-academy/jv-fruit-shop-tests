package core.basesyntax.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.service.ReportWriter;
import core.basesyntax.service.impl.exception.InvalidDataException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReportWriterImplTest {
    private static final String REPORT_FILE_PATH = "src/test/resources/testOutput.csv";
    private static final String INVALID_REPORT_FILE_PATH = "invalid/invalid/invalid/invalid";
    private ReportWriter reportWriter;

    @BeforeEach
    public void setUp() {
        reportWriter = new ReportWriterImpl();
        Storage.storage.clear();
    }

    @Test
    void writeReportToFile_validParameters_Ok() {
        Storage.storage.put("banana", 70);
        Storage.storage.put("apple", 70);
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,70"
                + System.lineSeparator()
                + "apple,70";
        reportWriter.writeReportToFile(report, REPORT_FILE_PATH);
        String result = getDataFromFile(REPORT_FILE_PATH);
        String expected = "fruit,quantity"
                + System.lineSeparator()
                + "banana,70"
                + System.lineSeparator()
                + "apple,70";
        assertEquals(expected, result);
    }

    @Test
    void writeReportToFile_invalidFilePath_notOK() {
        String report = "fruit,quantity"
                + System.lineSeparator()
                + "banana,70";
        assertThrows(InvalidDataException.class,
                () -> reportWriter.writeReportToFile(report, INVALID_REPORT_FILE_PATH),
                "InvalidDataException expected to be thrown");
    }

    private String getDataFromFile(String reportFilePath) {
        File file = new File(reportFilePath);
        StringBuilder builder = new StringBuilder();
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file))) {
            String data = bufferedReader.readLine();
            while (data != null) {
                builder.append(data)
                        .append(System.lineSeparator());
                data = bufferedReader.readLine();
            }
        } catch (IOException e) {
            throw new InvalidDataException("Can't find file by path " + reportFilePath);
        }
        return builder.toString().trim();
    }
}
