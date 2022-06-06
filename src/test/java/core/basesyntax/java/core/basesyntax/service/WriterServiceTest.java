package core.basesyntax.java.core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.java.core.basesyntax.service.impl.WriterServiceCsvImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String ACTUAL_REPORT = "src/main/resources/report.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceCsvImpl();
    }

    @Test
    public void write_report_ok() {
        StringBuilder expected = new StringBuilder()
                .append("fruit,quantity")
                .append(System.lineSeparator())
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        StringBuilder data = new StringBuilder()
                .append("banana,152")
                .append(System.lineSeparator())
                .append("apple,90");
        writerService.write(data.toString());
        String actual = getActualReport();
        assertEquals(expected.toString(),actual);
    }

    @Test
    public void write_emptyReport_ok() {
        String expected = "fruit,quantity";
        String data = "";
        writerService.write(data);
        String actual = getActualReport();
        assertEquals(expected, actual);
    }

    private String getActualReport() {
        List<String> report;
        try {
            report = Files.readAllLines(Path.of(ACTUAL_REPORT));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + ACTUAL_REPORT);
        }
        StringBuilder builder = new StringBuilder();
        for (String string : report) {
            builder.append(string).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
