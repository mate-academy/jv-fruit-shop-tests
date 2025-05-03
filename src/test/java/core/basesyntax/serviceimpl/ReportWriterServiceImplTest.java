package core.basesyntax.serviceimpl;

import static org.junit.Assert.assertEquals;

import core.basesyntax.service.ReportWriterService;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.Test;

public class ReportWriterServiceImplTest {
    private static final String VALID_PATH = "src/test/java/resources/testreport.csv";
    private static final String report = "fruit,quantity" + System.lineSeparator()
            + "banana,152" + System.lineSeparator() + "apple,90";
    private final ReportWriterService reportWriterService = new ReportWriterServiceImpl();

    @Test
    public void write_validPath_ok() {
        reportWriterService.write(report, VALID_PATH);
        List<String> actual;
        List<String> expected = List.of("fruit,quantity",
                "banana,152",
                "apple,90");
        Path fromFilePath = Path.of(VALID_PATH);
        try {
            actual = Files.readAllLines(fromFilePath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        assertEquals(expected, actual);
    }

    @Test (expected = RuntimeException.class)
    public void write_nullValue_notOk() {
        reportWriterService.write(null, VALID_PATH);
    }
}
