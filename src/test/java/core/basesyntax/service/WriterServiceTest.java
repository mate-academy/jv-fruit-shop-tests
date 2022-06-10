package core.basesyntax.service;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.impl.ReaderServiceImpl;
import core.basesyntax.service.impl.WriterServiceImpl;
import java.util.List;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WriterServiceTest {
    private static final String REPORT_PATH = "src/test/resources/report.csv";
    private static WriterService writerService;
    private static ReaderService readerService;

    @Rule
    public final ExpectedException exception = ExpectedException.none();

    @BeforeClass
    public static void beforeClass() throws Exception {
        writerService = new WriterServiceImpl();
        readerService = new ReaderServiceImpl();
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
        String actual = listToString(readerService.readFromFile(REPORT_PATH));
        assertEquals(expected, actual);
    }

    @Test
    public void write_emptyReport_ok() {
        String expected = "";
        writerService.write("", REPORT_PATH);
        String actual = listToString(readerService.readFromFile(REPORT_PATH));
        assertEquals(expected, actual);
    }

    @Test
    public void write_notValidPath_notOk() {
        exception.expect(RuntimeException.class);
        writerService.write("", "");
    }

    private String listToString(List<String> reportList) {
        StringBuilder report = new StringBuilder();
        for (String transaction : reportList) {
            report.append(transaction)
                    .append(System.lineSeparator());
        }
        return report.toString();
    }
}
