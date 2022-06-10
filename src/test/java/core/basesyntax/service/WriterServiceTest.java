package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Test;

public class WriterServiceTest {
    private static final String REPORT_PATH = "src/test/resources/report.csv";
    private static WriterService writerService;

    @BeforeClass
    public static void beforeClass() {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_report_ok() {
        String expected = "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        String data = "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        writerService.write(data, REPORT_PATH);
        String actual = getActualReport();
        assertEquals(expected, actual);
    }

    @Test
    public void write_emptyReport_ok() {
        String expected = "";
        writerService.write("", REPORT_PATH);
        String actual = getActualReport();
        assertEquals(expected, actual);
    }

    @Test(expected = RuntimeException.class)
    public void write_notValidPath_notOk() {
        writerService.write("", "");
    }

    private String getActualReport() {
        List<String> report;
        try {
            report = Files.readAllLines(Path.of(WriterServiceTest.REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + WriterServiceTest.REPORT_PATH);
        }
        StringBuilder builder = new StringBuilder();
        for (String string : report) {
            builder.append(string).append(System.lineSeparator());
        }
        return builder.toString();
    }
}
