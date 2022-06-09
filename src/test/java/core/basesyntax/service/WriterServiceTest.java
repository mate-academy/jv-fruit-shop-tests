package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.WriterServiceImpl;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceTest {
    private static final String REPORT_PATH = "src/main/resources/report.csv";
    private static WriterService writerService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
    }

    @Test
    public void write_report_ok() {
        String expected = "banana,152"
                + System.lineSeparator()
                + "apple,90";
        String data = "banana,152"
                + System.lineSeparator()
                + "apple,90"
                + System.lineSeparator();
        writerService.write(data, REPORT_PATH);
        String actual = getActualReport();
        assertEquals(expected,actual);
    }

    @Test
    public void write_emptyReport_ok() {
        String expected = "";
        writerService.write("", REPORT_PATH);
        String actual = getActualReport();
        assertEquals(expected, actual);
    }

    @Test
    public void write_notValidPath_notOk() {
        exception.expect(RuntimeException.class);
        writerService.write("", "");
    }

    private String getActualReport() {
        List<String> report;
        try {
            report = Files.readAllLines(Path.of(REPORT_PATH));
        } catch (IOException e) {
            throw new RuntimeException("Can't read from file " + REPORT_PATH);
        }
        StringBuilder builder = new StringBuilder();
        for (String string : report) {
            builder.append(string).append(System.lineSeparator());
        }
        return builder.toString().trim();
    }
}
