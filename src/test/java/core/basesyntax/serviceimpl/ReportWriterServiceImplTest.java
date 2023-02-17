package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import core.basesyntax.service.ReaderService;
import core.basesyntax.service.ReportWriterService;
import java.util.List;
import org.junit.Test;

public class ReportWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/java/resources/testreport.csv";
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private final ReportWriterService reportWriterService = new ReportWriterServiceImpl();
    private final ReaderService readerService = new ReaderServiceImpl();

    @Test
    public void write_validPath_ok() {
        reportWriterService.write(report, VALID_PATH);
        List<String> expected = List.of("fruit,quantity",
                "banana,152",
                "apple,90");
        List<String> actual = readerService.read(VALID_PATH);
        assertEquals(expected, actual);
    }

    @Test
    public void write_nullValue_notOk() {
        try {
            reportWriterService.write(null, VALID_PATH);
        } catch (NullPointerException e) {
            return;
        }
        fail("NullPointerException should be thrown");
    }
}
