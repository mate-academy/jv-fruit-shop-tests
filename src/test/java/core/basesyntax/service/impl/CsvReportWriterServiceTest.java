package core.basesyntax.service.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import core.basesyntax.service.ReportWriterService;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import org.junit.Before;
import org.junit.Test;

public class CsvReportWriterServiceTest {
    private ReportWriterService reportWriterService;

    @Before
    public void setUp() throws Exception {
        reportWriterService = new CsvReportWriterService();
    }

    @Test
    public void writeReport_correctParameter_ok() {
        String expected = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,90";
        String path = "./src/test/resources/report-test.csv";
        reportWriterService.writeReport(expected, path);
        File file = new File(path);
        String actual = readFile(path);
        assertEquals(expected, actual);
    }

    @Test
    public void writeReport_pathNull_notOk() {
        String report = "fruit,quantity" + System.lineSeparator()
                + "banana,100" + System.lineSeparator()
                + "apple,90";
        assertThrows(RuntimeException.class, () -> {
            reportWriterService.writeReport(report, null);
        });
    }

    @Test
    public void writeReport_reportNull_notOk() {
        String path = "./src/test/resources/report-null.csv";
        assertThrows(RuntimeException.class, () -> {
            reportWriterService.writeReport(null, path);
        });
    }

    private String readFile(String path) {
        StringBuilder stringBuilder = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(path));) {
            String value = reader.readLine();
            while (value != null) {
                stringBuilder.append(value);
                value = reader.readLine();
                if (value != null) {
                    stringBuilder.append(System.lineSeparator());
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Can't read file", e);
        }
        return stringBuilder.toString();
    }
}
